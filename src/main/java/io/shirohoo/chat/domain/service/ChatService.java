package io.shirohoo.chat.domain.service;

import io.shirohoo.chat.domain.Chat;
import io.shirohoo.chat.domain.model.ChatMessage;
import io.shirohoo.chat.domain.model.ChatRoom;
import io.shirohoo.chat.domain.model.ChatRoomId;
import io.shirohoo.chat.domain.model.User;
import io.shirohoo.chat.domain.repository.ChatMessageRepository;
import io.shirohoo.chat.domain.repository.ChatRepository;
import io.shirohoo.chat.domain.repository.ChatRoomRepository;
import io.shirohoo.chat.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public List<Chat> getChats() {
        return chatRepository.findAll();
    }

    @Transactional
    public Chat createChat(String hostId, String topic, String password) {
        if (topic.isBlank()) {
            throw new UnsupportedOperationException("topic is blank");
        }
        if (password == null || password.isBlank()) {
            password = null;
        }

        User host = userRepository.findById(hostId)
                .orElseGet(() -> userRepository.save(new User(hostId)));

        Chat chat = new Chat(host, topic, password);
        return chatRepository.save(chat);
    }

    @Transactional
    public ChatRoom joinChat(String chatId, String participantId) {
        ChatRoomId chatRoomId = new ChatRoomId(chatId, participantId);
        if (chatRoomRepository.existsById(chatRoomId)) {
            //noinspection OptionalGetWithoutIsPresent
            return chatRoomRepository.findById(chatRoomId).get();
        }

        User user = userRepository.findById(participantId).orElseGet(() -> userRepository.save(new User(participantId)));
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        return chatRoomRepository.save(chat.join(user));
    }

    @Transactional(readOnly = true)
    public Set<ChatMessage> getChatMessages(String chatId) {
        return chatMessageRepository.findByChat(chatId);
    }

    @Transactional
    public void saveChatMessage(String chatId, String userId, String content) {
        if (content == null || content.isBlank()) {
            return;
        }

        Chat chat = chatRepository.findById(chatId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        ChatMessage chatMessage = new ChatMessage(chat, user, content);
        chatMessageRepository.save(chatMessage);
    }

}
