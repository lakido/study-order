package org.study.data.sources.ingredient;

import org.study.data.entity.IngredientEntity;
import org.study.data.entity.RecipeEntity;
import org.study.data.exceptions.*;

import java.util.List;

public interface IngredientDataSourceInterface {
    int updateIngredient(IngredientEntity ingredientEntity) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int deleteIngredient(int id) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int deleteIngredient(String name) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    IngredientEntity extractRecipeEntityById(int id) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    IngredientEntity extractRecipeEntityByName(String name) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    int insertIngredient(IngredientEntity ingredientEntity) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int insertListOfIngredients(List<IngredientEntity> ingredientEntityList) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;
}
