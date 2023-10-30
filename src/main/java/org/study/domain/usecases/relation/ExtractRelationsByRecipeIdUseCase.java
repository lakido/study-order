package org.study.domain.usecases.relation;

import org.study.domain.models.RelationIngredientRecipeModel;
import org.study.domain.repository.RelationRepository;
import org.study.utils.Result;

import java.util.List;

public class ExtractRelationsByRecipeIdUseCase {

    private final RelationRepository repository;

    public ExtractRelationsByRecipeIdUseCase(RelationRepository relationRepository) {
        repository = relationRepository;
    }

    public Result<List<RelationIngredientRecipeModel>> invoke(int recipeId) {
        return repository.extractRelationsByRecipeId(recipeId);
    }
}
