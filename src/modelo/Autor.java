package modelo;

import java.util.ArrayList;
import java.util.List;

public class Autor {
    private String nombre;
    private String biografia;
    private List<Libro> libros;

    public Autor(String nombre, String biografia) {
        this.nombre = nombre;
        this.biografia = biografia;
        this.libros = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getBiografia() {
        return biografia;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }
}
