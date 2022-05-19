package ru.mirea.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.recipebook.domain.Recipe;

import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

	Optional<Recipe> findByName(String name);

}
