package org.study.domain.usecases.ingredient;

import org.study.domain.models.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

public class ExtractIngredientByIdUseCase {

    private final IngredientRepository repository;

    public ExtractIngredientByIdUseCase(IngredientRepository repository) {
        this.repository = repository;
    }

    public Result<IngredientModel> invoke (int id) {
        return repository.extractIngredientModelById(id);
    }
}
