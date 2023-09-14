package org.study.data.extraction;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.entity.IngredientEntity;
import org.study.data.entity.RecipeEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExtractIngredientEntity {
    private final ConnectionDatabaseSingleton connectionDatabaseSingleton = ConnectionDatabaseSingleton.getInstance();
    private final Connection connection = connectionDatabaseSingleton.getConnection();

    public IngredientEntity extractIngredientFromDataBase(int id) {
        if (id == 0) id = 1;

        String sql = "SELECT * FROM Ingredients WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return new IngredientEntity(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Incorrect input values!");
    }

    public IngredientEntity extractIngredientFromDataBase(String name) {

        if (name == null || name.equals("")) throw new IllegalArgumentException("Empty String is invalid!");

        String sql = "SELECT * FROM Ingredients WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            return new IngredientEntity(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Incorrect input values!");
    }
}
