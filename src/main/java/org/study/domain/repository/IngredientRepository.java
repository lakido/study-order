package org.study.domain.repository;

import org.study.domain.entities.IngredientModel;
import org.study.utils.Result;

import java.util.List;

public interface IngredientRepository {

    Result<Integer> updateIngredient(IngredientModel ingredientModel);

    Result<Integer> deleteIngredientById(int id);

    Result<Integer> deleteIngredientByName(String name);

    Result<IngredientModel> extractIngredientModelById(int id);

    Result<IngredientModel> extractIngredientModelByName(String name);

    Result<Integer> insertIngredient(IngredientModel ingredientModel);

    Result<Integer> insertListOfIngredients(List<IngredientModel> ingredientModels);
}
