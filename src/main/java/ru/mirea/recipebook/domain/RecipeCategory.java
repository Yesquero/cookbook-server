package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "recipe_category")
public class RecipeCategory extends BaseEntityWithUuid {

	private String name;

	@OneToMany(mappedBy = "category")
	private Set<Recipe> recipes = new HashSet<>();

}
