package io.shirohoo.chat.domain.model;

import io.shirohoo.chat.domain.Chat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@IdClass(ChatRoomId.class)
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private Chat chat;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participant_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private User participant;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ChatRoom(Chat chat, User participant) {
        this.chat = chat;
        this.participant = participant;
    }

    public ChatRoomId getId() {
        return new ChatRoomId(chat.getId(), participant.getId());
    }
}
