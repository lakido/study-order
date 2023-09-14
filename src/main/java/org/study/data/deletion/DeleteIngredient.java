package org.study.data.deletion;

import org.study.data.connection.ConnectionDatabaseSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteIngredient {

    private final ConnectionDatabaseSingleton connectionDatabaseSingleton= ConnectionDatabaseSingleton.getInstance();
    private final Connection connection = connectionDatabaseSingleton.getConnection();

    public void deleteIngredient(int id) {
        if (id == 0) id = 1;

        String sql = "DELETE FROM Ingredients WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, id);

            deleteRelationIngredientRecipe(id);

            preparedStatement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteIngredient(String name) {
        if (name == null || name.equals("")) throw new IllegalArgumentException("Empty string is invalid!");

        String sql = "DELETE FROM Ingredients WHERE name = ?";
        String sql2 = "SELECT id FROM Ingredients WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             PreparedStatement preparedStatementForDeletionOfRelation = connection.prepareStatement(sql2)
        ){
            preparedStatement.setString(1, name);
            preparedStatementForDeletionOfRelation.setString(1, name);

            int deletedRecord = preparedStatementForDeletionOfRelation.executeQuery().getInt("id");
            deleteRelationIngredientRecipe(deletedRecord);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteRelationIngredientRecipe(int id) {
        if (id == 0) id = 1;

        String sql = "DELETE FROM Ingredients_Recipe WHERE id_ingredients = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
