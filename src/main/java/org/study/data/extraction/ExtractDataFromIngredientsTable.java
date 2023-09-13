package org.study.data.extraction;

import org.study.data.connection.ConnectionDatabaseBuilder;
import org.study.data.connection.ConnectionDatabaseSingleton;

import java.sql.*;

public class ExtractDataFromIngredientsTable {
//    private final ConnectionDatabaseSingleton connectionDatabaseSingleton = ConnectionDatabaseSingleton.getInstance();
//    private final Connection connection = connectionDatabaseSingleton.getConnection();

    public String getNameById(int id) {
        if (id == 0) id = 1;

        String sql = "SELECT name FROM Ingredients WHERE id = ?";

        try (Connection conn = ConnectionDatabaseBuilder.builder().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)
            ) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeQuery().getString("name");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Incorrect input values!");
    }

    public Integer getCaloriesById(int id) {
        if (id == 0) throw new RuntimeException("Incorrect input values");

        String sql = "SELECT calories FROM Ingredients WHERE id = ?";

        try (Connection conn = ConnectionDatabaseBuilder.builder().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeQuery().getInt("calories");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Incorrect input values!");
    }

    public Integer getWeightById(int id) {
        if (id == 0) throw new RuntimeException("Incorrect input values");

        String sql = "SELECT weight FROM Ingredients WHERE id = ?";

        try (Connection conn = ConnectionDatabaseBuilder.builder().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeQuery().getInt("weight");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Incorrect input values!");
    }

    public Integer getIdByName(String id) throws SQLException {
        if (id == null || id.equals("")) throw new RuntimeException("Incorrect input values");

        String sql = "SELECT id FROM Ingredients WHERE name = ?";

        try (Connection conn = ConnectionDatabaseBuilder.builder().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, id);

            return preparedStatement.executeQuery().getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Incorrect input values!");
    }
}