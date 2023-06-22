package io.shirohoo.chat.domain.model;

import io.shirohoo.chat.domain.Chat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@IdClass(ChatUserId.class)
@EntityListeners(AuditingEntityListener.class)
public class ChatUser {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_guid", nullable = false)
    private Chat chat;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_guid", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ChatUser(Chat chat, User user) {
        this.chat = chat;
        this.user = user;
    }

    public ChatUserId getId() {
        return new ChatUserId(chat.getChatId(), user.getUserId());
    }
}
