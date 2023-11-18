package org.study.domain.usecases.ingredient;

import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

public class ExtractNextAvailableIdForIngredientUseCase {
    private final IngredientRepository repository;

    public ExtractNextAvailableIdForIngredientUseCase(IngredientRepository ingredientRepository) {
        this.repository = ingredientRepository;
    }

    public Result<Integer> invoke() {
        return repository.extractNextAvailableIdForIngredient();
    };
}
