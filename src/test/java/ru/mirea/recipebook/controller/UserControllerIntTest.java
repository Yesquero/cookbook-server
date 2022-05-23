package ru.mirea.recipebook.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mirea.recipebook.AbstractIntegrationTest;
import ru.mirea.recipebook.controller.dto.AuthenticationDto;
import ru.mirea.recipebook.controller.dto.ExceptionResponse;
import ru.mirea.recipebook.controller.dto.UserInfoDto;
import ru.mirea.recipebook.domain.UserEntity;
import ru.mirea.recipebook.domain.enumeration.UserRole;
import ru.mirea.recipebook.domain.enumeration.UserStatus;
import ru.mirea.recipebook.service.UserService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntTest extends AbstractIntegrationTest {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void registration_and_Success() throws Exception {
		UserInfoDto dto = createNewUserDto();

		mockMvc.perform(
				post("/api/user/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isOk());

		Optional<UserEntity> user = userRepository.findByLogin(dto.getLogin());
		Assertions.assertTrue(user.isPresent());
	}

	@Test
	public void login_and_Success() throws Exception {
		userRepository.save(createNewUser());
		AuthenticationDto dto = new AuthenticationDto();
		dto.setLogin(USER_LOGIN);
		dto.setPassword(USER_PASSWORD);

		mockMvc.perform(
			post("/api/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
		).andExpect(status().isOk());
	}

	@Test
	public void login_and_IncorrectPassword() throws Exception {
		userRepository.save(createNewUser());
		AuthenticationDto dto = new AuthenticationDto();
		dto.setLogin(USER_LOGIN);
		dto.setPassword(INVALID_PASSWORD);

		MockHttpServletResponse response = mockMvc.perform(
			post("/api/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
		).andExpect(status().isBadRequest()).andReturn().getResponse();

		ExceptionResponse exceptionResponse = objectMapper.readValue(
			response.getContentAsString(),
			ExceptionResponse.class
		);

		Assertions.assertEquals(UserService.INCORRECT_PASSWORD_MESSAGE, exceptionResponse.getResponseMessage());
	}

	private UserInfoDto createNewUserDto() {
		UserInfoDto dto = new UserInfoDto();

		dto.setLogin(USER_LOGIN);
		dto.setNickname(USER_LOGIN);
		dto.setPassword("TestPassword");
		dto.setFirstName("TestFirstName");
		dto.setLastName("TestLastName");
		dto.setStatus(String.valueOf(UserStatus.ACTIVE));
		dto.setUserRole(String.valueOf(UserRole.USER));

		return dto;
	}

	private UserEntity createNewUser() {
		UserEntity entity = new UserEntity();

		entity.setLogin(USER_LOGIN);
		entity.setNickname(USER_LOGIN);
		entity.setPassword(passwordEncoder.encode(USER_PASSWORD));
		entity.setFirstName("TestFirstName");
		entity.setLastName("TestLastName");
		entity.setStatus(UserStatus.ACTIVE);
		entity.setUserRole(UserRole.USER);

		return entity;
	}

}
