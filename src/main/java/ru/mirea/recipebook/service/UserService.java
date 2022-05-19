package ru.mirea.recipebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.recipebook.controller.dto.AuthenticationDto;
import ru.mirea.recipebook.controller.dto.UserInfoDto;
import ru.mirea.recipebook.domain.SecurityUser;
import ru.mirea.recipebook.domain.UserEntity;
import ru.mirea.recipebook.domain.enumeration.UserRole;
import ru.mirea.recipebook.domain.enumeration.UserStatus;
import ru.mirea.recipebook.repository.UserEntityRepository;
import ru.mirea.recipebook.utility.DomainLogicException;
import ru.mirea.recipebook.utility.ResourceConflictException;
import ru.mirea.recipebook.utility.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserEntityRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserEntity findUserById(UUID uuid) {
		return userRepository.findById(uuid)
			.orElseThrow(() -> new ResourceNotFoundException(UserEntity.class, uuid));
	}

	public UserEntity findByLogin(String login) {
		return userRepository.findByLogin(login)
			.orElseThrow(() -> new ResourceNotFoundException(UserEntity.class, login));
	}

	public UserEntity saveUser(UserEntity user) {
		return userRepository.saveAndFlush(user);
	}

	public List<UserEntity> getAll() {
		return userRepository.findAll();
	}

	public Boolean isRepoEmpty() {
		return userRepository.findAll().isEmpty();
	}

	public UserEntity authenticate(AuthenticationDto dto) {
		UserEntity user = findByLogin(dto.getLogin());

		if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			return user;
		} else {
			throw new DomainLogicException("Incorrect password.");
		}
	}

	public UserEntity register(UserInfoDto dto) {
		Optional<UserEntity> user = userRepository.findByLogin(dto.getLogin());
		if (user.isPresent()) {
			throw new ResourceConflictException(UserEntity.class, dto.getLogin());
		}

		UserEntity newUser = new UserEntity();
		newUser.setLogin(dto.getLogin());
		newUser.setUserRole(UserRole.USER);
		newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		newUser.setStatus(UserStatus.ACTIVE);
		newUser.setNickname(dto.getLogin());
		newUser.setFirstName(dto.getFirstName());
		newUser.setLastName(dto.getLastName());

		return userRepository.saveAndFlush(newUser);
	}

	@Override
	public SecurityUser loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<UserEntity> foundUser = userRepository.findByLogin(login);
		if (foundUser.isPresent()) {
			return new SecurityUser((foundUser.get()));
		} else {
			throw new UsernameNotFoundException("User with login: " + login + " not found.");
		}
	}

}
