package org.study.data.operations.deletion;

import org.study.data.connection.ConnectionDatabaseSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecipe {

    private final Connection connection = ConnectionDatabaseSingleton.getInstance().getConnection();

    public int deleteRecipe(int id) {
        String queryForRecipeDeletion = "DELETE FROM Recipe WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryForRecipeDeletion)){

            preparedStatement.setInt(1, id);


            if (deleteRelationRecord(id) == 0) {
                preparedStatement.executeUpdate();
                return 0;
            }

            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -2;
    }

    public int deleteRecipe(String name) {
        String queryForDeletion = "DELETE FROM Recipe WHERE name = ?";
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
        String queryForRecipeIngredientRelation = "DELETE FROM Ingredients_Recipe WHERE id_recipe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryForRecipeIngredientRelation)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  -1;
    }
}
