package io.shirohoo.chat.domain.repository;

import io.shirohoo.chat.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.chat.chatId = :chatId ORDER BY m.createdAt")
    Set<Message> findByChat(@Param("chatId") String chatId);

}
