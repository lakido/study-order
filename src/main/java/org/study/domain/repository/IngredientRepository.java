package org.study.domain.repository;

import org.study.domain.models.IngredientModel;
import org.study.utils.Result;

import java.util.List;

public interface IngredientRepository {

    Result<Integer> updateIngredient(IngredientModel ingredientModel);

    Result<Integer> deleteIngredientById(int id);

    Result<Integer> deleteIngredientByName(String name);

    Result<IngredientModel> extractIngredientModelById(int id);

    Result<IngredientModel> extractIngredientModelByName(String name);

    Result<List<IngredientModel>> extractListOfFirstRecords(int limit);

    Result<List<IngredientModel>> extractIngredientListByRecipeId(int recipeId);

    Result<List<IngredientModel>> extractIngredientListByRecipeName(String recipeName);

    Result<Integer> extractNextAvailableIdForIngredient();

    Result<Integer> insertIngredient(IngredientModel ingredientModel);

    Result<Integer> insertListOfIngredients(List<IngredientModel> ingredientModels);
}
