package org.study.domain.usecases.ingredient;

import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

public class UpdateIngredientUseCase {
    private final IngredientRepository repository;

    public UpdateIngredientUseCase(IngredientRepository ingredientRepository) {
        this.repository = ingredientRepository;
    }

    public Result<Integer> invoke (int id, String newName, String newRecommendation, int newCalories, int newWeight) {
        return repository.updateIngredient(id, newName, newRecommendation, newCalories, newWeight);
    }
}
