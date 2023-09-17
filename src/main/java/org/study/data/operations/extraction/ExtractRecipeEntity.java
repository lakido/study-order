package org.study.data.operations.extraction;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.entity.RecipeEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExtractRecipeEntity {
    private final ConnectionDatabaseSingleton connectionDatabaseSingleton = ConnectionDatabaseSingleton.getInstance();
    private final Connection connection = connectionDatabaseSingleton.getConnection();

    public RecipeEntity extractRecipeFromDataBase(int id) {
        if (id == 0) id = 1;

        String sql = "SELECT * FROM Recipe WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return RecipeEntity.getRecipeEntity(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Incorrect input values!");
    }

    public RecipeEntity extractRecipeFromDataBase(String name) {

        if (name == null || name.equals("")) throw new IllegalArgumentException("Empty String is invalid!");

        String sql = "SELECT * FROM Recipe WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            return RecipeEntity.getRecipeEntity(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Incorrect input values!");
    }
}
