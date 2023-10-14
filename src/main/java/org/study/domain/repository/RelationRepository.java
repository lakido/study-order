package org.study.domain.repository;

import org.study.domain.entities.RelationIngredientRecipeModel;
import org.study.utils.Result;

import java.util.List;

public interface RelationRepository {
    Result<RelationIngredientRecipeModel> extractRelationByIngredientIdAndRecipeId(int recipeId, int ingredientId);

    Result<List<RelationIngredientRecipeModel>> extractRelationToListByRecipe(int recipeId);

    Result<List<RelationIngredientRecipeModel>> extractRelationToListByIngredient(int ingredientId);

    Result<Integer> insertRelation(int recipeId, int ingredientId);
}
