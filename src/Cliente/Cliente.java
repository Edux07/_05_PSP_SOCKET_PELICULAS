package Cliente;

import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {

    public static final int PUERTO = 2180;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) throws SocketException {
        System.out.println("--------Peliculario--------");

        InetSocketAddress direccionServer = new InetSocketAddress(IP_SERVER, PUERTO);

        try (Socket socketServer = new Socket(); Scanner sc = new Scanner(System.in)) {
            socketServer.connect(direccionServer);

            PrintWriter salida = new PrintWriter(socketServer.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketServer.getInputStream(), "UTF-8"));

            while (true) {
                mostrarMenu();
                int opcion = sc.nextInt();

                switch (opcion) {
                    case 1: {
                        consultarId(salida, entrada, sc);
                        break;
                    }

                    case 2: {
                        consultarTitulo(salida, entrada, sc);
                        break;
                    }

                    case 3: {
                        consultarDirector(salida, entrada, sc);
                        break;
                    }

                    case 4: {
                        agregarPelicula(salida, entrada, sc);
                        break;
                    }

                    case 5: {
                        salir();
                        return;
                    }
                    default:
                        System.out.println("Opcion no valida. Intentalo de nuevo.");
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("No se puede conectar al servidor en la direccion " + IP_SERVER);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error -> " + e);
            e.printStackTrace();
        }
    }

    protected static void mostrarMenu() {
        System.out.println("\nMenu:");
        System.out.println("Ingrese el numero de la opcion deseada: ");
        System.out.println("1. Consultar pelicula por ID");
        System.out.println("2. Consultar pelicula por titulo");
        System.out.println("3. Consultar peliculas por director");
        System.out.println("4. Agregar pelicula");
        System.out.println("5. Salir de aplicacion");
    }

    private static void consultarId(PrintWriter salida, BufferedReader entrada, Scanner sc) throws IOException {
        System.out.print("Ingrese el ID de la pelicula: ");
        int id = sc.nextInt();

        salida.println(1);
        salida.println(id);

        String respuesta = entrada.readLine();
        if (!respuesta.isEmpty()) {
            System.out.println("Pelicula encontrada:" + respuesta);
        } else {
            System.out.println("No se encontro ninguna pelicula con el ID especificado.");
        }
    }

    private static void consultarTitulo(PrintWriter salida, BufferedReader entrada, Scanner sc) throws IOException {
        System.out.print("Ingrese el titulo de la pelicula: ");
        String titulo = sc.next();

        salida.println(2);
        salida.println(titulo);

        String respuesta = entrada.readLine();
        if (!respuesta.isEmpty()) {
            System.out.println("Pelicula encontrada:" + respuesta);
        } else {
            System.out.println("No se encontro ninguna pelicula con el titulo especificado.");
        }
    }

    private static void consultarDirector(PrintWriter salida, BufferedReader entrada, Scanner sc) throws IOException {
        System.out.print("Ingrese el director de la pelicula: ");
        String director = sc.next();

        salida.println(3);
        salida.println(director);

        int numPeliculas = Integer.parseInt(entrada.readLine());
        if (numPeliculas > 0) {
            System.out.println("Peliculas encontradas por el director " + director + ":");
            for (int i = 0; i < numPeliculas; i++) {
                String pelicula = entrada.readLine();
                System.out.println(pelicula);
            }
        } else {
            System.out.println("No se encontraron peliculas para el director especificado.");
        }
    }

    private static void agregarPelicula(PrintWriter salida, BufferedReader entrada, Scanner sc) throws IOException {
        System.out.print("Ingrese el ID de la nueva pelicula: ");
        int id = sc.nextInt();
        System.out.print("Ingrese el titulo de la nueva pelicula: ");
        String titulo = sc.next();
        System.out.print("Ingrese el director de la nueva pelicula: ");
        String director = sc.next();

        salida.println(4);
        salida.println(id + "," + titulo + "," + director);

        boolean operacionExitosa = Boolean.parseBoolean(entrada.readLine());
        if (operacionExitosa) {
            System.out.println("Pelicula agregada");
        } else {
            System.out.println("Error");
        }
    }

    private static void salir() {
        System.out.println("Saliendo de la aplicacion.");
        System.exit(0);
    }
}
