package io.shirohoo.chat.domain.service;

import io.shirohoo.chat.domain.Chat;
import io.shirohoo.chat.domain.model.ChatUser;
import io.shirohoo.chat.domain.model.ChatUserId;
import io.shirohoo.chat.domain.model.Message;
import io.shirohoo.chat.domain.model.User;
import io.shirohoo.chat.domain.repository.ChatRepository;
import io.shirohoo.chat.domain.repository.ChatUserRepository;
import io.shirohoo.chat.domain.repository.MessageRepository;
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
    private final MessageRepository messageRepository;
    private final ChatUserRepository chatUserRepository;

    @Transactional
    public List<Chat> getChats() {
        return chatRepository.findAll();
    }

    @Transactional
    public Chat createChat(String topic, String password) {
        if (topic.isBlank()) {
            throw new UnsupportedOperationException("topic is blank");
        }
        if (password.isBlank()) {
            password = null;
        }

        Chat chat = new Chat(topic, password);
        return chatRepository.save(chat);
    }

    @Transactional
    public ChatUser joinChat(String chatId, String userId) {
        User user = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(new User(userId)));

        ChatUserId chatUserId = new ChatUserId(chatId, userId);
        if (chatUserRepository.existsById(chatUserId)) {
            //noinspection OptionalGetWithoutIsPresent
            return chatUserRepository.findById(chatUserId).get();
        }

        Chat chat = chatRepository.findById(chatId).orElseThrow();
        ChatUser chatUser = chat.join(user);
        return chatUserRepository.save(chatUser);
    }

    @Transactional
    public void saveMessage(String chatId, String userId, String content) {
        if (content == null || content.isBlank()) {
            return;
        }

        Chat chat = chatRepository.findById(chatId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Message message = new Message(chat, user, content);
        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public Set<Message> getMessages(String chatId) {
        return messageRepository.findByChat(chatId);
    }

}
