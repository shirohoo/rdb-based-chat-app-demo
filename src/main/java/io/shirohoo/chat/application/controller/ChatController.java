package io.shirohoo.chat.application.controller;

import io.shirohoo.chat.domain.Chat;
import io.shirohoo.chat.domain.model.ChatUser;
import io.shirohoo.chat.domain.model.Message;
import io.shirohoo.chat.domain.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/api/v1/chat/{chatId}/messages")
    @SendTo("/topic/chat/{chatId}/messages")
    public ChatMessage send(@DestinationVariable String chatId, @RequestBody ChatMessage chatMessage) {
        chatService.saveMessage(chatId, chatMessage.userId(), chatMessage.content());
        return chatMessage;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Chat> chats = chatService.getChats();
        model.addAttribute("chats", chats);
        return "index";
    }

    @ResponseBody
    @PostMapping("/api/v1/chat")
    public Map<String, String> createChat(@RequestBody CreateChatRequest request) {
        Chat chat = chatService.createChat(request.hostId, request.topic, request.password);
        return Map.of("chatId", chat.getChatId());
    }

    private record CreateChatRequest(String hostId, String topic, String password) {
    }

    @GetMapping("/chat")
    public String joinChat(String chatId, String userId, Model model) {
        ChatUser chatUser = chatService.joinChat(chatId, userId);
        Set<Message> messages = chatService.getMessages(chatId);

        model.addAttribute("chatUser", chatUser);
        model.addAttribute("messages", messages);

        return "chat";
    }

    private record ChatMessage(String userId, String content) {
    }

}
