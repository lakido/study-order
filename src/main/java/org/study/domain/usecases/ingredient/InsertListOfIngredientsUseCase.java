package org.study.domain.usecases.ingredient;

import org.study.data.entities.IngredientEntity;
import org.study.domain.entities.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

import java.util.List;

public class InsertListOfIngredientsUseCase {
    private final IngredientRepository repository;

    public InsertListOfIngredientsUseCase(IngredientRepository ingredientRepository) {
        this.repository = ingredientRepository;
    }

    public Result<Integer> invoke(List<IngredientModel> ingredientModels) {
        return repository.insertListOfIngredients(ingredientModels);
    }
}
