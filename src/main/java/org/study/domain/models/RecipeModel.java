package org.study.domain.models;

import org.study.data.entities.RecipeEntity;

import java.util.Objects;

public final class RecipeModel {
    private final int id;
    private final String name;
    private final String category;
    private final int popularity;
    private final int agePreferences;

    public RecipeModel(int id, String name, String category, int popularity, int agePreferences) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.popularity = popularity;
        this.agePreferences = agePreferences;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecipeModel tempModel)) return false;

        return tempModel.getId() == this.getId() &&
                tempModel.getName().equals(this.getName()) &&
                tempModel.getCategory().equals(this.getCategory()) &&
                tempModel.getAgePreferences() == (this.getAgePreferences()) &&
                tempModel.getPopularity() == (this.getPopularity());
    }

    public static RecipeEntity mapRecipeModelToEntity(RecipeModel recipeModel) {
        return RecipeEntity.getRecipeEntity(
                recipeModel.getId(),
                recipeModel.getName(),
                recipeModel.getCategory(),
                recipeModel.getPopularity(),
                recipeModel.getPopularity()
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getAgePreferences() {
        return agePreferences;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, popularity, agePreferences);
    }

    @Override
    public String toString() {
        return "RecipeModel[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "category=" + category + ", " +
                "popularity=" + popularity + ", " +
                "agePreferences=" + agePreferences + ']';
    }

}
