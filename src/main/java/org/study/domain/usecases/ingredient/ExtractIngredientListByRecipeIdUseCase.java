package org.study.domain.usecases.ingredient;

import org.study.domain.models.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

import java.util.List;

public class ExtractIngredientListByRecipeIdUseCase {
    private final IngredientRepository ingredientRepository;

    public ExtractIngredientListByRecipeIdUseCase(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Result<List<IngredientModel>> invoke(int recipeId) {
        return ingredientRepository.extractIngredientListByRecipeId(recipeId);
    }
}
