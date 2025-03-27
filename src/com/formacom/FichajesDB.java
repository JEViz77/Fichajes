package com.formacom;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FichajesDB implements IFichajes{
    private String modo="Entrada";


    @Override
    public Boolean login(String user, String pass) {
        try {
            Connection conexion=DatabaseConnection.getConnection();
            String sql="SELECT * FROM administradores WHERE username=? AND password=?";
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1,user);
            pst.setString(2,pass);
            return pst.executeQuery().next();

        } catch (SQLException e) {
            return false;
            //throw new RuntimeException(e);
        }

    }

    @Override
    public String alta_alumno(String dni, String nombre) {
        String sql="INSERT INTO usuarios(dni,nombre) VALUES(?,?)";
        try {
            Connection conexion=DatabaseConnection.getConnection();
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1,dni);
            pst.setString(2,nombre);
            pst.executeUpdate();
            return "Alumno añadido correctamente";
        } catch (SQLException e) {
            return "El alumno ya existe con ese DNI";
            //throw new RuntimeException(e);
        }

    }

    @Override
    public String cambiar_modo(String modo) {
        this.modo=modo;
        return this.modo;
    }

    @Override
    public List<Registro> informe_por_dia(LocalDate dia) {

        return List.of();
    }

    @Override
    public List<Registro> informe_por_alumno(String dni) {
        List<Registro> informe = new ArrayList<>();
        String sql = "SELECT U.dni,U.nombre,R.modo,R.created_at FROM registros R inner join\n" +
                "usuarios U on R.dni=U.dni where R.dni=?";
        try {
            Connection conexion = DatabaseConnection.getConnection();
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, dni);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {

                String nombre = resultSet.getString("nombre");
                String modo = resultSet.getString("modo");
                LocalDateTime fecha = resultSet.getTimestamp("created_at").toLocalDateTime();
                informe.add(new Registro(dni, nombre, fecha, modo));
            }
        } catch (SQLException e) {
            return List.of();
            //throw new RuntimeException(e);
        }
        return informe;
    }

    @Override
    public String fichar(String dni) {
        String sql="INSERT INTO registros(dni,modo) VALUES(?,?)";
        try {
            Connection conexion=DatabaseConnection.getConnection();
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1,dni);
            pst.setString(2,modo);
            pst.executeUpdate();
            return "Fichaje realizado correctamente";
        } catch (SQLException e) {
            return "Error al realizar el fichaje";
            //throw new RuntimeException(e);
        }

    }
}
