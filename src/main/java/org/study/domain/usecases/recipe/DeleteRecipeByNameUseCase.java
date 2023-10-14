package org.study.domain.usecases.recipe;

import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

public class DeleteRecipeByNameUseCase {

    private final RecipeRepository repository;

    public DeleteRecipeByNameUseCase(RecipeRepository recipeRepository) {
        repository = recipeRepository;
    }

    public Result<Integer> invoke(String name) {
        return repository.deleteRecipeByName(name);
    }
}
