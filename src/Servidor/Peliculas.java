package Servidor;

import java.io.Serializable;

public class Peliculas implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ID;
	private String titulo;
	private String director;

	public Peliculas(int iD, String titulo, String director) {
		ID = iD;
		this.titulo = titulo;
		this.director = director;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Peliculas [ID=" + ID + ", titulo=" + titulo + ", director=" + director + "]";
	}

}
