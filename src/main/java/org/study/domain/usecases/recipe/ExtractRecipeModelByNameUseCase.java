package org.study.domain.usecases.recipe;

import org.study.domain.models.RecipeModel;
import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

public class ExtractRecipeModelByNameUseCase {

    private final RecipeRepository repository;

    public ExtractRecipeModelByNameUseCase(RecipeRepository recipeRepository) {
        repository = recipeRepository;
    }

    public Result<RecipeModel> invoke(String name) {
        return repository.extractRecipeModelByName(name);
    }
}
