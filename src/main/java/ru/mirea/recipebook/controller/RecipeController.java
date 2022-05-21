package ru.mirea.recipebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.recipebook.controller.dto.NewRecipeDto;
import ru.mirea.recipebook.controller.dto.RecipeInfoDto;
import ru.mirea.recipebook.controller.dto.RecipeShortDto;
import ru.mirea.recipebook.controller.dto.mapper.DtoMapper;
import ru.mirea.recipebook.domain.Recipe;
import ru.mirea.recipebook.service.RecipeService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/recipe")
@RestController
@RequiredArgsConstructor
public class RecipeController {

	private final DtoMapper mapper;
	private final RecipeService recipeService;

	@GetMapping("/recipes")
	public List<RecipeShortDto> getAllShort() {
		List<Recipe> recipes = recipeService.getAll();

		return recipes.stream().map(mapper::toShortDto).collect(Collectors.toList());
	}

	@GetMapping("/info/{uuid}")
	public RecipeInfoDto getDetails(@PathVariable UUID uuid) {
		return mapper.toDto(recipeService.findByUuid(uuid));
	}

	@PostMapping("/add")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
	public RecipeShortDto addRecipe(@RequestBody NewRecipeDto dto) {
		return mapper.toShortDto(recipeService.add(dto));
	}

	@PutMapping("/update/{uuid}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
	public RecipeShortDto updateRecipe(@PathVariable UUID uuid, @RequestBody NewRecipeDto dto) {
		return mapper.toShortDto(recipeService.update(uuid, dto));
	}

	@DeleteMapping("/delete/{uuid}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public void delete(@PathVariable UUID uuid) {
		recipeService.delete(uuid);
	}

}
