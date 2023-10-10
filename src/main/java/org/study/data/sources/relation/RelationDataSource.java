package org.study.data.sources.relation;

import org.study.data.entities.RelationIngredientRecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.extraction.RelationRecordExtractor;
import org.study.data.operations.inserting.RelationRecordInsertWorker;

import java.util.List;

public class RelationDataSource implements RelationDataSourceInterface {

    private final RelationRecordInsertWorker relationRecordInsertWorker;
    private final RelationRecordExtractor relationRecordExtractor;

    private static RelationDataSource relationDataSourceSingleton;

    private RelationDataSource(
            RelationRecordInsertWorker relationRecordInsertWorker,
            RelationRecordExtractor relationRecordExtractor
    ) {
        this.relationRecordInsertWorker = relationRecordInsertWorker;
        this.relationRecordExtractor = relationRecordExtractor;
    }

    public static RelationDataSource getInstance(
            RelationRecordInsertWorker relationRecordInsertWorker,
            RelationRecordExtractor relationRecordExtractor
    ) {
        if (relationDataSourceSingleton == null) {
            relationDataSourceSingleton = new RelationDataSource(relationRecordInsertWorker, relationRecordExtractor);
        }

        return relationDataSourceSingleton;
    }

    @Override
    public int insertRelationRecordRecipeIngredientById(
            int recipeId, int ingredientId
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return relationRecordInsertWorker.insertRelationRecipeIngredientRecord(recipeId, ingredientId);
    }

    @Override
    public RelationIngredientRecipeEntity extractRelationByRecipeIdAndIngredientId(
            int recipeId, int ingredientId
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return relationRecordExtractor.extractRelationByIngredientIdAndRecipeId(recipeId, ingredientId);
    }

    @Override
    public List<RelationIngredientRecipeEntity> extractListOfRelationIngredientEntitiesByRecipeId(
            int recipeId
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return relationRecordExtractor.extractRelationToListByRecipe(recipeId);
    }

    @Override
    public List<RelationIngredientRecipeEntity> extractListOfRelationIngredientEntitiesByIngredientId(
            int ingredientId
    ) throws FailedExecuteException, FailedStatementException, FailedConnectingException, UnexpectedException {
        return relationRecordExtractor.extractRelationToListByIngredient(ingredientId);
    }
}
