package org.study.domain.models;

import org.study.data.entities.RecipeEntity;

public record RecipeModel(int id, String name, String category, int popularity, int agePreferences) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecipeModel tempModel)) return false;

        return tempModel.id() == this.id() &&
                tempModel.name().equals(this.name()) &&
                tempModel.category().equals(this.category()) &&
                tempModel.agePreferences() == (this.agePreferences()) &&
                tempModel.popularity() == (this.popularity());
    }

    public static RecipeEntity mapRecipeModelToEntity(RecipeModel recipeModel) {
        return new RecipeEntity(
                recipeModel.name(),
                recipeModel.category(),
                recipeModel.popularity(),
                recipeModel.agePreferences()
        );
    }
}
