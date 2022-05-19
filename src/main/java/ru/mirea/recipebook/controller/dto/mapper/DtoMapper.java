package ru.mirea.recipebook.controller.dto.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.mirea.recipebook.controller.dto.RecipeCategoryDto;
import ru.mirea.recipebook.controller.dto.RecipeInfoDto;
import ru.mirea.recipebook.controller.dto.RecipeShortDto;
import ru.mirea.recipebook.controller.dto.UserInfoDto;
import ru.mirea.recipebook.domain.Recipe;
import ru.mirea.recipebook.domain.RecipeCategory;
import ru.mirea.recipebook.domain.RecipeRating;
import ru.mirea.recipebook.domain.UserEntity;

@Component
public class DtoMapper {

	private final ModelMapper mapper;

	public DtoMapper() {
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

		mapper.typeMap(Recipe.class, RecipeShortDto.class)
			.addMapping(Recipe::getUuid, RecipeShortDto::setUuid)
			.addMapping(Recipe::getName, RecipeShortDto::setName)
			.addMapping(Recipe::getPrice, RecipeShortDto::setPrice)
			.addMapping(Recipe::getDurationHours, RecipeShortDto::setDurationHours)
			.addMapping(Recipe::getDurationMinutes, RecipeShortDto::setDurationMinutes)
			.setPostConverter(toDtoShort());

		mapper.typeMap(Recipe.class, RecipeInfoDto.class)
			.addMapping(Recipe::getUuid, RecipeInfoDto::setUuid)
			.addMapping(Recipe::getName, RecipeInfoDto::setName)
			.addMapping(Recipe::getComplexity, RecipeInfoDto::setComplexity)
			.addMapping(Recipe::getDescription, RecipeInfoDto::setDescription)
			.addMapping(Recipe::getInstructions, RecipeInfoDto::setInstructions)
			.addMapping(Recipe::getPrice, RecipeInfoDto::setPrice)
			.addMapping(Recipe::getDurationHours, RecipeInfoDto::setDurationHours)
			.addMapping(Recipe::getDurationMinutes, RecipeInfoDto::setDurationMinutes)
			.addMapping(Recipe::getPortion, RecipeInfoDto::setPortion)
			.addMapping(Recipe::getCalories, RecipeInfoDto::setCalories)
			.addMapping(Recipe::getFats, RecipeInfoDto::setFats)
			.addMapping(Recipe::getCarbohydrates, RecipeInfoDto::setCarbohydrates)
			.addMapping(Recipe::getProteins, RecipeInfoDto::setProteins)
			.setPostConverter(toDto());
	}

	private Converter<Recipe, RecipeInfoDto> toDto() {
		return mappingContext -> {
			Recipe source = mappingContext.getSource();
			RecipeInfoDto dto = mappingContext.getDestination();

			dto.setRecipeImageUuid(source.getRecipeImage().getUuid().toString());
			dto.setCategory(source.getCategory().getName());
			dto.setRating(
				source.getRatings().stream()
					.mapToInt(RecipeRating::getRating)
					.average()
					.orElse(0)
			);

			return dto;
		};
	}

	private Converter<Recipe, RecipeShortDto> toDtoShort() {
		return mappingContext -> {
			Recipe source = mappingContext.getSource();
			RecipeShortDto dto = mappingContext.getDestination();

			dto.setCategory(source.getCategory().getName());
			dto.setRating(
				source.getRatings().stream()
					.mapToInt(RecipeRating::getRating)
					.average()
					.orElse(0)
			);

			return dto;
		};
	}

	public RecipeInfoDto toDto(Recipe source) {
		return mapper.map(source, RecipeInfoDto.class);
	}

	public RecipeShortDto toShortDto(Recipe source) {
		return mapper.map(source, RecipeShortDto.class);
	}

	public UserInfoDto toDto(UserEntity source) {
		return mapper.map(source, UserInfoDto.class);
	}

	public RecipeCategoryDto toDto(RecipeCategory source) {
		return mapper.map(source, RecipeCategoryDto.class);
	}

}
