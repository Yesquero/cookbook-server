package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.recipebook.domain.compositeKeys.RecipeRatingPK;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "recipe_rating")
@IdClass(RecipeRatingPK.class)
public class RecipeRating {
    @Id
    @Column(name = "fk_recipe")
    private UUID fkRecipe;

    @Id
    @Column(name = "fk_user")
    private UUID fkUser;

    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "fk_user", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "fk_recipe", insertable = false, updatable = false)
    private Recipe recipe;

}
