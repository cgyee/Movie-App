package com.example.movieapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection = null;
    private String url;
    public Database() {
    }
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public boolean validUser(String email, String password) {
        final String sql = "SELECT email FROM users WHERE email=? and password=?";
        Connection conn = connect();
        int count =0;
        try {
           PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, email);
           pstmt.setString(1, password);
           ResultSet rs = pstmt.executeQuery();
           while(rs.next()) {
               count = rs.getInt(1);
           }
            rs.close();
            conn.close();
        }
        catch (SQLException e) {System.out.println(e.getMessage());}
        return count == 1? true:false;
    }

    public boolean validEmail(String email) {
        final String sql = "SELECT email FROM users WHERE email=?";
        Connection conn = connect();
        int count =0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            conn.close();
        }
        catch (SQLException e) {System.out.println(e.getMessage());}
        return count == 0? true:false;
    }

    public void insertNewUser(String email, String password) {
        final String sql = "INSERT INTO user (email, password)";
        Connection conn = connect();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(1, password);
            pstmt.executeUpdate();
            conn.close();
        }
        catch (SQLException e) {System.out.println(e.getMessage());}

    }
}
