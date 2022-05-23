package ru.mirea.recipebook.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import ru.mirea.recipebook.AbstractIntegrationTest;
import ru.mirea.recipebook.controller.dto.NewRecipeDto;
import ru.mirea.recipebook.controller.dto.RecipeShortDto;
import ru.mirea.recipebook.domain.Recipe;
import ru.mirea.recipebook.domain.RecipeCategory;
import ru.mirea.recipebook.domain.enumeration.RecipeStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerIntTest extends AbstractIntegrationTest {

	private static final String FIRST_RECIPE_NAME = "FirstRecipe";
	private static final String SECOND_RECIPE_NAME = "SecondRecipe";
	private static final String CHANGED_RECIPE_NAME = "ChangedName";

	@Test
	public void addRecipe_and_Unauthorized() throws Exception {
		NewRecipeDto dto = createNewRecipeDto();

		mockMvc.perform(
				post("/api/recipe/add")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void addRecipe_and_Success() throws Exception {
		NewRecipeDto dto = createNewRecipeDto();
		RecipeCategory recipeCategory = new RecipeCategory();
		recipeCategory.setName(CATEGORY_NAME);
		categoryRepository.save(recipeCategory);

		mockMvc.perform(
				post("/api/recipe/add")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isOk());

		Optional<Recipe> foundRecipe = recipeRepository.findByName(dto.getName());
		Assertions.assertTrue(foundRecipe.isPresent());
	}

	@Test
	@WithMockUser(authorities = {"USER"})
	public void updateRecipe_and_Success() throws Exception {
		UUID savedRecipeUuid = createAndSaveRecipe();
		NewRecipeDto dto = createNewRecipeDto();
		dto.setName(CHANGED_RECIPE_NAME);

		mockMvc.perform(
			put("/api/recipe/update/{uuid}", savedRecipeUuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
		).andExpect(status().isOk());

		Optional<Recipe> updatedRecipe = recipeRepository.findById(savedRecipeUuid);
		Assertions.assertTrue(updatedRecipe.isPresent());
		Assertions.assertEquals(CHANGED_RECIPE_NAME, updatedRecipe.get().getName());
	}

	@Test
	public void updateRecipe_and_Unauthorized() throws Exception {
		createAndSaveRecipe();
		NewRecipeDto dto = createNewRecipeDto();
		dto.setName(CHANGED_RECIPE_NAME);

		mockMvc.perform(
			put("/api/recipe/update/{uuid}", RECIPE_UUID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
		).andExpect(status().isUnauthorized());
	}

	@Test
	public void getRecipeList() throws Exception {
		createAndSaveTwoRecipes();

		MockHttpServletResponse response = mockMvc.perform(
			get("/api/recipe/recipes")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk()).andReturn().getResponse();

		List<RecipeShortDto> recipeShortDtoList = objectMapper.readValue(
			response.getContentAsString(),
			new TypeReference<List<RecipeShortDto>>() {
			}
		);

		Assertions.assertNotNull(recipeShortDtoList);
		Assertions.assertEquals(FIRST_RECIPE_NAME, recipeShortDtoList.get(0).getName());
		Assertions.assertEquals(SECOND_RECIPE_NAME, recipeShortDtoList.get(1).getName());
	}

	private NewRecipeDto createNewRecipeDto() {
		NewRecipeDto dto = new NewRecipeDto();

		dto.setCategory(CATEGORY_NAME);
		dto.setName(RECIPE_NAME);
		dto.setStatus("APPROVED");
		dto.setComplexity(1);
		dto.setDescription("TestDescription");
		dto.setInstructions("TestInstructions");
		dto.setRecipeImageUuid(null);
		dto.setDurationHours(1);
		dto.setDurationMinutes(2);
		dto.setPortion("TestPortion");
		dto.setCalories(BigDecimal.ONE);
		dto.setFats(BigDecimal.ONE);
		dto.setCarbohydrates(BigDecimal.ONE);
		dto.setProteins(BigDecimal.ONE);
		dto.setPrice(BigDecimal.ONE);

		return dto;
	}

	private UUID createAndSaveRecipe() {
		RecipeCategory recipeCategory = new RecipeCategory();
		recipeCategory.setName(CATEGORY_NAME);
		recipeCategory.setUuid(CATEGORY_UUID);
		recipeCategory = categoryRepository.saveAndFlush(recipeCategory);

		Recipe newRecipe = new Recipe();

		newRecipe.setCategory(recipeCategory);
		newRecipe.setName(RECIPE_NAME);
		newRecipe.setStatus(RecipeStatus.APPROVED);
		newRecipe.setComplexity(1);
		newRecipe.setDescription("TestDescription");
		newRecipe.setInstructions("TestInstructions");
		newRecipe.setRecipeImage(null);
		newRecipe.setDurationHours(1);
		newRecipe.setDurationMinutes(2);
		newRecipe.setPortion("TestPortion");
		newRecipe.setCalories(BigDecimal.ONE);
		newRecipe.setFats(BigDecimal.ONE);
		newRecipe.setCarbohydrates(BigDecimal.ONE);
		newRecipe.setProteins(BigDecimal.ONE);
		newRecipe.setPrice(BigDecimal.ONE);

		newRecipe = recipeRepository.saveAndFlush(newRecipe);
		return newRecipe.getUuid();
	}

	private void createAndSaveTwoRecipes() {
		RecipeCategory recipeCategory = new RecipeCategory();
		recipeCategory.setUuid(CATEGORY_UUID);
		recipeCategory.setName(CATEGORY_NAME);
		recipeCategory = categoryRepository.saveAndFlush(recipeCategory);

		Recipe firstRecipe = new Recipe();
		Recipe secondRecipe = new Recipe();

		firstRecipe.setCategory(recipeCategory);
		firstRecipe.setName(FIRST_RECIPE_NAME);
		firstRecipe.setStatus(RecipeStatus.APPROVED);
		firstRecipe.setComplexity(1);
		firstRecipe.setDescription("TestDescription");
		firstRecipe.setInstructions("TestInstructions");
		firstRecipe.setRecipeImage(null);
		firstRecipe.setDurationHours(1);
		firstRecipe.setDurationMinutes(2);
		firstRecipe.setPortion("TestPortion");
		firstRecipe.setCalories(BigDecimal.ONE);
		firstRecipe.setFats(BigDecimal.ONE);
		firstRecipe.setCarbohydrates(BigDecimal.ONE);
		firstRecipe.setProteins(BigDecimal.ONE);
		firstRecipe.setPrice(BigDecimal.ONE);

		secondRecipe.setCategory(recipeCategory);
		secondRecipe.setName(SECOND_RECIPE_NAME);
		secondRecipe.setStatus(RecipeStatus.APPROVED);
		secondRecipe.setComplexity(1);
		secondRecipe.setDescription("TestDescription");
		secondRecipe.setInstructions("TestInstructions");
		secondRecipe.setRecipeImage(null);
		secondRecipe.setDurationHours(1);
		secondRecipe.setDurationMinutes(2);
		secondRecipe.setPortion("TestPortion");
		secondRecipe.setCalories(BigDecimal.ONE);
		secondRecipe.setFats(BigDecimal.ONE);
		secondRecipe.setCarbohydrates(BigDecimal.ONE);
		secondRecipe.setProteins(BigDecimal.ONE);
		secondRecipe.setPrice(BigDecimal.ONE);

		recipeRepository.save(firstRecipe);
		recipeRepository.save(secondRecipe);
	}

}
