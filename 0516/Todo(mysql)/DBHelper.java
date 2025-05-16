package com.example.mytodoapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private final String url = "jdbc:mysql://10.0.2.2:3307/mytodo";
    private final String user = "skim";
    private final String password = "1111";

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("date")
                );
                tasks.add(task);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void insertTask(String title, String date) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tasks(title, date) VALUES (?, ?)");
            pstmt.setString(1, title);
            pstmt.setString(2, date);
            int rows = pstmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
