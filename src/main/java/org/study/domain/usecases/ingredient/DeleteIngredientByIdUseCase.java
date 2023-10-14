package org.study.domain.usecases.ingredient;

import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

public class DeleteIngredientByIdUseCase {
    private final IngredientRepository repository;

    public DeleteIngredientByIdUseCase(IngredientRepository ingredientRepository) {
        this.repository = ingredientRepository;
    }

    public Result<Integer> invoke(int id) {
        return repository.deleteIngredientById(id);
    }
}
