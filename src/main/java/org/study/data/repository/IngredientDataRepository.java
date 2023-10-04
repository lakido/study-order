package org.study.data.repository;

import org.study.data.entity.IngredientEntity;
import org.study.data.exceptions.*;
import org.study.data.exceptions.Error;
import org.study.data.sources.ingredient.IngredientDataSource;
import org.study.ui.repository.IngredientRepository;
import org.study.utils.Result;

import javax.naming.spi.DirStateFactory;
import java.util.List;

public class IngredientDataRepository implements IngredientRepository {

    private final IngredientDataSource ingredientDataSource;

    public IngredientDataRepository(IngredientDataSource ingredientDataSource) {
        this.ingredientDataSource = ingredientDataSource;
    }

    @Override
    public Result<Integer> updateIngredient(int id, String newName, String newRecommendation, int newCalories, int newWeight) {
        try {
            return new Result.Correct<>(ingredientDataSource.updateIngredient(new IngredientEntity(id, newName, newCalories, newWeight, newRecommendation)));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> deleteIngredientById(int id) {
        try {
            return new Result.Correct<>(ingredientDataSource.deleteIngredientById(id));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> deleteIngredientByName(String name) {
        try {
            return new Result.Correct<>(ingredientDataSource.deleteIngredientByName(name));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<IngredientEntity> extractIngredientEntityById(int id) {
        try {
            return new Result.Correct<>(ingredientDataSource.extractRecipeEntityById(id));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<IngredientEntity> extractIngredientEntityByName(String name) {
        try {
            return new Result.Correct<>(ingredientDataSource.extractRecipeEntityByName(name));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertIngredient(String name, int calories, int weight, String recommendation) {
        try {
            return new Result.Correct<>(ingredientDataSource.insertIngredient(new IngredientEntity(name, calories, weight, recommendation)));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertListOfIngredients(List<IngredientEntity> ingredientEntityList) {
        try {
            return new Result.Correct<>(ingredientDataSource.insertListOfIngredients(ingredientEntityList));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }
}
