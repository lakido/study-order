package org.study.domain.repository;

import org.study.domain.models.RecipeModel;
import org.study.utils.Result;

import java.util.List;

public interface RecipeRepository {

    Result<Integer> updateRecipe(RecipeModel recipeModel);

    Result<Integer> deleteRecipeByName(String name);

    Result<Integer> deleteRecipeById(int id);

    Result<RecipeModel> extractRecipeModelByName(String name);

    Result<RecipeModel> extractRecipeModelById(int id);

    Result<List<RecipeModel>> extractRecipeListOfFirstRecords(int limit);

    Result<Integer> insertRecipe(RecipeModel recipeModel);
}