package org.study.data.operations;

import org.study.data.entity.IngredientEntity;
import org.study.data.entity.RecipeEntity;
import org.study.data.exceptions.FailedExecuteException;
import org.study.data.exceptions.FailedReadException;
import org.study.data.exceptions.FailedStatementException;
import org.study.data.exceptions.UnexpectedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SinglePreparedStatementWrapper {

    private final PreparedStatement preparedStatement;

    public SinglePreparedStatementWrapper(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public void setInt(int position, int i) throws FailedStatementException {
        try {
            preparedStatement.setInt(position, i);
        } catch (SQLException e) {
            throw new FailedStatementException();
        }
    }

    public void setString(int position, String str) throws FailedStatementException {
        try {
            preparedStatement.setString(position, str);
        } catch (SQLException e) {
            throw new FailedStatementException();
        }
    }

    public ResultSet executeQuery() throws FailedExecuteException, UnexpectedException {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new FailedExecuteException();
        } finally {
            closeStatement();
        }
    }

    public RecipeEntity executeQueryToGetRecipeEntity() throws UnexpectedException, FailedReadException {
        try {
            return RecipeEntity.getRecipeEntity(preparedStatement.executeQuery());
        } catch (UnexpectedException | FailedReadException  e) {
            throw e;
        } catch (SQLException e) {
            throw new UnexpectedException();
        } finally {
            closeStatement();
        }
    }

    public IngredientEntity executeQueryToGetIngredientsEntity() throws UnexpectedException, FailedReadException {
        try {
            return IngredientEntity.getIngredientEntity(preparedStatement.executeQuery());
        } catch (UnexpectedException | FailedReadException  e) {
            throw e;
        } catch (SQLException e) {
            throw new UnexpectedException();
        } finally {
            closeStatement();
        }
    }

    public int executeUpdate() throws FailedExecuteException, UnexpectedException {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new FailedExecuteException();
        } finally {
            closeStatement();
        }
    }

    private void closeStatement() throws UnexpectedException{
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            throw new UnexpectedException();
        }
    }
}
