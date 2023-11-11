package org.study.domain.usecases.ingredient;

import org.study.domain.models.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

import java.util.List;

public class ExtractIngredientListByRecipeNameUseCase {
    private final IngredientRepository ingredientRepository;

    public ExtractIngredientListByRecipeNameUseCase(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Result<List<IngredientModel>> invoke(String recipeName) {
        return ingredientRepository.extractIngredientListByRecipeName(recipeName);
    }
}
