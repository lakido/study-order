package org.study.data.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientEntity {

    private int id;
    private String name;
    private int calories;
    private int weight;
    private String recommendation;

    public IngredientEntity(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("id");
            this.name = resultSet.getString("name");
            this.calories = resultSet.getInt("weight");
            this.recommendation = resultSet.getString("recommendation");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public IngredientEntity(int id, String name, int calories, int weight, String recommendation) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.weight = weight;
        this.recommendation = recommendation;
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
