package ru.mirea.recipebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.mirea.recipebook.controller.dto.AuthenticationDto;
import ru.mirea.recipebook.controller.dto.UserInfoDto;
import ru.mirea.recipebook.controller.dto.mapper.DtoMapper;
import ru.mirea.recipebook.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final DtoMapper mapper;
	private final UserService userService;

	@GetMapping("/login")
	public UserInfoDto login(@RequestBody AuthenticationDto dto) {
		return mapper.toDto(userService.authenticate(dto));
	}

	@PostMapping("/register")
	public UserInfoDto register(@RequestBody UserInfoDto dto) {
		return mapper.toDto(userService.register(dto));
	}

	@GetMapping("/testAuth")
	public String testAuthentication(Authentication authentication) {
		return "Authenticated as: " + authentication.getName();
	}

}
