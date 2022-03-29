package ru.mirea.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.recipebook.domain.UserEntity;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
}
