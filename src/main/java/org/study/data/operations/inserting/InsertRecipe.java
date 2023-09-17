package org.study.data.operations.inserting;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.entity.IngredientEntity;
import org.study.data.entity.RecipeEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsertRecipe {
    ConnectionDatabaseSingleton connectionDatabaseSingleton = ConnectionDatabaseSingleton.getInstance();
    Connection connection = connectionDatabaseSingleton.getConnection();

    //TODO separate this method into two. one of them should insert recipe, return some value
    //TODO create another method to insert ingredients and create relation table
    //TODO these methods should be separated and can be called from another class sequentially
    //TODO this needs to be done because of acquisition the ability to control this operations
    //TODO each method should throws some exceptions, that should be handled, so the application could continue work
    public void insertRecipe(RecipeEntity recipeEntity, List<IngredientEntity> list) {
        List<IngredientEntity> listWithUniqueIngredients = new ArrayList<>(list);

        String sql = "INSERT INTO Recipe (name, category, popularity, age_preferences)" +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, recipeEntity.getName());
            preparedStatement.setString(2, recipeEntity.getCategory());
            preparedStatement.setInt(3, recipeEntity.getPopularity());
            preparedStatement.setInt(4, recipeEntity.getAgePreferences());

            //inserting new record into recipe table
            preparedStatement.executeUpdate();

            //get list with ingredients that have not been existing in database at this moment
            listWithUniqueIngredients.removeAll(getListOfExistingIngredients(list));

            //insert new ingredients
            listWithUniqueIngredients.forEach((x) -> {
                InsertIngredient insertIngredient = new InsertIngredient();
                insertIngredient.insertIngredient(x);
            });

            //get list with all ingredients that have been passed to this method with new id's from database
            List<IngredientEntity> listWithUpdatedRecordFromIngredientTable = new ArrayList<>(getListOfExistingIngredients(list));

            //create relation with the help of assistive Ingredients_Recipe table
            insertRecipeRelation(getIdOfNewRow(recipeEntity), listWithUpdatedRecordFromIngredientTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRecipeRelation(int recordIdInRecipeTable, List<IngredientEntity> list) {

        String sql = "INSERT INTO Ingredients_Recipe (id_recipe, id_ingredients)" +
                "VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, recordIdInRecipeTable);

            for (IngredientEntity element: list
                 ) {
                preparedStatement.setInt(2, element.getId());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getIdOfNewRow(RecipeEntity recipeEntity) {

        String sql = "SELECT id FROM Recipe WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, recipeEntity.getName());

            return preparedStatement.executeQuery().getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Invalid recipe entity!");
    }

    private List<IngredientEntity> getListOfExistingIngredients(List<IngredientEntity> list) {

        String sql = "SELECT * FROM Ingredients WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (IngredientEntity entity: list
                 ) {
                preparedStatement.setString(1, entity.getName());

                ResultSet resultSet = preparedStatement.executeQuery();

                list.add(IngredientEntity.getIngredientEntity(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
