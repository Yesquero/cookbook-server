package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "recipe_step")
public class RecipeStep extends BaseEntityWithUuid {

    @ManyToOne
    @JoinColumn(name = "fk_recipe")
    private Recipe recipe;

    @OneToOne
    @JoinColumn(name = "fk_image", referencedColumnName = "uuid")
    private Image stepImage;

    private Integer stepNumber;

    private String description;

    private Duration duration;

    @OneToMany(mappedBy = "recipeStep")
    private Set<IngredientStep> ingredients = new HashSet<>();
}
