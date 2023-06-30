package io.shirohoo.chat.domain.model;

import io.shirohoo.chat.domain.Chat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {

	@Id
	@Column(name = "message_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "chat_id", nullable = false)
	private Chat chat;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;

	@Column(name = "content", nullable = false)
	private String content;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	public ChatMessage(Chat chat, User sender, String content) {
		this.chat = chat;
		this.sender = sender;
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message{id='%d', chat='%s', sender='%s', content='%s', createdAt='%s'}".formatted(id, chat, sender, content, createdAt);
	}
}
