package org.study.domain.models;

import org.study.data.entities.IngredientEntity;

public record IngredientModel(int id, String name, int calories, int weight, String recommendation) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IngredientModel tempModel)) return false;

        return tempModel.id() == this.id() &&
                tempModel.calories() == this.calories() &&
                tempModel.name().equals(this.name()) &&
                tempModel.weight() == this.weight() &&
                tempModel.recommendation().equals(this.recommendation());
    }

    public static IngredientEntity mapIngredientModelToEntity(IngredientModel ingredientModel) {
        return new IngredientEntity(
                ingredientModel.name,
                ingredientModel.calories(),
                ingredientModel.weight(),
                ingredientModel.recommendation()
        );
    }
}
