package org.study.data.inserting;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.entity.IngredientEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIngredient {

    private final ConnectionDatabaseSingleton connectionDatabaseSingleton = ConnectionDatabaseSingleton.getInstance();
    private final Connection connection = connectionDatabaseSingleton.getConnection();

    public void insertIngredient(IngredientEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Invalid entity pf ingredient!");

        String sql = "INSERT INTO Ingredients (name, calories, weight, recommendation)" +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getCalories());
            preparedStatement.setInt(3, entity.getWeight());
            preparedStatement.setString(4, entity.getRecommendation());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
