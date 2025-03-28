package com.formacom;

import java.util.Objects;

public class Alumno {
    private String dni;
    private String nombre;

    public Alumno(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    // En la clase Alumno
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Alumno alumno = (Alumno) obj;
        return Objects.equals(dni, alumno.dni);
    }

    @Override
    public int hashCode() {
        return dni != null ? dni.hashCode() : 0;
    }

}

