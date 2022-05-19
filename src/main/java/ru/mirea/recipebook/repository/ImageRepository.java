package ru.mirea.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.recipebook.domain.Image;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}
