package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.recipebook.domain.converter.RecipeStatusConverter;
import ru.mirea.recipebook.domain.enumeration.RecipeStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "recipe")
public class Recipe extends BaseEntityWithUuid {

	@ManyToOne
	@JoinColumn(name = "fk_recipe_category")
	private RecipeCategory category;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_image", referencedColumnName = "uuid")
	private Image recipeImage;

	private String name;

	private Integer complexity;

	private String description;

	private String instructions;

	@Column(precision = 9, scale = 3)
	private BigDecimal price;

	private Integer duration;

	private String portion;

	@Column(precision = 9, scale = 3)
	private BigDecimal calories;

	@Column(precision = 7, scale = 3)
	private BigDecimal fats;

	@Column(precision = 7, scale = 3)
	private BigDecimal proteins;

	@Column(precision = 7, scale = 3)
	private BigDecimal carbohydrates;

	@Convert(converter = RecipeStatusConverter.class)
	private RecipeStatus status;

	@ManyToMany(mappedBy = "favoriteRecipes")
	private Set<UserEntity> inFavorites = new HashSet<>();

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private Set<RecipeRating> ratings;

}
