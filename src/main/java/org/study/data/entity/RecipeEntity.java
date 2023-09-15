package org.study.data.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeEntity {

    private String name;
    private int id;
    private String category;
    private int popularity;
    private int age_preferences;

    public RecipeEntity(ResultSet resultSet) {
        try {
            this.name = resultSet.getString("name");
            this.id = resultSet.getInt("id");
            this.category = resultSet.getString("category");
            this.popularity = resultSet.getInt("popularity");
            this.age_preferences = resultSet.getInt("age_preferences");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public RecipeEntity(int id, String name, String category, int popularity, int age_preferences) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.popularity = popularity;
        this.age_preferences = age_preferences;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {return category;}

    public int getPopularity() {return popularity;}

    public int getAge_preferences() {
        return age_preferences;
    }

    public String getName() {return name;}
}
