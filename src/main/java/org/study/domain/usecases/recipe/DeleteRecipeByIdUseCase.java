package org.study.domain.usecases.recipe;

import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

public class DeleteRecipeByIdUseCase {
    private final RecipeRepository repository;

    public DeleteRecipeByIdUseCase(RecipeRepository recipeRepository) {
        repository = recipeRepository;
    }

    public Result<Integer> invoke(int id) {
        return repository.deleteRecipeById(id);
    }
}
