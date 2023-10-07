package org.study.data.entities;

import org.study.data.exceptions.FailedReadException;
import org.study.data.exceptions.UnexpectedException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelationIngredientRecipeEntity {

    private int id = 0;
    private final int idRecipe;
    private final int idIngredient;

    public RelationIngredientRecipeEntity(int idRecipe, int idIngredient) {
        this.idRecipe = idRecipe;
        this.idIngredient = idIngredient;
    }

    private RelationIngredientRecipeEntity(int id, int idRecipe, int idIngredient) {
        this.id = id;
        this.idRecipe = idRecipe;
        this.idIngredient = idIngredient;
    }

    public static RelationIngredientRecipeEntity getRelationIngredientRecipeEntity(
            ResultSet resultSet
    ) throws FailedReadException, UnexpectedException {
        try {
            return new RelationIngredientRecipeEntity(resultSet.getInt("id"),
                                                      resultSet.getInt("id_recipe"),
                                                      resultSet.getInt("id_ingredients"));
        } catch (SQLException exception) {
            throw new FailedReadException();
        } catch (Exception exception) {
            throw new UnexpectedException();
        }
    }

    public static RelationIngredientRecipeEntity getRelationIngredientRecipeEntity(int id, int idRecipe, int idIngredient) {
        return new RelationIngredientRecipeEntity(id, idRecipe, idIngredient);
    }

    public int getId() {
        return id;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    @Override
    public String toString() {
        return "Relation Ingredient-Recipe Entity: [" +
                "Record id: " + this.getId() + "; " +
                "Recipe id: " + this.getIdRecipe() + "; " +
                "Ingredient id: " + this.getIdIngredient() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RelationIngredientRecipeEntity relationIngredientRecipeEntity)) return false;

        return (this.getId() == relationIngredientRecipeEntity.getId() &&
                this.getIdIngredient() == relationIngredientRecipeEntity.getIdIngredient() &&
                this.getIdRecipe() == relationIngredientRecipeEntity.getIdRecipe());
    }
}
