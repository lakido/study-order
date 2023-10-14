package org.study.domain.usecases.ingredient;

import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

public class DeleteIngredientByNameUseCase {
    private final IngredientRepository repository;

    public DeleteIngredientByNameUseCase(IngredientRepository ingredientRepository) {
        this.repository = ingredientRepository;
    }

    public Result<Integer> invoke(String name) {
        return repository.deleteIngredientByName(name);
    }
}
