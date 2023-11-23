package org.study.domain.usecases.recipe;

import org.study.domain.models.RecipeModel;
import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

import java.util.List;

public class ExtractRecipeListUseCase {
    private final RecipeRepository recipeRepository;

    public ExtractRecipeListUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Result<List<RecipeModel>> invoke(String category, int popularity, int agePreferences) {
        return recipeRepository.extractRecipeList(category, popularity, agePreferences);
    }
}
