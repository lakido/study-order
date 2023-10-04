package org.study.ui.repository;

import org.study.data.entity.RelationIngredientRecipeEntity;
import org.study.utils.Result;

import java.util.List;

public interface RelationRepository {
    Result<RelationIngredientRecipeEntity> extractRelationByIngredientIdAndRecipeId (int recipeId, int ingredientId);

    Result<List<RelationIngredientRecipeEntity>> extractRelationToListByRecipe (int recipeId);

    Result<List<RelationIngredientRecipeEntity>> extractRelationToListByIngredient (int ingredientId);

    Result<Integer> insertRelation(int recipeId, int ingredientId);
}
