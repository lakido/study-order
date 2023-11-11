package org.study.data.repository;

import org.study.data.entities.IngredientEntity;
import org.study.data.exceptions.Error;
import org.study.data.exceptions.*;
import org.study.data.sources.ingredient.IngredientDataSource;
import org.study.domain.models.IngredientModel;
import org.study.domain.repository.IngredientRepository;
import org.study.utils.Result;

import java.util.List;
import java.util.stream.Collectors;

public class IngredientDataRepository implements IngredientRepository {

    private static IngredientDataSource dataSource;

    private static IngredientDataRepository ingredientDataRepositorySingleton;

    private IngredientDataRepository() {}

    public static IngredientDataRepository getInstance(IngredientDataSource ingredientDataSource) {
        if (ingredientDataRepositorySingleton == null) {
            ingredientDataRepositorySingleton = new IngredientDataRepository();
            dataSource = ingredientDataSource;
        }

        return ingredientDataRepositorySingleton;
    }

    @Override
    public Result<Integer> updateIngredient(IngredientModel ingredientModel) {
        try {
            return new Result.Correct<>(dataSource.updateIngredient(IngredientEntity.getIngredientEntity(
                    ingredientModel.getId(),
                    ingredientModel.getName(),
                    ingredientModel.getCalories(),
                    ingredientModel.getWeight(),
                    ingredientModel.getRecommendation()))
            );
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> deleteIngredientById(int id) {
        try {
            return new Result.Correct<>(dataSource.deleteIngredientById(id));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> deleteIngredientByName(String name) {
        try {
            return new Result.Correct<>(dataSource.deleteIngredientByName(name));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<IngredientModel> extractIngredientModelById(int id) {
        try {
            return new Result.Correct<>(dataSource.extractIngredientEntityById(id)).map(IngredientEntity::mapIngredientEntityToModel);
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<IngredientModel> extractIngredientModelByName(String name) {
        try {
            return new Result.Correct<>(dataSource.extractIngredientEntityByName(name)).map(IngredientEntity::mapIngredientEntityToModel);
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<IngredientModel>> extractListOfFirstRecords(int limit) {
        try {
            return new Result.Correct<>(dataSource.extractListOfFirstRecords(limit).stream().map(IngredientEntity::mapIngredientEntityToModel).collect(Collectors.toList()));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<IngredientModel>> extractIngredientListByRecipeId(int recipeId){
        try {
            return new Result.Correct<>(dataSource.extractIngredientListByRecipeId(recipeId).stream().map(IngredientEntity::mapIngredientEntityToModel).collect(Collectors.toList()));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<IngredientModel>> extractIngredientListByRecipeName(String recipeName) {
        try {
            return new Result.Correct<>(dataSource.extractIngredientListByRecipeName(recipeName).stream().map(IngredientEntity::mapIngredientEntityToModel).collect(Collectors.toList()));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertIngredient(IngredientModel ingredientModel) {
        try {
            return new Result.Correct<>(dataSource.insertIngredient(new IngredientEntity(
                    ingredientModel.getName(),
                    ingredientModel.getCalories(),
                    ingredientModel.getWeight(),
                    ingredientModel.getRecommendation()))
            );
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertListOfIngredients(List<IngredientModel> ingredientModels) {
        try {
            return new Result.Correct<>(dataSource.insertListOfIngredients(ingredientModels.stream()
                    .map(IngredientModel::mapIngredientModelToEntity)
                    .collect(Collectors.toList())
            ));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException |
                 FailedConnectingException e) {
            return new Result.Error<>(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }
}
