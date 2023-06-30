package io.shirohoo.chat.application.controller;

import io.shirohoo.chat.domain.Chat;
import io.shirohoo.chat.domain.model.ChatRoom;
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

	@SendTo("/topic/chat/{chatId}/messages")
	@MessageMapping("/api/v1/chat/{chatId}/messages")
	public ChatMessage send(@DestinationVariable String chatId, @RequestBody ChatMessage chatMessage) {
		chatService.saveChatMessage(chatId, chatMessage.userId(), chatMessage.content());
		return chatMessage;
	}

	private record ChatMessage(String userId, String content) {
	}

	@ResponseBody
	@PostMapping("/api/v1/chat")
	public Map<String, String> createChat(@RequestBody CreateChatRequest request) {
		Chat chat = chatService.createChat(request.hostId, request.topic, request.password);
		return Map.of("chatId", chat.getId());
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Chat> chats = chatService.getChats();
		model.addAttribute("chats", chats);
		return "index";
	}

	private record CreateChatRequest(String hostId, String topic, String password) {
	}

	@GetMapping("/chat")
	public String joinChat(Model model, String chatId, String userId) {
		ChatRoom chatRoom = chatService.joinChat(chatId, userId);
		Set<io.shirohoo.chat.domain.model.ChatMessage> chatMessages = chatService.getChatMessages(chatId);

		model.addAttribute("chatRoom", chatRoom);
		model.addAttribute("chatMessages", chatMessages);

		return "chat";
	}

}
