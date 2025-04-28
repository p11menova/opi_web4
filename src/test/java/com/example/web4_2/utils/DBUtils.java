package com.example.web4_2.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBUtils {
    public static void deleteUserByUsername(String username) {
        try {
            Connection connection = DriverManager.getConnection(
                    PropertiesLoader.getProperty("db.url"),
                    PropertiesLoader.getProperty("db.username"),
                    PropertiesLoader.getProperty("db.password")
            );

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM web_users WHERE username = ?"
            );
            statement.setString(1, username);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException("не смог удалить юзера из базы(", e);
        }
    }
}
