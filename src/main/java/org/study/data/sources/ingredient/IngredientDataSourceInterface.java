package org.study.data.sources.ingredient;

import org.study.data.entities.IngredientEntity;
import org.study.data.exceptions.*;

import java.util.List;

public interface IngredientDataSourceInterface {
    int updateIngredient(IngredientEntity ingredientEntity) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int deleteIngredientById(int id) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int deleteIngredientByName(String name) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    IngredientEntity extractIngredientEntityById(int id) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    IngredientEntity extractIngredientEntityByName(String name) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    List<IngredientEntity> extractListOfFirstRecords(int limit) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    List<IngredientEntity> extractIngredientListByRecipeId(int recipeId) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    List<IngredientEntity> extractIngredientListByRecipeName(String name) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    int insertIngredient(IngredientEntity ingredientEntity) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int insertListOfIngredients(List<IngredientEntity> ingredientEntityList) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;
}
