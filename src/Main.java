import com.formacom.Fichajes;

import com.formacom.IFichajes;
import com.formacom.Registro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    final static String MENU_ALUMNO= """
____________________________________________________________             
                FICHAJE ALUMN@S                
                
                1. Fichar
                2. Salir
                
____________________________________________________________             
                
                """;

    public static void main(String[] args) {
        Scanner leer=new Scanner(System.in);
        IFichajes fichajes=new Fichajes();
        String opcionSeleccionada="";
        final String MENU_ADMINISTRADOR= """
____________________________________________________________                           
                FICHAJES FORMACOM
                
                1. Nuevo Alumno
                2. Seleccionar Modo
                3. Ver Informes
                4. Salir
                
                
____________________________________________________________             
                """;

        final String MENU_INFORME= """
____________________________________________________________                           
                MENÚ INFORMES
                
                
                1. Fichajes por dia
                2. Fichajes por alumno
                3. Volver
                
____________________________________________________________             

                """;
        String usuario="";
        String password="";
        System.out.println("---BIENVENIDO A FICHAJES FORMACOM---");
        do{
            System.out.print("usuario: ");
            usuario=leer.nextLine();
            System.out.print("contraseña: ");
            password=leer.nextLine();
            if(!fichajes.login(usuario,password))
                System.out.println("Usuario o Contraseña incorrectas");
        }while (! fichajes.login(usuario,password));
        do {
            System.out.println(MENU_ADMINISTRADOR);
            opcionSeleccionada=leer.nextLine();
            switch (opcionSeleccionada){
                case "1":
                    System.out.println("Nombre del alumn@:");
                    String nombre=leer.nextLine();
                    System.out.println("DNI del alumn@:");
                    String dni=leer.nextLine();
                    System.out.println(fichajes.alta_alumno(dni,nombre));
                    break;
                case "2":
                    funcionMenu2(opcionSeleccionada,leer,fichajes);

                    break;
                case "3":
                    System.out.println(MENU_INFORME);
                    opcionSeleccionada=leer.nextLine();
                    switch (opcionSeleccionada){
                        case "1":
                            System.out.println("Fecha Informe dd/MM/yyyy");
                            String fecha_informe=leer.nextLine();
                            LocalDate localDate=LocalDate.parse(fecha_informe, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            mostrarRegistros(fichajes.informe_por_dia(localDate));
                            break;
                        case "2":
                            System.out.println("DNI Alumn@:");
                            dni=leer.nextLine();
                            mostrarRegistros(fichajes.informe_por_alumno(dni));
                            break;
                        case "3":
                            System.out.println("Volviendo...");
                            break;
                        default:
                            System.out.println("Opción no válida");
                    }
                    break;
                case "4":

                    System.out.println("Hasta la próxima");
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        }while (! opcionSeleccionada.equals("4"));

    }

    private static void mostrarRegistros(List<Registro> registros) {
        System.out.println(registros);
    }


    static void funcionMenu2(String opcionSeleccionada,Scanner leer,IFichajes fichajes){
        System.out.println("""
____________________________________________________________             
                SELECCIÓN DE MODO
                              
                1.Modo Entrada
                2.Modo Salida
                              
____________________________________________________________             

                """);
        opcionSeleccionada=leer.nextLine();
        if(opcionSeleccionada.equals("1")){
            System.out.println(fichajes.cambiar_modo("Entrada"));
        }else{
            System.out.println(fichajes.cambiar_modo("Salida"));
        }
        System.out.println("Activar modo alumn@ s/n");
        opcionSeleccionada=leer.nextLine();
        if(opcionSeleccionada.equals("s")){
            //Activamos modo alumno
            do {
                System.out.println(MENU_ALUMNO);
                opcionSeleccionada=leer.nextLine();
                switch (opcionSeleccionada){
                    case "1":
                        System.out.println("Introduce DNI: ");
                        String dni=leer.nextLine();
                        System.out.println(fichajes.fichar(dni));
                        break;
                    case "2":
                        System.out.println("Password Administrador:");
                        String pass=leer.nextLine();
                        if(fichajes.login("admin",pass)){
                            System.out.println("Saliendo....");
                        }else{
                            opcionSeleccionada="dddd";
                        }
                        break;
                    default:
                        System.out.println("Opción no válida");
                }

            }while(!opcionSeleccionada.equals("2"));
        }
    }
}