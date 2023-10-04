package org.study.data.sources.relation;

import org.study.data.entity.RelationIngredientRecipeEntity;
import org.study.data.exceptions.*;

import java.util.List;

public interface RelationDataSourceInterface {
    int insertRelationRecordRecipeIngredientById(int recipeId, int ingredientId) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    RelationIngredientRecipeEntity extractRelationByRecipeIdAndIngredientId(int recipeId, int ingredientId) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException;

    List<RelationIngredientRecipeEntity> extractListOfRelationIngredientEntitiesByRecipeId(int recipeId) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException;

    List<RelationIngredientRecipeEntity> extractListOfRelationIngredientEntitiesByIngredientId(int ingredientId) throws FailedExecuteException, FailedStatementException, FailedConnectingException, UnexpectedException;


}
