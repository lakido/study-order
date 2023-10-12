package org.study.domain.entities;

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
}
