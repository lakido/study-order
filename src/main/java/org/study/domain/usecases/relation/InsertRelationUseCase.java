package org.study.domain.usecases.relation;

import org.study.domain.repository.RelationRepository;
import org.study.utils.Result;

public class InsertRelationUseCase {

    private final RelationRepository repository;

    public InsertRelationUseCase(RelationRepository relationRepository) {
        repository = relationRepository;
    }

    public Result<Integer> invoke(int recipeId, int ingredientId) {
        return repository.insertRelation(recipeId, ingredientId);
    }
}
