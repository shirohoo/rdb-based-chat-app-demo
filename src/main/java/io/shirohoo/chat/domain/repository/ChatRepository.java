package io.shirohoo.chat.domain.repository;

import io.shirohoo.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {
}
