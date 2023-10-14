package org.study.domain.repository;

import org.study.domain.entities.RecipeModel;
import org.study.utils.Result;

public interface RecipeRepository {

    Result<Integer> updateRecipe(RecipeModel recipeModel);

    Result<Integer> deleteRecipeByName(String name);

    Result<Integer> deleteRecipeById(int id);

    Result<RecipeModel> extractRecipeModelByName(String name);

    Result<RecipeModel> extractRecipeModelById(int id);

    Result<Integer> insertRecipe(RecipeModel recipeModel);
}