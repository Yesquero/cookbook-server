package ru.mirea.recipebook.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeShortDto {

	private String uuid;
	private String name;
	private String category;
	private BigDecimal price;
	private Integer duration;
	private Double rating;

}
