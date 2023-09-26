package org.study.data.operations.deletion;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.connection.ConnectionWrapper;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.SinglePreparedStatementWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteIngredient {
    private final ConnectionWrapper connection = ConnectionDatabaseSingleton.getInstance().getConnection();

    public void deleteIngredient(int id) throws UnexpectedException {
        if (id == 0) id = 1;

        String query = "DELETE FROM Ingredients WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setInt(1, id);

            deleteRelationIngredientRecipe(id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UnexpectedException();
        }
    }

    //TODO if i have my exceptions with system exceptions in one method (SQLException), i should rethrow again my exceptions, but throw UnexpectedException if i catch SQLException

    public int deleteIngredient(String name) throws UnexpectedException, FailedExecuteException, FailedStatementException {
        if (name == null || name.equals("")) throw new IllegalArgumentException("Empty string is invalid!");

        String queryForDeletionOfIngredientsWithName = "DELETE FROM Ingredients WHERE name = ?";
        String queryForSelectionIdFromIngredientsWithName = "SELECT id FROM Ingredients WHERE name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryForDeletionOfIngredientsWithName);
            PreparedStatement preparedStatementForDeletionOfRelation = connection.prepareStatement(queryForSelectionIdFromIngredientsWithName);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setString(1, name);
            preparedStatementForDeletionOfRelation.setString(1, name);

            int deletedRecordId = preparedStatementForDeletionOfRelation.executeQuery().getInt("id");

            if ( deleteRelationIngredientRecipe(deletedRecordId) != 0) {
                singlePreparedStatementWrapper.executeUpdate();
                return 0;
            }

            throw new UnexpectedException();

        } catch (UnexpectedException | FailedExecuteException | FailedStatementException exception) {
            throw exception;
        } catch (SQLException e) {
            throw new UnexpectedException();
        }
    }

    private int deleteRelationIngredientRecipe(int id) throws UnexpectedException, FailedExecuteException, FailedConnectingException, FailedStatementException {
        if (id == 0) id = 1;

        String sql = "DELETE FROM Ingredients_Recipe WHERE id_ingredients = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

        singlePreparedStatementWrapper.setInt(1, id);
        return singlePreparedStatementWrapper.executeUpdate();
    }
}
