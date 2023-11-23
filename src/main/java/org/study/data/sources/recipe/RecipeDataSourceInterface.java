package org.study.data.sources.recipe;

import org.study.data.entities.RecipeEntity;
import org.study.data.exceptions.*;

import java.util.List;

public interface RecipeDataSourceInterface {

    int updateRecipe(RecipeEntity recipeEntity) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int deleteRecipeByName(String name) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    int deleteRecipeById(int id) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    RecipeEntity extractRecipeEntityById(int id) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    RecipeEntity extractRecipeEntityByName(String name) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    List<RecipeEntity> extractRecipeListOfFirstRecords(int limit) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    List<RecipeEntity> extractRecipeList( String category, int popularity, int agePreferences) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    Integer extractNextAvailableIdForRecipe() throws UnexpectedException, FailedExecuteException, FailedConnectingException;

    int insertRecipe(RecipeEntity recipeEntity) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

}
