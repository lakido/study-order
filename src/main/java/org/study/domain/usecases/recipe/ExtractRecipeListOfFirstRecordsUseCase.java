package org.study.domain.usecases.recipe;

import org.study.domain.models.RecipeModel;
import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

import java.util.List;

public class ExtractRecipeListOfFirstRecordsUseCase {
    private final RecipeRepository recipeRepository;

    public ExtractRecipeListOfFirstRecordsUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Result<List<RecipeModel>> invoke(int limit) {
        return recipeRepository.extractRecipeListOfFirstRecords(limit);
    }
}
