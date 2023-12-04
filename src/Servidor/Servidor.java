package Servidor;

import java.io.*;
import java.net.*;
import java.util.*;


public class Servidor {

    public static final int PUERTO = 2180;
    private static List<Peliculas> pelis = new ArrayList<>();

    public static void main(String[] args) {
        Biblioteca();
        System.out.println("-------- Servidor  --------");

        try (ServerSocket server = new ServerSocket(PUERTO)) {
            System.out.println("Esperando conexiones en el puerto " + PUERTO);

            while (true) {
                Socket socketAlCliente = server.accept();
                System.out.println("Nuevo cliente conectado.");

                new peliHilo(socketAlCliente, pelis).start();
            }

        } catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error -> " + e);
			e.printStackTrace();
		}
    }

    public static void Biblioteca() {
        pelis.add(new Peliculas(1, "Gladiator", "RidleyScott"));
        pelis.add(new Peliculas(2, "KillBill", "QuentinTarantino"));
        pelis.add(new Peliculas(3, "SpiderMan", "SamRaimi"));
        pelis.add(new Peliculas(4, "Tiburon", "StevenSpielberg"));
        pelis.add(new Peliculas(5, "Interestellar", "ChristopherNolan"));
    }
}