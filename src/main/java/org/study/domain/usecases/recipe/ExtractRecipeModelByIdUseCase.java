package org.study.domain.usecases.recipe;

import org.study.domain.entities.RecipeModel;
import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

public class ExtractRecipeModelByIdUseCase {
    private final RecipeRepository repository;

    public ExtractRecipeModelByIdUseCase(RecipeRepository recipeRepository) {
        repository = recipeRepository;
    }

    public Result<RecipeModel> invoke(int id) {
        return repository.extractRecipeModelById(id);
    }
}
