package org.study.domain.entities;

import org.study.data.entities.RelationIngredientRecipeEntity;

public record RelationIngredientRecipeModel(int id, int idRecipe, int idIngredient) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RelationIngredientRecipeModel tempModel)) return false;

        return (tempModel.id() == this.id() &&
                tempModel.idIngredient() == this.idIngredient() &&
                tempModel.idRecipe() == this.idRecipe());
    }

    public static RelationIngredientRecipeEntity mapRelationModelToEntity(RelationIngredientRecipeEntity relationIngredientRecipeEntity) {
        return new RelationIngredientRecipeEntity(
                relationIngredientRecipeEntity.getIdRecipe(),
                relationIngredientRecipeEntity.getIdIngredient()
        );
    }
}
