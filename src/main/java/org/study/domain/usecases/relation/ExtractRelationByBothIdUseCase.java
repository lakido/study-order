package org.study.domain.usecases.relation;

import org.study.domain.entities.RelationIngredientRecipeModel;
import org.study.domain.repository.RelationRepository;
import org.study.utils.Result;

public class ExtractRelationByBothIdUseCase {

    private final RelationRepository repository;

    public ExtractRelationByBothIdUseCase(RelationRepository relationRepository) {
        repository = relationRepository;
    }

    public Result<RelationIngredientRecipeModel> invoke(int idRecipe, int ingredientId) {
        return repository.extractRelation(idRecipe, ingredientId);
    }
}
