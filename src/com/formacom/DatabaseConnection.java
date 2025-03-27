package com.formacom;

import java.sql.*;

public class DatabaseConnection {

    // Método para establecer la conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        // Cargar la configuración de la base de datos desde un archivo o variables
        String dbUrl = "jdbc:mysql://localhost:3306/fichajes"; // URL de la base de datos
        String dbUser = "root";  // Usuario de la base de datos
        String dbPassword = "";  // Contraseña de la base de datos
        String dbDriver = "com.mysql.cj.jdbc.Driver";  // Driver JDBC, depende de la base de datos

        try {
            // Cargar el driver JDBC
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("No se encontró el driver JDBC.");
        }

        // Establecer la conexión con la base de datos
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // Método genérico para ejecutar consultas SELECT
    public static ResultSet executeQuery(String query) throws SQLException {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            return stmt.executeQuery(query);
        }
    }

    // Método genérico para ejecutar consultas INSERT, UPDATE, DELETE
    public static int executeUpdate(String query) throws SQLException {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }
}