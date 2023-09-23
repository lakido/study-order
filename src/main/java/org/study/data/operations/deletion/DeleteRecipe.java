package org.study.data.operations.deletion;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.*;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecipe {

    private final ConnectionWrapper connection = ConnectionDatabaseSingleton.getInstance().getConnection();

    public int deleteRecipe(int id) {
        String queryForRecipeDeletion = "DELETE FROM Recipe WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryForRecipeDeletion);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setInt(1, id);

            if (deleteRelationRecord(id) != 0) {
                return singlePreparedStatementWrapper.executeUpdate();
            }

            throw new FailedDeleteException();

        } catch (FailedDeleteException | FailedStatementException | FailedConnectingException | UnexpectedException |
                 FailedExecuteException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteRecipe(String name) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int deleteRelationRecord(int id) {
        String queryForRecipeIngredientRelation = "DELETE OR IGNORE FROM Ingredients_Recipe WHERE id_recipe = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryForRecipeIngredientRelation);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setInt(1, id);

            return singlePreparedStatementWrapper.executeUpdate();

        } catch (UnexpectedException | FailedExecuteException | FailedStatementException | FailedConnectingException e) {
            throw new RuntimeException(e);
        }
    }
}
