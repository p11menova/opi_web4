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
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM web_points WHERE user_id = (SELECT id FROM web_users WHERE username = ?)"
            );
            st.setString(1, username);
            st.executeUpdate();
            st.close();

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
    public static void createUserByUsername(String username){
        try {
            Connection connection = DriverManager.getConnection(
                    PropertiesLoader.getProperty("db.url"),
                    PropertiesLoader.getProperty("db.username"),
                    PropertiesLoader.getProperty("db.password")
            );

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO web_users (id, username, password) VALUES (2122212, ?, ?)"
            );
            statement.setString(1, username);
            statement.setString(2, username);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException("не смог создать юзера в базе(", e);
        }
    }


}
