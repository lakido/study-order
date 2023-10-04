package org.study.data.repository;

import org.study.data.entity.RelationIngredientRecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.exceptions.Error;
import org.study.data.sources.relation.RelationDataSource;
import org.study.ui.repository.RelationRepository;
import org.study.utils.Result;

import java.util.List;

public class RelationDataRepository implements RelationRepository {

    private final RelationDataSource relationDataSource;

    public RelationDataRepository(RelationDataSource relationDataSource) {
        this.relationDataSource = relationDataSource;
    }

    @Override
    public Result<RelationIngredientRecipeEntity> extractRelationByIngredientIdAndRecipeId(int recipeId, int ingredientId) {
        try {
            return new Result.Correct<>(relationDataSource.extractRelationByRecipeIdAndIngredientId(recipeId, ingredientId));
        } catch (UnexpectedException | FailedReadException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<RelationIngredientRecipeEntity>> extractRelationToListByRecipe(int recipeId) {
        try {
            return new Result.Correct<>(relationDataSource.extractListOfRelationIngredientEntitiesByRecipeId(recipeId));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<List<RelationIngredientRecipeEntity>> extractRelationToListByIngredient(int ingredientId) {
        try {
            return new Result.Correct<>(relationDataSource.extractListOfRelationIngredientEntitiesByIngredientId(ingredientId));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }

    @Override
    public Result<Integer> insertRelation(int recipeId, int ingredientId) {
        try {
            return new Result.Correct<>(relationDataSource.insertRelationRecordRecipeIngredientById(recipeId, ingredientId));
        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            return new Result.Error<>(new Error());
        }
    }
}
