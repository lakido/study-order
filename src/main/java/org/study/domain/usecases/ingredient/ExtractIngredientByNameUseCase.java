package org.study.domain.usecases.ingredient;

import org.study.domain.entities.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

public class ExtractIngredientByNameUseCase {
    private final IngredientRepository repository;

    public ExtractIngredientByNameUseCase(IngredientRepository repository) {
        this.repository = repository;
    }

    public Result<IngredientModel> invoke (String name) {
        return repository.extractIngredientModelByName(name);
    }
}
