package org.study.domain.usecases.recipe;

import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

public class ExtractNextAvailableIdForRecipeUseCase {
    private final RecipeRepository repository;

    public ExtractNextAvailableIdForRecipeUseCase(RecipeRepository recipeRepository) {
        this.repository = recipeRepository;
    }

    public Result<Integer> invoke() {
        return repository.extractNextAvailableIdForRecipe();
    }
}
