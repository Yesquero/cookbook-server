package ru.mirea.recipebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.mirea.recipebook.repository.CategoryRepository;
import ru.mirea.recipebook.repository.RecipeRepository;
import ru.mirea.recipebook.repository.UserEntityRepository;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
public
class AbstractIntegrationTest {

	protected static final String USER_LOGIN = "TestUser_1";
	protected static final String RECIPE_NAME = "TestRecipeOne";
	protected static final String CATEGORY_NAME = "TestCategory";
	protected static final String USER_PASSWORD = "TestPassword";
	protected static final String INVALID_PASSWORD = "InvalidPassword";
	protected static final UUID CATEGORY_UUID = UUID.randomUUID();
	protected static final UUID RECIPE_UUID = UUID.randomUUID();

	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected CategoryRepository categoryRepository;
	@Autowired
	protected UserEntityRepository userRepository;
	@Autowired
	protected RecipeRepository recipeRepository;
	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setupMvc() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(SecurityMockMvcConfigurers.springSecurity())
			.build();
	}

	@AfterEach
	public void cleanup() {
		recipeRepository.deleteAll();
		categoryRepository.deleteAll();
		userRepository.deleteAll();
	}

}
