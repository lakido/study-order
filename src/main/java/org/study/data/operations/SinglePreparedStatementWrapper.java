package org.study.data.operations;

import org.study.data.entity.IngredientEntity;
import org.study.data.entity.RecipeEntity;
import org.study.data.entity.RelationIngredientRecipeEntity;
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
        } catch (SQLException exception) {
            throw new FailedStatementException();
        }
    }

    public void setString(int position, String str) throws FailedStatementException {
        try {
            preparedStatement.setString(position, str);
        } catch (SQLException exception) {
            throw new FailedStatementException();
        }
    }

    public ResultSet executeQuery() throws FailedExecuteException {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException exception) {
            throw new FailedExecuteException();
        }
    }

    public ResultSet executeQueryToGetRecipeEntity() throws UnexpectedException, FailedReadException {
        try {
            return preparedStatement.executeQuery();
        } catch (FailedReadException exception) {
            throw exception;
        } catch (SQLException e) {
            throw new UnexpectedException();
        }
    }

    public ResultSet executeQueryToGetIngredientsEntity() throws UnexpectedException, FailedReadException {
        try {
            return preparedStatement.executeQuery();
        } catch (FailedReadException exception) {
            throw exception;
        } catch (SQLException e) {
            throw new UnexpectedException();
        }
    }

    public ResultSet executeQueryToGetRelationIngredientRecipeEntity() throws UnexpectedException, FailedReadException {
        try {
            return preparedStatement.executeQuery();
        } catch (FailedReadException exception) {
            throw exception;
        } catch (SQLException e) {
            throw new UnexpectedException();
        } finally {
            closeStatement();
        }
    }

    public int executeUpdate() throws FailedExecuteException, UnexpectedException {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new FailedExecuteException();
        } finally {
            closeStatement();
        }
    }

    public void closeStatement() throws UnexpectedException {
        try {
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException exception) {
            throw new UnexpectedException();
        }
    }
}
