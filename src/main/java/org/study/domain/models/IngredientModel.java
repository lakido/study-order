package org.study.domain.models;

import org.study.data.entities.IngredientEntity;

import java.util.Objects;

public final class IngredientModel {
    private final int id;
    private final String name;
    private final int calories;
    private final int weight;
    private final String recommendation;

    public IngredientModel(int id, String name, int calories, int weight, String recommendation) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.weight = weight;
        this.recommendation = recommendation;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IngredientModel tempModel)) return false;

        return tempModel.getId() == this.getId() &&
                tempModel.getCalories() == this.getCalories() &&
                tempModel.getName().equals(this.getName()) &&
                tempModel.getWeight() == this.getWeight() &&
                tempModel.getRecommendation().equals(this.getRecommendation());
    }

    public static IngredientEntity mapIngredientModelToEntity(IngredientModel ingredientModel) {
        return new IngredientEntity(
                ingredientModel.name,
                ingredientModel.getCalories(),
                ingredientModel.getWeight(),
                ingredientModel.getRecommendation()
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getWeight() {
        return weight;
    }

    public String getRecommendation() {
        return recommendation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, calories, weight, recommendation);
    }

    @Override
    public String toString() {
        return "IngredientModel[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "calories=" + calories + ", " +
                "weight=" + weight + ", " +
                "recommendation=" + recommendation + ']';
    }

}
