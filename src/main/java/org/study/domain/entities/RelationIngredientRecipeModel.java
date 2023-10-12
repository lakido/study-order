package org.study.domain.entities;

public record RelationIngredientRecipeModel(int id, int idRecipe, int idIngredient) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RelationIngredientRecipeModel tempModel)) return false;

        return (tempModel.id() == this.id() &&
                tempModel.idIngredient() == this.idIngredient() &&
                tempModel.idRecipe() == this.idRecipe());
    }
}
