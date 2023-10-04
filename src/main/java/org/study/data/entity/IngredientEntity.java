package org.study.data.entity;

import org.study.data.exceptions.FailedReadException;
import org.study.data.exceptions.UnexpectedException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientEntity {

    private int id = 0;
    private final String name;
    private final int calories;
    private final int weight;
    private final String recommendation;

    public IngredientEntity(String name, int calories, int weight, String recommendation) {
        this.name = name;
        this.calories = calories;
        this.weight = weight;
        this.recommendation = recommendation;
    }

    public IngredientEntity(int id, String name, int calories, int weight, String recommendation) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.weight = weight;
        this.recommendation = recommendation;
    }

    public static IngredientEntity getIngredientEntity(
            ResultSet resultSet
    ) throws FailedReadException, UnexpectedException {

        try {
            return new IngredientEntity(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("calories"),
                    resultSet.getInt("weight"),
                    resultSet.getString("recommendation"));
        } catch (SQLException e) {
            throw new FailedReadException();
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IngredientEntity tempEntity)) return false;

        return tempEntity.getId() == this.getId() &&
                tempEntity.getCalories() == this.getCalories() &&
                tempEntity.getName().equals(this.getName()) &&
                tempEntity.getWeight() == this.getWeight() &&
                tempEntity.getRecommendation().equals(this.getRecommendation());
    }

    @Override
    public String toString() {
        return "Ingredient Entity: [" +
                "Id: " + this.getId() + ", " +
                "Name: " + this.getName() + ", " +
                "Calories: " + this.getCalories() + ", " +
                "Weight: " + this.getWeight() + ", " +
                "Recommendation: " + this.getRecommendation() + "]";
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
}
