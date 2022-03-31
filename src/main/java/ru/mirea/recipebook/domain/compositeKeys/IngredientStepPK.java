package ru.mirea.recipebook.domain.compositeKeys;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class IngredientStepPK implements Serializable {
    private UUID fkRecipeStep;
    private UUID fkIngredient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientStepPK that = (IngredientStepPK) o;
        return fkRecipeStep.equals(that.fkRecipeStep) && fkIngredient.equals(that.fkIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkRecipeStep, fkIngredient);
    }
}
