package ru.mirea.recipebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.recipebook.domain.RecipeCategory;
import ru.mirea.recipebook.repository.CategoryRepository;
import ru.mirea.recipebook.utility.ResourceNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public RecipeCategory findByName(String name) {
		return categoryRepository.findByName(name)
			.orElseThrow(() -> new ResourceNotFoundException(RecipeCategory.class, name));
	}

	public List<RecipeCategory> getAll() {
		return categoryRepository.findAll();
	}

}
