package ru.mirea.recipebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.mirea.recipebook.controller.dto.AuthenticationDto;
import ru.mirea.recipebook.controller.dto.UserInfoDto;
import ru.mirea.recipebook.controller.dto.mapper.DtoMapper;
import ru.mirea.recipebook.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final DtoMapper mapper;
	private final UserService userService;

	// change to GET through basic auth chain
	@PostMapping("/login")
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

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<UserInfoDto> getAll() {
		return userService.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

}
