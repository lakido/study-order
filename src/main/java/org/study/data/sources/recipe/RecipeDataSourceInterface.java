package org.study.data.sources.recipe;

import org.study.data.entity.RecipeEntity;
import org.study.data.exceptions.*;

public interface RecipeDataSourceInterface {

    int updateRecipe(RecipeEntity recipeEntity) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int deleteRecipeByName(String name) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int deleteRecipeById(int id) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    RecipeEntity extractRecipeEntityById(int id) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    RecipeEntity extractRecipeEntityByName(String name) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    int insertRecipe(RecipeEntity recipeEntity) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

}
