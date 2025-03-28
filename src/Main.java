import com.formacom.Fichajes;
import com.formacom.FichajesDB;
import com.formacom.IFichajes;
import com.formacom.Registro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String LINE = "════════════════════════════════════════════════════════════";
    private static final String MENU_ALUMNO = "\n" + LINE + "\n               🎓 FICHAJE ALUMN@S 🎓\n" + LINE + "\n                \n1️⃣  Fichar\n2️⃣  Salir\n" + LINE + "\n";

    private static final String MENU_ADMINISTRADOR = "\n" + LINE + "\n               🔒 FICHAJES FORMACOM 🔒\n" + LINE + "\n                \n1️⃣  Nuevo Alumno\n2️⃣  Seleccionar Modo\n3️⃣  Ver Informes\n4️⃣  Salir\n" + LINE + "\n";

    private static final String MENU_INFORME = "\n" + LINE + "\n               📊 MENÚ INFORMES 📊\n" + LINE + "\n                \n1️⃣  Fichajes por día\n2️⃣  Fichajes por alumno\n3️⃣  Volver\n" + LINE + "\n";

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        IFichajes fichajes = new FichajesDB();
        String opcionSeleccionada = "";

        System.out.println(LINE);
        System.out.println("              🎉 BIENVENIDO A FICHAJES FORMACOM 🎉");
        System.out.println(LINE);

        String usuario, password;
        do {
            System.out.print("👤 Usuario: ");
            usuario = leer.nextLine();
            System.out.print("🔑 Contraseña: ");
            password = leer.nextLine();

            if (!fichajes.login(usuario, password)) {
                System.out.println("❌ Usuario o contraseña incorrectos. Intente de nuevo.");
            }
        } while (!fichajes.login(usuario, password));

        do {
            System.out.println(MENU_ADMINISTRADOR);
            System.out.print("👉 Seleccione una opción: ");
            opcionSeleccionada = leer.nextLine();

            switch (opcionSeleccionada) {
                case "1":
                    System.out.print("📛 Nombre del alumn@: ");
                    String nombre = leer.nextLine();
                    System.out.print("🆔 DNI del alumn@: ");
                    String dni = leer.nextLine();
                    System.out.println(fichajes.alta_alumno(dni, nombre));
                    break;
                case "2":
                    seleccionarModo(leer, fichajes);
                    break;
                case "3":
                    System.out.println(MENU_INFORME);
                    System.out.print("📌 Seleccione una opción: ");
                    opcionSeleccionada = leer.nextLine();

                    switch (opcionSeleccionada) {
                        case "1":
                            System.out.print("📅 Ingrese la fecha (dd/MM/yyyy): ");
                            String fecha_informe = leer.nextLine();
                            LocalDate localDate = LocalDate.parse(fecha_informe, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            mostrarRegistros(fichajes.informe_por_dia(localDate));
                            break;
                        case "2":
                            System.out.print("🔍 Ingrese DNI del alumn@: ");
                            dni = leer.nextLine();
                            mostrarRegistros(fichajes.informe_por_alumno(dni));
                            break;
                        case "3":
                            System.out.println("🔙 Volviendo...");
                            break;
                        default:
                            System.out.println("⚠️ Opción no válida.");
                    }
                    break;
                case "4":
                    System.out.println("👋 Hasta la próxima!");
                    break;
                default:
                    System.out.println("⚠️ Opción no válida.");
            }
        } while (!opcionSeleccionada.equals("4"));
    }

    private static void mostrarRegistros(List<Registro> registros) {
        System.out.println("📋 Registros:");
        registros.forEach(System.out::println);
    }

    private static void seleccionarModo(Scanner leer, IFichajes fichajes) {
        System.out.println("\n" + LINE);
        System.out.println("               🔄 SELECCIÓN DE MODO 🔄");
        System.out.println(LINE + "\n                \n1️⃣  Modo Entrada\n2️⃣  Modo Salida\n" + LINE + "\n");
        System.out.print("👉 Seleccione una opción: ");
        String opcionSeleccionada = leer.nextLine();

        if (opcionSeleccionada.equals("1")) {
            System.out.println(fichajes.cambiar_modo("Entrada"));
        } else {
            System.out.println(fichajes.cambiar_modo("Salida"));
        }

        System.out.print("🎓 Activar modo alumn@ (s/n): ");
        opcionSeleccionada = leer.nextLine();
        if (opcionSeleccionada.equalsIgnoreCase("s")) {
            do {
                System.out.println(MENU_ALUMNO);
                System.out.print("👉 Seleccione una opción: ");
                opcionSeleccionada = leer.nextLine();
                switch (opcionSeleccionada) {
                    case "1":
                        System.out.print("🆔 Introduce DNI: ");
                        String dni = leer.nextLine();
                        System.out.println(fichajes.fichar(dni));
                        break;
                    case "2":
                        System.out.print("🔑 Password Administrador: ");
                        String pass = leer.nextLine();
                        if (fichajes.login("admin", pass)) {
                            System.out.println("👋 Saliendo...");
                        } else {
                            opcionSeleccionada = "x";
                        }
                        break;
                    default:
                        System.out.println("⚠️ Opción no válida.");
                }
            } while (!opcionSeleccionada.equals("2"));
        }
    }
}