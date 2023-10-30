package org.study.domain.usecases.recipe;

import org.study.domain.models.RecipeModel;
import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

public class InsertRecipeUseCase {

    private final RecipeRepository repository;

    public InsertRecipeUseCase(RecipeRepository recipeRepository) {
        repository = recipeRepository;
    }

    public Result<Integer> invoke(RecipeModel recipeModel) {
        return repository.insertRecipe(recipeModel);
    }
}
