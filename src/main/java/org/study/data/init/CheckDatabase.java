package org.study.data.init;

import org.study.data.exceptions.FailedConnectingException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckDatabase {
    public static void checkDatabaseStatus() throws FailedConnectingException {
        String dataBaseName = "dataBase.db";
        String dataBaseLocation = "jdbc:sqlite:DataBaseSource";

        File database = new File(dataBaseLocation + dataBaseName);
        if (!database.exists()) {
            try (
                    Connection originConnection = DriverManager.getConnection(dataBaseLocation + File.separator + dataBaseName);
                    Statement statementForIngredientTable = originConnection.createStatement();
                    Statement statementForRecipeTable = originConnection.createStatement();
                    Statement statementForRelationTable = originConnection.createStatement()
            ){
                statementForIngredientTable.execute("""
                        CREATE TABLE IF NOT EXISTS "Ingredients" (
                        \t"id"\tINTEGER NOT NULL UNIQUE,
                        \t"name"\tTEXT NOT NULL,
                        \t"calories"\tINTEGER NOT NULL,
                        \t"weight"\tINTEGER NOT NULL,
                        \t"recommendation"\tTEXT NOT NULL,
                        \tPRIMARY KEY("id" AUTOINCREMENT)
                        );""");

                statementForRecipeTable.execute("""
                        CREATE TABLE IF NOT EXISTS "Recipe" (
                        \t"id"\tINTEGER NOT NULL UNIQUE,
                        \t"name"\tTEXT NOT NULL,
                        \t"category"\tTEXT NOT NULL,
                        \t"popularity"\tINTEGER NOT NULL,
                        \t"age_preferences"\tINTEGER NOT NULL,
                        \tPRIMARY KEY("id" AUTOINCREMENT)
                        );""");

                statementForRelationTable.execute("""
                        CREATE TABLE IF NOT EXISTS "Ingredients_Recipe" (
                        \t"id"\tINTEGER NOT NULL UNIQUE,
                        \t"id_recipe"\tINTEGER NOT NULL,
                        \t"id_ingredients"\tINTEGER NOT NULL,
                        \tFOREIGN KEY("id_ingredients") REFERENCES "Ingredients"("id") ON DELETE CASCADE,
                        \tFOREIGN KEY("id_recipe") REFERENCES "Recipe"("id") ON DELETE CASCADE,
                        \tPRIMARY KEY("id" AUTOINCREMENT)
                        );""");
            } catch (SQLException e) {
                throw new FailedConnectingException();
            }
        }
    }
}
