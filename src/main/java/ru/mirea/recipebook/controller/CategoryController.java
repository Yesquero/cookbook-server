package ru.mirea.recipebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.recipebook.controller.dto.RecipeCategoryDto;
import ru.mirea.recipebook.controller.dto.mapper.DtoMapper;
import ru.mirea.recipebook.domain.RecipeCategory;
import ru.mirea.recipebook.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	private final DtoMapper mapper;

	@GetMapping("/all")
	public List<RecipeCategoryDto> getAll() {
		List<RecipeCategory> categories = categoryService.getAll();

		return categories.stream().map(mapper::toDto).collect(Collectors.toList());
	}

}
