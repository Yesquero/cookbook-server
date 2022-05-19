package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.recipebook.domain.converter.UserRoleConverter;
import ru.mirea.recipebook.domain.converter.UserStatusConverter;
import ru.mirea.recipebook.domain.enumeration.UserRole;
import ru.mirea.recipebook.domain.enumeration.UserStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user_entity")
public class UserEntity extends BaseEntityWithUuid {

	private String login;

	private String password;

	private String nickname;

	@Convert(converter = UserRoleConverter.class)
	private UserRole userRole;

	@Convert(converter = UserStatusConverter.class)
	private UserStatus status;

	private String firstName;

	private String lastName;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
		name = "favorite_recipe",
		joinColumns = @JoinColumn(name = "fk_user"),
		inverseJoinColumns = @JoinColumn(name = "fk_recipe")
	)
	private Set<Recipe> favoriteRecipes = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<RecipeRating> recipeRatings = new HashSet<>();

	public void addToFavorites(Recipe recipe) {
		favoriteRecipes.add(recipe);
		recipe.getInFavorites().add(this);
	}

	public void deleteFromFavorites(Recipe recipe) {
		favoriteRecipes.remove(recipe);
		recipe.getInFavorites().remove(this);
	}

}
