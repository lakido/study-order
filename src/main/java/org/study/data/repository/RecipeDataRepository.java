package org.study.data.repository;

import org.study.data.entities.RecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.exceptions.Error;
import org.study.data.sources.recipe.RecipeDataSource;
import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

public class RecipeDataRepository implements RecipeRepository {

    //TODO this is a component that should implement interaction data layer with other program layers
    //TODO other program layer should not have an ability to use classes from data layer
    //TODO this class should implement all operations that data layer can execute (insert, deletion, modifying, extracting)

    //TODO all classes in data layer must be a singleton object (except Exceptions and entities)
    private static RecipeDataSource dataSource;

    private static RecipeDataRepository recipeDataRepositorySingleton;

    public RecipeDataRepository() {}

    public static RecipeDataRepository getInstance(RecipeDataSource recipeDataSource) {
        if (recipeDataRepositorySingleton == null) {
            recipeDataRepositorySingleton = new RecipeDataRepository();
            dataSource = recipeDataSource;
        }

        return recipeDataRepositorySingleton;
    }

    @Override
    public Result<Integer> updateRecipe(RecipeEntity recipeEntity) {
        try {
            return new Result.Correct<>(dataSource.updateRecipe(recipeEntity));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception e) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> deleteRecipeByName(String name) {
        try {
            return new Result.Correct<>(dataSource.deleteRecipeByName(name));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> deleteRecipeById(int id) {
        try {
            return new Result.Correct<>(dataSource.deleteRecipeById(id));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<RecipeEntity> extractRecipeEntityByName(String name) {
        try {
            return new Result.Correct<>(dataSource.extractRecipeEntityByName(name));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<RecipeEntity> extractRecipeEntityById(int id) {
        try {
            return new Result.Correct<>(dataSource.extractRecipeEntityById(id));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertRecipe(String name, String category, int popularity, int agePreferences) {
        try {
            return new Result.Correct<>(dataSource.insertRecipe(new RecipeEntity(name, category, popularity, agePreferences)));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }
}
