package ru.mirea.recipebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.recipebook.controller.dto.RecipeCategoryDto;
import ru.mirea.recipebook.controller.dto.mapper.DtoMapper;
import ru.mirea.recipebook.domain.RecipeCategory;
import ru.mirea.recipebook.service.CategoryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	private final DtoMapper mapper;

	@PostMapping("/add/{name}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
	public RecipeCategoryDto addCategory(@PathVariable String name) {
		return mapper.toDto(categoryService.addCategory(name));
	}

	@GetMapping("/all")
	public List<RecipeCategoryDto> getAll() {
		List<RecipeCategory> categories = categoryService.getAll();

		return categories.stream().map(mapper::toDto).collect(Collectors.toList());
	}

	@DeleteMapping("/delete/{uuid}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
	public void delete(@PathVariable UUID uuid) {
		categoryService.delete(uuid);
	}

}
