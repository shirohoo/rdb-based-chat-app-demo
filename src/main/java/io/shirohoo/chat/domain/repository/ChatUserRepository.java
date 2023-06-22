package io.shirohoo.chat.domain.repository;

import io.shirohoo.chat.domain.model.ChatUser;
import io.shirohoo.chat.domain.model.ChatUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, ChatUserId> {
}
