package org.study.domain.repository;

import org.study.domain.entities.IngredientModel;
import org.study.utils.Result;

import java.util.List;

public interface IngredientRepository {

    Result<Integer> updateIngredient(int id, String newName, String newRecommendation, int newCalories, int newWeight);

    Result<Integer> deleteIngredientById(int id);

    Result<Integer> deleteIngredientByName(String name);

    Result<IngredientModel> extractIngredientModelById(int id);

    Result<IngredientModel> extractIngredientModelByName(String name);

    Result<Integer> insertIngredient(String name, int calories, int weight, String recommendation);

    Result<Integer> insertListOfIngredients(List<IngredientModel> ingredientModels);
}
