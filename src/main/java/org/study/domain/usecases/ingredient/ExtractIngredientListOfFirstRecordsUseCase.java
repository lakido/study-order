package org.study.domain.usecases.ingredient;

import org.study.domain.models.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

import java.util.List;

public class ExtractIngredientListOfFirstRecordsUseCase {
    private final IngredientRepository ingredientRepository;

    public ExtractIngredientListOfFirstRecordsUseCase(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Result<List<IngredientModel>> invoke(int limit) {
        return ingredientRepository.extractListOfFirstRecords(limit);
    }
}
