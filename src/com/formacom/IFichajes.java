package com.formacom;

import java.time.LocalDate;
import java.util.List;

public interface IFichajes {
    public Boolean login(String user, String pass);
    public String alta_alumno(String dni,String nombre);
    public String cambiar_modo(String modo);
    public List<Registro> informe_por_dia(LocalDate dia);
    public List<Registro> informe_por_alumno(String dni);
    public String fichar(String dni);
}