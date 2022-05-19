package ru.mirea.recipebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.recipebook.domain.UserEntity;
import ru.mirea.recipebook.repository.UserEntityRepository;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
@Service
@RequiredArgsConstructor
public class UserService {

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

}
