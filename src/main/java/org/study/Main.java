package org.study;

import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.exceptions.*;
import org.study.data.operations.extraction.IngredientEntityExtractor;
import org.study.data.operations.extraction.RecipeEntityExtractor;
import org.study.data.operations.inserting.RelationRecordInsertWorker;

import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    interface Test {
        String concat(Function<String, String> fun);
    }
    public static void main(String[] args) throws FailedConnectingException, UnexpectedException, FailedExecuteException, FailedStatementException, FailedReadException {
//        String x = "boba";


//        System.out.println(print(Main::getMess, "error123"));

//        System.out.println(test.concat("biba", "boba"));

//        RelationRecordExtractor relationRecordExtractor = new RelationRecordExtractor(ConnectionDatabaseSingleton.getInstance().getConnection());
//        System.out.println(relationRecordExtractor.extractListOfRelationRecordsOfRecipeIngredient(9, 15));
//        RelationRecordInsertWorker relationRecordInsertWorker = new RelationRecordInsertWorker(ConnectionDatabaseSingleton.getInstance().getConnection());
//        System.out.println(relationRecordInsertWorker.insertRelationRecipeIngredientRecord(9, 15));

//        RelationRecordExtractor relationRecordExtractor = new RelationRecordExtractor(ConnectionDatabaseSingleton.getInstance().getConnection());
//        RelationRecordInsertWorker relationRecordInsertWorker = new RelationRecordInsertWorker(ConnectionDatabaseSingleton.getInstance().getConnection());

//        RelationDataSource relationDataSource = new RelationDataSource(relationRecordInsertWorker, relationRecordExtractor);

//        System.out.println(relationDataSource.extractListOfRelationIngredientEntitiesByRecipeId(9));

//        System.out.println(relationRecordExtractor.extractRelationRecipeIngredientRecord(9, 9));

//        RecipeEntityExtractor recipeEntityExtractor = new RecipeEntityExtractor(ConnectionDatabaseSingleton.getInstance().getConnection());

//        System.out.println(recipeEntityExtractor.extractRecipeFromDatabase(1));

//        IngredientEntityExtractor entityExtractor = new IngredientEntityExtractor(ConnectionDatabaseSingleton.getInstance().getConnection());

//        System.out.println(entityExtractor.extractIngredientFromDatabaseById(1));

//        RelationRecordInsertWorker relationRecordInsertWorker = new RelationRecordInsertWorker(ConnectionDatabaseSingleton.getInstance().getConnection());
//        System.out.println(relationRecordInsertWorker.insertRelationRecipeIngredientRecord(45,90));;



    }


    public static String print(Supplier<String> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException exception) {
            return "Error";
        }
    }

    public static String print(Function<String, String> function, String str) {
        try {
            return function.apply(str);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
    }

    public static String getMess(String str1) {
        throw new IllegalArgumentException("single" + str1);
    }
}