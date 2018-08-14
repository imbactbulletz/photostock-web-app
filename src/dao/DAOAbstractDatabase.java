package dao;


import entities.BasicEntity;


import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOAbstractDatabase <T extends BasicEntity>{
    private Class<T> clazz;

    private String USERNAME = "root";
    private String PASSWORD = "";
    private String DATABASE_NAME = "photostock-web-app";


    public DAOAbstractDatabase(Class<T> clazz){
        this.clazz = clazz;
    }


    protected T readFromResultSet(ResultSet resultSet){
        if( resultSet == null){
            return null;
        }

        try {
            T object = this.clazz.getDeclaredConstructor().newInstance();

            for(String columnName : object.columnNames()){

                object.setValueForColumnName(columnName, resultSet.getObject(columnName));
            }

            return object;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    protected void closeResultSet(ResultSet resultSet){
        if(resultSet == null){
            return;
        }

        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected Connection createConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/" + DATABASE_NAME, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void closeConnection(Connection connection){
        if( connection == null){
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeStatement(PreparedStatement statement){
        if(statement == null){
            return;
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}