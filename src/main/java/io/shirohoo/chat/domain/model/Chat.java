package io.shirohoo.chat.domain;

import io.shirohoo.chat.domain.model.ChatUser;
import io.shirohoo.chat.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "chat_guid", nullable = false, length = 36)
    private String chatId;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "password")
    private String password;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Chat(String topic, String password) {
        this.topic = topic;
        this.password = password;
    }

    public ChatUser join(User user) {
        return new ChatUser(this, user);
    }
}
