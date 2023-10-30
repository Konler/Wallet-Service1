package ru.ylab.task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabasePreparing {

    public static void createTables(Connection connection) {
        String sql;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/db/ddl/creating_tables.sql"))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            sql = stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла creating_tables.sql", e);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запросов", e);
        }
    }
}
