package ru.mirea.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.recipebook.domain.RecipeCategory;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<RecipeCategory, UUID> {

}
