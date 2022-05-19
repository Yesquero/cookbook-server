package ru.mirea.recipebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mirea.recipebook.domain.SecurityUser;
import ru.mirea.recipebook.domain.UserEntity;
import ru.mirea.recipebook.repository.UserEntityRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserEntityRepository userRepository;

	public UserEntity findUserById(UUID uuid) {
		return userRepository.findById(uuid)
			.orElseThrow(() -> new ResourceNotFoundException(UserEntity.class, uuid));
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
