package org.study.data.repository;

import org.study.data.entities.RecipeEntity;
import org.study.data.exceptions.Error;
import org.study.data.exceptions.*;
import org.study.data.sources.recipe.RecipeDataSource;
import org.study.domain.models.RecipeModel;
import org.study.domain.repository.RecipeRepository;
import org.study.utils.Result;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeDataRepository implements RecipeRepository {
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
    public Result<Integer> updateRecipe(RecipeModel recipeModel) {
        try {
            return new Result.Correct<>(dataSource.updateRecipe(RecipeModel.mapRecipeModelToEntity(recipeModel)));
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
    public Result<RecipeModel> extractRecipeModelByName(String name) {
        try {
            return new Result.Correct<>(dataSource.extractRecipeEntityByName(name)).map(RecipeEntity::mapEntityToModel);
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<RecipeModel> extractRecipeModelById(int id) {
        try {
            return new Result.Correct<>(dataSource.extractRecipeEntityById(id)).map(RecipeEntity::mapEntityToModel);
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<RecipeModel>> extractRecipeListOfFirstRecords(int limit) {
        try {
            return new Result.Correct<>(dataSource.extractRecipeListOfFirstRecords(limit).stream().map(RecipeEntity::mapEntityToModel).collect(Collectors.toList()));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<RecipeModel>> extractRecipeList(String category, int popularity, int agePreferences) {
        try {
            return new Result.Correct<>(dataSource.extractRecipeList(category, popularity, agePreferences).stream().map(RecipeEntity::mapEntityToModel).collect(Collectors.toList()));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> extractNextAvailableIdForRecipe() {
        try {
            return new Result.Correct<>(dataSource.extractNextAvailableIdForRecipe());
        } catch (UnexpectedException | FailedExecuteException | FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception e) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertRecipe(RecipeModel recipeModel) {
        try {
            return new Result.Correct<>(dataSource.insertRecipe(new RecipeEntity(
                    recipeModel.getName(),
                    recipeModel.getCategory(),
                    recipeModel.getPopularity(),
                    recipeModel.getAgePreferences()))
            );
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }
}
