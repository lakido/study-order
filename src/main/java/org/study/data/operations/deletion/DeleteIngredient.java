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

    public void deleteIngredient(int id) {
        if (id == 0) id = 1;

        String query = "DELETE FROM Ingredients WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setInt(1, id);

            deleteRelationIngredientRecipe(id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int deleteIngredient(String name) {
        if (name == null || name.equals("")) throw new IllegalArgumentException("Empty string is invalid!");

        String queryForDeletionOfIngredientsWithName = "DELETE FROM Ingredients WHERE name = ?";
        String queryForSelectionIdFromIngredientsWithName = "SELECT id FROM Ingredients WHERE name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryForDeletionOfIngredientsWithName);
            PreparedStatement preparedStatementForDeletionOfRelation = connection.prepareStatement(queryForSelectionIdFromIngredientsWithName);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);
//            SinglePreparedStatementWrapper preparedStatementForDeletionOfRelationWrapper = new SinglePreparedStatementWrapper(preparedStatementForDeletionOfRelation);

            singlePreparedStatementWrapper.setString(1, name);
            preparedStatementForDeletionOfRelation.setString(1, name);

            int deletedRecordId = preparedStatementForDeletionOfRelation.executeQuery().getInt("id");

            if ( deleteRelationIngredientRecipe(deletedRecordId) != 0) {
                singlePreparedStatementWrapper.executeUpdate();
                return 0;
            }

            throw new UnexpectedException();

        } catch (UnexpectedException | FailedExecuteException | FailedStatementException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int deleteRelationIngredientRecipe(int id) {
        if (id == 0) id = 1;

        String sql = "DELETE FROM Ingredients_Recipe WHERE id_ingredients = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            SinglePreparedStatementWrapper singlePreparedStatementWrapper = new SinglePreparedStatementWrapper(preparedStatement);

            singlePreparedStatementWrapper.setInt(1, id);

            return singlePreparedStatementWrapper.executeUpdate();

        } catch (FailedStatementException | FailedConnectingException | UnexpectedException | FailedExecuteException e) {
            throw new RuntimeException(e);
        }
    }

}
