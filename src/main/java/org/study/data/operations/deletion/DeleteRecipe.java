package org.study.data.operations.deletion;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecipe {
    //TODO all methods that can throw only my custom exceptions must be declared with throws in signature
    private final ConnectionWrapper connection = ConnectionDatabaseSingleton.getInstance().getConnection();

    public int deleteRecipe(int id) throws UnexpectedException, FailedExecuteException, FailedDeleteException, FailedConnectingException, FailedStatementException {
        String queryForRecipeDeletion = "DELETE FROM Recipe WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryForRecipeDeletion);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);

        if (deleteRelationRecord(id) != 0) {
            return singlePreparedStatementWrapper.executeUpdate();
        }

        throw new FailedDeleteException();
    }

    public int deleteRecipe(String name) throws UnexpectedException {
        String queryForDeletion = "DELETE OR IGNORE FROM Recipe WHERE name = ?";
        String queryForSelectionIdOfDeletingRecords = "SELECT id FROM Recipe WHERE name = ?";

        try (PreparedStatement preparedStatement1 = connection.prepareStatement(queryForDeletion);
             PreparedStatement preparedStatement2 = connection.prepareStatement(queryForSelectionIdOfDeletingRecords)) {

            preparedStatement1.setString(1, name);
            preparedStatement2.setString(1, name);

            int idOfRecordForDeletion = preparedStatement2.executeQuery().getInt("id");

            if (deleteRelationRecord(idOfRecordForDeletion) == 0) {
                preparedStatement1.executeUpdate();
                return 0;
            }

        } catch (SQLException exception) {
            throw new UnexpectedException();
        }

        return -1;
    }

    private int deleteRelationRecord(int id) throws UnexpectedException, FailedExecuteException, FailedStatementException, FailedConnectingException {
        String queryForRecipeIngredientRelation = "DELETE OR IGNORE FROM Ingredients_Recipe WHERE id_recipe = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryForRecipeIngredientRelation);

        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
        singlePreparedStatementWrapper.setInt(1, id);
        return singlePreparedStatementWrapper.executeUpdate();
    }
}
