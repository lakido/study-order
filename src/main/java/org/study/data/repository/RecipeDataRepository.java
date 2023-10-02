package org.study.data.repository;

import org.study.data.sources.recipe.RecipeDataSource;
import org.study.ui.repository.RecipeRepository;

public class RecipeDataRepository implements RecipeRepository {

    //TODO this is a component that should implement interaction data layer with other program layers
    //TODO other program layer should not have an ability to use classes from data layer
    //TODO this class should implement all operations that data layer can execute (insert, deletion, modifying, extracting)

    //TODO WARNING i need to implement container (watch vladik's github: android useful features, container);
    //TODO WARNING как передать функцию в другую функцию и вызвать её внутри - узнать (смотерть класс Test в org.study)

    //TODO implement function, that will create the wrapper of "Result" class for every object from data sources

    //TODO implement class "Result", that will store information about current transaction. instances of this class will be creating every time
    // when accessing the database happens, this class should locate in high layer (ort.study.utils)
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
