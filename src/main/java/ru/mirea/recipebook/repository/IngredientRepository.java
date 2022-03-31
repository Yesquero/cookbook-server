package ru.mirea.recipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.recipebook.domain.Ingredient;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
}
