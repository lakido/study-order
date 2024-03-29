package org.study.data.sources.recipe;

import org.study.data.entities.RecipeEntity;
import org.study.data.exceptions.*;
import org.study.data.operations.changing.RecipeUpdateWorker;
import org.study.data.operations.deletion.RecipeDeleteWorker;
import org.study.data.operations.extraction.RecipeEntityExtractor;
import org.study.data.operations.inserting.RecipeInsertWorker;

import java.util.List;

public class RecipeDataSource implements RecipeDataSourceInterface {

    private final RecipeUpdateWorker recipeUpdateWorker;
    private final RecipeDeleteWorker recipeDeleteWorker;
    private final RecipeEntityExtractor recipeEntityExtractor;
    private final RecipeInsertWorker recipeInsertWorker;

    private static RecipeDataSource recipeDataSourceSingleton;

    private RecipeDataSource(
            RecipeUpdateWorker recipeUpdateWorker,
            RecipeDeleteWorker recipeDeleteWorker,
            RecipeEntityExtractor recipeEntityExtractor,
            RecipeInsertWorker recipeInsertWorker
    ) {
        this.recipeUpdateWorker = recipeUpdateWorker;
        this.recipeDeleteWorker = recipeDeleteWorker;
        this.recipeEntityExtractor = recipeEntityExtractor;
        this.recipeInsertWorker = recipeInsertWorker;
    }

    public static RecipeDataSource getInstance(
            RecipeUpdateWorker recipeUpdateWorker,
            RecipeDeleteWorker recipeDeleteWorker,
            RecipeEntityExtractor recipeEntityExtractor,
            RecipeInsertWorker recipeInsertWorker
    ) {
        if (recipeDataSourceSingleton == null) {
            recipeDataSourceSingleton = new RecipeDataSource(
                    recipeUpdateWorker,
                    recipeDeleteWorker,
                    recipeEntityExtractor,
                    recipeInsertWorker
            );
        }

        return recipeDataSourceSingleton;
    }

    @Override
    public int updateRecipe(
            RecipeEntity recipeEntity
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return recipeUpdateWorker.updateRecipe(
                recipeEntity.getId(),
                recipeEntity.getName(),
                recipeEntity.getCategory(),
                recipeEntity.getPopularity(),
                recipeEntity.getAgePreferences()
        );
    }

    @Override
    public int deleteRecipeByName(
            String name
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return recipeDeleteWorker.deleteRecipeByName(name);
    }

    @Override
    public int deleteRecipeById(
            int id
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return recipeDeleteWorker.deleteRecipeById(id);
    }

    @Override
    public RecipeEntity extractRecipeEntityById(
            int id
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return recipeEntityExtractor.extractRecipeFromDatabaseById(id);
    }

    @Override
    public RecipeEntity extractRecipeEntityByName(
            String name
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return recipeEntityExtractor.extractRecipeFromDatabaseByName(name);
    }

    @Override
    public List<RecipeEntity> extractRecipeListOfFirstRecords(
            int limit
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return recipeEntityExtractor.extractRecipeListOfFirstRecords(limit);
    }

    @Override
    public List<RecipeEntity> extractRecipeList(
            String category,
            int popularity,
            int agePreferences
    ) throws UnexpectedException, FailedReadException, FailedStatementException, FailedConnectingException {
        return recipeEntityExtractor.extractRecipeList(category, popularity, agePreferences);
    }

    @Override
    public Integer extractNextAvailableIdForRecipe() throws UnexpectedException, FailedExecuteException, FailedConnectingException {
        return recipeEntityExtractor.extractNextAvailableIdForRecipe();
    }

    @Override
    public int insertRecipe(
            RecipeEntity recipeEntity
    ) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        return recipeInsertWorker.insertRecipe(
                recipeEntity.getName(),
                recipeEntity.getCategory(),
                recipeEntity.getPopularity(),
                recipeEntity.getAgePreferences()
        );
    }
}
