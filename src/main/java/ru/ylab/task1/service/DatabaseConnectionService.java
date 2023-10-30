package ru.ylab.task1.service;


import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.helper.PropertiesUtil;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionService {
    private static String dbUrl;
    private static String dbPassword;
    private static String dbUser;
    private static Connection connection;
    private DatabaseConnectionService() {
    }

    private static void setConnection(ServletContext context) throws SQLException, DbException, FileNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DbException("Driver class not found");
        }
        Properties prop = PropertiesUtil.getProperties(context);
        dbUrl = prop.getProperty("url");
        dbPassword = prop.getProperty("password");
        dbUser = prop.getProperty("username");
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public static Connection getInstance(ServletContext context) throws DbException, FileNotFoundException {
        try {
            if (connection == null || connection.isClosed()) {
                setConnection(context);
            }
        } catch (SQLException e) {
            throw new DbException("Database connection error", e);
        }

        return connection;
    }

    public static Connection getExistingConnection() {
        return connection;
    }


}
