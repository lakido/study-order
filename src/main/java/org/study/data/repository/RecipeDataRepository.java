package org.study.data.repository;

import org.study.data.entity.RecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.exceptions.Error;
import org.study.data.sources.recipe.RecipeDataSource;
import org.study.ui.repository.RecipeRepository;
import org.study.utils.Result;

public class RecipeDataRepository implements RecipeRepository {

    //TODO this is a component that should implement interaction data layer with other program layers
    //TODO other program layer should not have an ability to use classes from data layer
    //TODO this class should implement all operations that data layer can execute (insert, deletion, modifying, extracting)

    //TODO make Result class parameterized with bool. if correct result of updating db (x>0), then true
    private final RecipeDataSource recipeDataSource;

    public RecipeDataRepository(RecipeDataSource recipeDataSource) {
        this.recipeDataSource = recipeDataSource;
    }

    @Override
    public Result<Integer> updateRecipe(RecipeEntity recipeEntity) {
        try {
            return new Result.Correct<>(recipeDataSource.updateRecipe(recipeEntity));
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
            return new Result.Correct<>(recipeDataSource.deleteRecipeByName(name));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException exception) {
            throw new RuntimeException(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> deleteRecipeById(int id) {
        try {
            return new Result.Correct<>(recipeDataSource.deleteRecipeById(id));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException exception) {
            throw new RuntimeException(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<RecipeEntity> extractRecipeEntityByName(String name) {
        try {
            return new Result.Correct<>(recipeDataSource.extractRecipeEntityByName(name));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<RecipeEntity> extractRecipeEntityById(int id) {
        try {
            return new Result.Correct<>(recipeDataSource.extractRecipeEntityById(id));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertRecipe(String name, String category, int popularity, int agePreferences) {
        try {
            return new Result.Correct<>(recipeDataSource.insertRecipe(new RecipeEntity(name, category, popularity, agePreferences)));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }
}
