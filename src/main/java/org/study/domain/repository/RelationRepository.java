package org.study.domain.repository;

import org.study.domain.models.RelationIngredientRecipeModel;
import org.study.utils.Result;

import java.util.List;

public interface RelationRepository {
    Result<RelationIngredientRecipeModel> extractRelation(int recipeId, int ingredientId);

    Result<List<RelationIngredientRecipeModel>> extractRelationsByRecipeId(int recipeId);

    Result<List<RelationIngredientRecipeModel>> extractRelationsByIngredientId(int ingredientId);

    Result<Integer> insertRelation(int recipeId, int ingredientId);
}
