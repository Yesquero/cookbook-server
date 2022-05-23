package ru.mirea.recipebook.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.mirea.recipebook.AbstractIntegrationTest;
import ru.mirea.recipebook.domain.RecipeCategory;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerIntTest extends AbstractIntegrationTest {

	@Test
	@WithMockUser(authorities = "USER")
	public void addCategory_and_Success() throws Exception {
		mockMvc.perform(
				post("/api/category/add/{name}", CATEGORY_NAME)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		Optional<RecipeCategory> foundCategory = categoryRepository.findByName(CATEGORY_NAME);
		Assertions.assertTrue(foundCategory.isPresent());
	}

}
