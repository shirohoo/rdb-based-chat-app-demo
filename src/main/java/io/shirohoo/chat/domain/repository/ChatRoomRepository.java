package io.shirohoo.chat.domain.repository;

import io.shirohoo.chat.domain.model.ChatRoom;
import io.shirohoo.chat.domain.model.ChatRoomId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, ChatRoomId> {
}
