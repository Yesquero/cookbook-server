package ru.mirea.recipebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.recipebook.controller.dto.NewRecipeDto;
import ru.mirea.recipebook.domain.Image;
import ru.mirea.recipebook.domain.Recipe;
import ru.mirea.recipebook.domain.RecipeCategory;
import ru.mirea.recipebook.domain.UserEntity;
import ru.mirea.recipebook.domain.enumeration.RecipeStatus;
import ru.mirea.recipebook.repository.RecipeRepository;
import ru.mirea.recipebook.utility.DomainLogicException;
import ru.mirea.recipebook.utility.ResourceConflictException;
import ru.mirea.recipebook.utility.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final ImageService imageService;
	private final CategoryService categoryService;
	private final UserService userService;

	public Recipe findByUuid(UUID uuid) {
		return recipeRepository.findById(uuid)
			.orElseThrow(() -> new ResourceNotFoundException(Recipe.class, uuid));
	}

	public List<Recipe> getAll() {
		return recipeRepository.findAll();
	}

	// picture should be uploaded first
	@Transactional
	public Recipe add(NewRecipeDto dto) {
		Optional<Recipe> existingRecipe = recipeRepository.findByName(dto.getName());

		if (existingRecipe.isPresent()) {
			throw new ResourceConflictException(Recipe.class, dto.getName());
		}

		Image recipeImage = null;
		if (dto.getRecipeImageUuid() != null) {
			recipeImage = imageService.findByUuid(UUID.fromString(dto.getRecipeImageUuid()));
		}

		RecipeCategory recipeCategory = null;
		try {
			recipeCategory = categoryService.findByName(dto.getCategory());
		} catch (ResourceNotFoundException ex) {
			recipeCategory = categoryService.addCategory(dto.getCategory());
		}

		Recipe newRecipe = new Recipe();

		// set recipe fields
		setRecipeFields(newRecipe, dto);
		newRecipe.setRecipeImage(recipeImage);
		newRecipe.setCategory(recipeCategory);
		newRecipe.setStatus(RecipeStatus.APPROVED);

		return recipeRepository.saveAndFlush(newRecipe);
	}

	// figure out how to change picture
	@Transactional
	public Recipe update(UUID uuid, NewRecipeDto dto) {
		Recipe toUpdate = findByUuid(uuid);

		// update image if new UUID is specified, and it is different from currently assigned UUID
		if (dto.getRecipeImageUuid() == null) {
			toUpdate.setRecipeImage(null);
		} else if (!toUpdate.getRecipeImage().getUuid().equals(UUID.fromString(dto.getRecipeImageUuid()))) {
			Image newImage = imageService.findByUuid(UUID.fromString(dto.getRecipeImageUuid()));
			toUpdate.setRecipeImage(newImage);
		}

		// update category if it changed
		if (!toUpdate.getCategory().getName().equals(dto.getCategory())) {
			RecipeCategory newCategory = categoryService.findByName(dto.getCategory());
			toUpdate.setCategory(newCategory);
		}

		// set all remaining fields from dto
		setRecipeFields(toUpdate, dto);

		return recipeRepository.saveAndFlush(toUpdate);
	}

	@Transactional
	public List<Recipe> getFavoriteRecipes(String login) {
		UserEntity foundUser = userService.findByLogin(login);

		return new ArrayList<>(foundUser.getFavoriteRecipes());
	}

	@Transactional
	public void delete(UUID uuid) {
		Recipe toDelete = findByUuid(uuid);

		for (UserEntity user : toDelete.getInFavorites()) {
			user.deleteFromFavorites(toDelete);
		}

		recipeRepository.delete(toDelete);
	}

	@Transactional
	public Recipe addToFavorites(String login, UUID recipeUuid) {
		UserEntity foundUser = userService.findByLogin(login);
		Recipe foundRecipe = findByUuid(recipeUuid);

		if (foundUser.getFavoriteRecipes().contains(foundRecipe)) {
			throw new DomainLogicException("Recipe is already in favorites.");
		} else {
			foundUser.addToFavorites(foundRecipe);
		}

		return foundRecipe;
	}

	@Transactional
	public Recipe deleteFromFavorites(String login, UUID recipeUuid) {
		UserEntity foundUser = userService.findByLogin(login);
		Recipe foundRecipe = findByUuid(recipeUuid);

		if (!foundUser.getFavoriteRecipes().contains(foundRecipe)) {
			throw new DomainLogicException("Recipe is not in users favorites.");
		} else {
			foundUser.deleteFromFavorites(foundRecipe);
		}

		return foundRecipe;
	}

	private void setRecipeFields(Recipe recipe, NewRecipeDto dto) {
		recipe.setName(dto.getName());
		recipe.setDescription(dto.getDescription());
		recipe.setComplexity(dto.getComplexity());
		recipe.setInstructions(dto.getInstructions());
		recipe.setPrice(dto.getPrice());
		recipe.setCalories(dto.getCalories());
		recipe.setCarbohydrates(dto.getCarbohydrates());
		recipe.setFats(dto.getFats());
		recipe.setProteins(dto.getProteins());
		recipe.setDurationHours(dto.getDurationHours());
		recipe.setDurationMinutes(dto.getDurationMinutes());
		recipe.setPortion(dto.getPortion());
	}

}
