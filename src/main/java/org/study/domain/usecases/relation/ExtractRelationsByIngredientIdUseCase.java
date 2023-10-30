package org.study.domain.usecases.relation;

import org.study.domain.models.RelationIngredientRecipeModel;
import org.study.domain.repository.RelationRepository;
import org.study.utils.Result;

import java.util.List;

public class ExtractRelationsByIngredientIdUseCase {

    private final RelationRepository repository;

    public ExtractRelationsByIngredientIdUseCase(RelationRepository relationRepository) {
        repository = relationRepository;
    }

    public Result<List<RelationIngredientRecipeModel>> invoke(int ingredientId) {
        return repository.extractRelationsByIngredientId(ingredientId);
    }
}
