package org.study.data.operations.inserting;

import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;

public class RelationRecordInsertWorker {

    //TODO this class can insert duplicate values in relation table, i.e. that this class does not check input values for uniqueness in database
    private final ConnectionWrapper connection;

    public RelationRecordInsertWorker(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public int insertRelationRecipeIngredientRecord(
            int idRecipe,
            int idIngredient
    ) throws UnexpectedException, FailedConnectingException, FailedStatementException, FailedExecuteException {
        String query = "INSERT INTO Ingredients_Recipe (id_recipe, id_ingredients) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setInt(1, idRecipe);
        singlePreparedStatementWrapper.setInt(2, idIngredient);

        return singlePreparedStatementWrapper.executeUpdate();
    }
}
