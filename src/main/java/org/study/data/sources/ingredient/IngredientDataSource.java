package org.study.data.sources.ingredient;

import org.study.data.entities.IngredientEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.changing.IngredientUpdateWorker;
import org.study.data.operations.deletion.IngredientDeleteWorker;
import org.study.data.operations.extraction.IngredientEntityExtractor;
import org.study.data.operations.inserting.IngredientInsertWorker;

import java.util.List;

public class IngredientDataSource implements IngredientDataSourceInterface{

    private final IngredientUpdateWorker ingredientUpdateWorker;
    private final IngredientDeleteWorker ingredientDeleteWorker;
    private final IngredientEntityExtractor ingredientEntityExtractor;
    private final IngredientInsertWorker ingredientInsertWorker;

    private static IngredientDataSource ingredientDataSourceSingleton;

    private IngredientDataSource(
            IngredientUpdateWorker ingredientUpdateWorker,
            IngredientDeleteWorker ingredientDeleteWorker,
            IngredientEntityExtractor ingredientEntityExtractor,
            IngredientInsertWorker ingredientInsertWorker
    ) {
        this.ingredientUpdateWorker = ingredientUpdateWorker;
        this.ingredientDeleteWorker = ingredientDeleteWorker;
        this.ingredientEntityExtractor = ingredientEntityExtractor;
        this.ingredientInsertWorker = ingredientInsertWorker;
    }

    public static IngredientDataSource getInstance(
            IngredientUpdateWorker ingredientUpdateWorker,
            IngredientDeleteWorker ingredientDeleteWorker,
            IngredientEntityExtractor ingredientEntityExtractor,
            IngredientInsertWorker ingredientInsertWorker
    ) {
        if (ingredientDataSourceSingleton == null) {
            ingredientDataSourceSingleton = new IngredientDataSource(
                    ingredientUpdateWorker,
                    ingredientDeleteWorker,
                    ingredientEntityExtractor,
                    ingredientInsertWorker);
        }

        return ingredientDataSourceSingleton;
    }

    @Override
    public int updateIngredient(
            IngredientEntity ingredientEntity
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return ingredientUpdateWorker.updateIngredient(
                ingredientEntity.getId(),
                ingredientEntity.getName(),
                ingredientEntity.getRecommendation(),
                ingredientEntity.getCalories(),
                ingredientEntity.getWeight()
        );
    }

    @Override
    public int deleteIngredientById(
            int id
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return ingredientDeleteWorker.deleteIngredientById(id);
    }

    @Override
    public int deleteIngredientByName(
            String name
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return ingredientDeleteWorker.deleteIngredientByName(name);
    }

    @Override
    public IngredientEntity extractIngredientEntityById(
            int id
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return ingredientEntityExtractor.extractIngredientFromDatabaseById(id);
    }

    @Override
    public IngredientEntity extractIngredientEntityByName(
            String name
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return ingredientEntityExtractor.extractIngredientFromDatabaseByName(name);
    }

    @Override
    public List<IngredientEntity> extractListOfFirstRecords(
            int limit
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return ingredientEntityExtractor.extractIngredientListOfFirstRecords(limit);
    }

    @Override
    public List<IngredientEntity> extractIngredientListByRecipeId(
            int recipeId
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return ingredientEntityExtractor.extractIngredientListByRecipeId(recipeId);
    }

    @Override
    public List<IngredientEntity> extractIngredientListByRecipeName(
            String name
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return ingredientEntityExtractor.extractIngredientListByRecipeName(name);
    }

    @Override
    public Integer extractNextAvailableIdForIngredient() throws UnexpectedException, FailedExecuteException, FailedConnectingException {
        return ingredientEntityExtractor.extractNextAvailableIdForIngredient();
    }

    @Override
    public int insertListOfIngredients(
            List<IngredientEntity> ingredientEntityList
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return ingredientInsertWorker.insertListOfIngredients(ingredientEntityList);
    }

    @Override
    public int insertIngredient(
            IngredientEntity ingredientEntity
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return ingredientInsertWorker.insertIngredient(
                ingredientEntity.getName(),
                ingredientEntity.getCalories(),
                ingredientEntity.getWeight(),
                ingredientEntity.getRecommendation()
        );
    }
}
