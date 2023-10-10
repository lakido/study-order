package org.study.domain.repository;

import org.study.data.entities.RecipeEntity;
import org.study.utils.Result;

public interface RecipeRepository {

    Result<Integer> updateRecipe(RecipeEntity recipeEntity);

    Result<Integer> deleteRecipeByName(String name);

    Result<Integer> deleteRecipeById(int id);

    Result<RecipeEntity> extractRecipeEntityByName(String name);

    Result<RecipeEntity> extractRecipeEntityById(int id);

    Result<Integer> insertRecipe(String name, String category, int popularity, int agePreferences);
}