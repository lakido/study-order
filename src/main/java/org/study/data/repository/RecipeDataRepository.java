package org.study.data.repository;

import org.study.data.sources.RecipeDataSource;
import org.study.ui.repository.RecipeRepository;

public class RecipeDataRepository implements RecipeRepository {

    //this is a component that should implement interaction data layer with other program layers
    //other program layer should not have an ability to use classes from data layer
    //this class should implement all operations that data layer can execute (insert, deletion, modifying, extracting)
    private final RecipeDataSource recipeDataSource;
    public RecipeDataRepository(RecipeDataSource recipeDataSource) {
        this.recipeDataSource = recipeDataSource;
    }
    @Override
    public long insertRecipe() {
        return 0;
    }

    @Override
    public long insertIngredient() {
        return 0;
    }
}
