package org.study.data.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeEntity {

    private int id = 0;
    private final String name;
    private final String category;
    private final int popularity;
    private final int agePreferences;

    public RecipeEntity(String name, String category, int popularity, int agePreferences) {
        this.name = name;
        this.category = category;
        this.popularity = popularity;
        this.agePreferences = agePreferences;
    }

    private RecipeEntity(int id, String name, String category, int popularity, int agePreferences) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.popularity = popularity;
        this.agePreferences = agePreferences;
    }

    public static RecipeEntity getRecipeEntity(ResultSet resultSet) {
        try {
            return new RecipeEntity(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("category"),
                    resultSet.getInt("popularity"),
                    resultSet.getInt("agePreferences"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Wrong sql result!");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecipeEntity tempEntity)) return false;

        return this.getName().equals(tempEntity.getName()) &&
                this.getCategory().equals(tempEntity.getCategory()) &&
                this.getAgePreferences() == (tempEntity.getAgePreferences()) &&
                this.getPopularity() == (tempEntity.getPopularity());
    }

    public int getId() {
        return id;
    }

    public String getCategory() {return category;}

    public int getPopularity() {return popularity;}

    public int getAgePreferences() {
        return agePreferences;
    }

    public String getName() {return name;}
}
