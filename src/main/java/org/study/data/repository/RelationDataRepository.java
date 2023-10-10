package org.study.data.repository;

import org.study.data.entities.RelationIngredientRecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.exceptions.Error;
import org.study.data.sources.relation.RelationDataSource;
import org.study.domain.repository.RelationRepository;
import org.study.utils.Result;

import java.util.List;

public class RelationDataRepository implements RelationRepository {

    private static RelationDataSource dataSource;

    private static RelationDataRepository relationDataRepositorySingleton;

    public RelationDataRepository() {}

    public static RelationDataRepository getInstance(RelationDataSource relationDataSource) {
        if (relationDataRepositorySingleton == null) {
            relationDataRepositorySingleton = new RelationDataRepository();
            dataSource = relationDataSource;
        }

        return relationDataRepositorySingleton;
    }

    @Override
    public Result<RelationIngredientRecipeEntity> extractRelationByIngredientIdAndRecipeId(int recipeId, int ingredientId) {
        try {
            return new Result.Correct<>(dataSource.extractRelationByRecipeIdAndIngredientId(recipeId, ingredientId));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<RelationIngredientRecipeEntity>> extractRelationToListByRecipe(int recipeId) {
        try {
            return new Result.Correct<>(dataSource.extractListOfRelationIngredientEntitiesByRecipeId(recipeId));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<RelationIngredientRecipeEntity>> extractRelationToListByIngredient(int ingredientId) {
        try {
            return new Result.Correct<>(dataSource.extractListOfRelationIngredientEntitiesByIngredientId(ingredientId));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertRelation(int recipeId, int ingredientId) {
        try {
            return new Result.Correct<>(dataSource.insertRelationRecordRecipeIngredientById(recipeId, ingredientId));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException exception) {
            return new Result.Error<>(exception);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }
}
