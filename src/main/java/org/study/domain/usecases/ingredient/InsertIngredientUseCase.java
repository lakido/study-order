package org.study.domain.usecases.ingredient;

import org.study.domain.entities.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

public class InsertIngredientUseCase {
    private final IngredientRepository repository;

    public InsertIngredientUseCase(IngredientRepository ingredientRepository) {
        repository = ingredientRepository;
    }

    public Result<Integer> invoke(IngredientModel ingredientModel) {
        return repository.insertIngredient(ingredientModel);
    }
}
