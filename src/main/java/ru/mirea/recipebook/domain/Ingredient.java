package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "ingredient")
public class Ingredient extends BaseEntityWithUuid {

    private String name;

    private BigDecimal proteinPercentage;

    private BigDecimal fatsPercentage;

    private BigDecimal carbohydratesPercentage;

    @OneToMany(mappedBy = "ingredient")
    private Set<IngredientStep> inSteps = new HashSet<>();
}
