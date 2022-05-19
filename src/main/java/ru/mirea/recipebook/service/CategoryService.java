package ru.mirea.recipebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.recipebook.domain.RecipeCategory;
import ru.mirea.recipebook.repository.CategoryRepository;
import ru.mirea.recipebook.utility.DomainLogicException;
import ru.mirea.recipebook.utility.ResourceConflictException;
import ru.mirea.recipebook.utility.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public RecipeCategory findByName(String name) {
		return categoryRepository.findByName(name)
			.orElseThrow(() -> new ResourceNotFoundException(RecipeCategory.class, name));
	}

	@Transactional
	public RecipeCategory addCategory(String name) {
		Optional<RecipeCategory> foundCategory = categoryRepository.findByName(name);
		if (foundCategory.isPresent()) {
			throw new ResourceConflictException(RecipeCategory.class, name);
		}

		RecipeCategory newCategory = new RecipeCategory();
		newCategory.setName(name);

		return categoryRepository.saveAndFlush(newCategory);
	}

	public List<RecipeCategory> getAll() {
		return categoryRepository.findAll();
	}

	@Transactional
	public void delete(UUID uuid) {
		RecipeCategory toDelete = categoryRepository.findById(uuid)
			.orElseThrow(() -> new ResourceNotFoundException(RecipeCategory.class, uuid));

		if (!toDelete.getRecipes().isEmpty()) {
			throw new DomainLogicException("Cannot delete category with linked recipes.");
		}

		categoryRepository.delete(toDelete);
	}

}
