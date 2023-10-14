package org.study.domain.usecases.ingredient;

import org.study.domain.entities.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

public class UpdateIngredientUseCase {
    private final IngredientRepository repository;

    public UpdateIngredientUseCase(IngredientRepository ingredientRepository) {
        this.repository = ingredientRepository;
    }

    public Result<Integer> invoke (IngredientModel ingredientModel) {
        return repository.updateIngredient(ingredientModel);
    }
}
