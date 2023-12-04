package Servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class peliHilo extends Thread {
    private Socket socketAlCliente;
    private List<Peliculas> pelis;

    public peliHilo(Socket socketAlCliente, List<Peliculas> pelis) {
        this.socketAlCliente = socketAlCliente;
        this.pelis = pelis;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socketAlCliente.getInputStream(), "UTF-8"));
             PrintWriter salida = new PrintWriter(socketAlCliente.getOutputStream(), true)) {

            while (true) {
                int op = Integer.parseInt(entrada.readLine());

                switch (op) {
                    case 1:
                        consultarID(entrada, salida);
                        break;
                    case 2:
                        consultarTitulo(entrada, salida);
                        break;
                    case 3:
                        consultarDirector(entrada, salida);
                        break;
                    case 4:
                        agregarPeli(entrada, salida);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void consultarID(BufferedReader entrada, PrintWriter salida) throws IOException {
        int id = Integer.parseInt(entrada.readLine());
        Peliculas peli = buscarID(id);

        if (peli != null) {
            salida.println(peli.getID() + "," + peli.getTitulo() + "," + peli.getDirector());
        } else {
            salida.println("");
        }
    }

    private Peliculas buscarID(int id) {
        for (Peliculas pelicula : pelis) {
            if (pelicula.getID() == id) {
                return pelicula;
            }
        }
        return null;
    }

    private void consultarTitulo(BufferedReader entrada, PrintWriter salida) throws IOException {
        String titulo = entrada.readLine();
        Peliculas pelicula = buscarPeliculaPorTitulo(titulo);

        if (pelicula != null) {
            salida.println(pelicula.getID() + "," + pelicula.getTitulo() + "," + pelicula.getDirector());
        } else {
            salida.println("");
        }
    }

    private Peliculas buscarPeliculaPorTitulo(String titulo) {
        for (Peliculas pelicula : pelis) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null;
    }

    private void consultarDirector(BufferedReader entrada, PrintWriter salida) throws IOException {
        String director = entrada.readLine();
        List<Peliculas> pelisDirector = buscarDirector(director);

        salida.println(pelisDirector.size());

        for (Peliculas pelicula : pelisDirector) {
            salida.println(pelicula.getID() + "," + pelicula.getTitulo() + "," + pelicula.getDirector());
        }
    }

    private List<Peliculas> buscarDirector(String director) {
        List<Peliculas> pelisDirector = new ArrayList<>();

        for (Peliculas pelicula : pelis) {
            if (pelicula.getDirector().equalsIgnoreCase(director)) {
                pelisDirector.add(pelicula);
            }
        }
        return pelisDirector;
    }

    private void agregarPeli(BufferedReader entrada, PrintWriter salida) throws IOException {
        try {
            int id = Integer.parseInt(entrada.readLine());
            String titulo = entrada.readLine();
            String director = entrada.readLine();
 
            Peliculas nuevaPelicula = new Peliculas(id, titulo, director);
            
            if (!pelis.contains(nuevaPelicula)) {
                pelis.add(nuevaPelicula);
                salida.println("La pelicula se agrego correctamente.");
            } else {
                salida.println("La pelicula ya existe en la biblioteca.");
            }
        } catch (NumberFormatException e) {
            salida.println("Error al procesar los datos. Asegurate de ingresar valores validos.");
        }
    }
}
