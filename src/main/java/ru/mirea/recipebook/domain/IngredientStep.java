package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.recipebook.domain.compositeKeys.IngredientStepPK;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "step_ingredient")
@IdClass(IngredientStepPK.class)
public class IngredientStep {
    @Id
    @Column(name = "fk_recipe_step")
    private UUID fkRecipeStep;

    @Id
    @Column(name = "fk_ingredient")
    private UUID fkIngredient;

    @ManyToOne
    @JoinColumn(name = "fk_ingredient", insertable = false, updatable = false)
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "fk_recipe_step", insertable = false, updatable = false)
    private RecipeStep recipeStep;
}
