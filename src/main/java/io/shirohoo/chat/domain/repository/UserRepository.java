package io.shirohoo.chat.domain.repository;

import io.shirohoo.chat.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
