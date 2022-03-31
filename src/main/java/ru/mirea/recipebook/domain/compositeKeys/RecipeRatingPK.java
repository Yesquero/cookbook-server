package ru.mirea.recipebook.domain.compositeKeys;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class RecipeRatingPK implements Serializable {
    private UUID fkRecipe;
    private UUID fkUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeRatingPK that = (RecipeRatingPK) o;
        return fkRecipe.equals(that.fkRecipe) && fkUser.equals(that.fkUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkRecipe, fkUser);
    }
}
