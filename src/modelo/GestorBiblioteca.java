package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorBiblioteca {
    private static GestorBiblioteca instancia;
    private List<Libro> libros;
    private List<Autor> autores;

    private GestorBiblioteca() {
        libros = new ArrayList<>();
        autores = new ArrayList<>();
    }

    public static GestorBiblioteca getInstance() {
        if (instancia == null) {
            instancia = new GestorBiblioteca();
        }
        return instancia;
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
        libro.getAutor().agregarLibro(libro);
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
    }

    public List<Libro> buscarLibros(String criterio) {
        return libros.stream()
                .filter(libro -> libro.getTitulo().contains(criterio) ||
                        libro.getAutor().getNombre().contains(criterio) ||
                        libro.getGenero().contains(criterio))
                .collect(Collectors.toList());
    }
    
    public List<Libro> buscarLibrosConFiltro(String criterio, String filtro) {
        return libros.stream()
            .filter(libro -> {
                switch (filtro) {
                    case "Título":
                        return libro.getTitulo().toLowerCase().contains(criterio.toLowerCase());
                    case "Autor":
                        return libro.getAutor().getNombre().toLowerCase().contains(criterio.toLowerCase());
                    case "Género":
                        return libro.getGenero().toLowerCase().contains(criterio.toLowerCase());
                    default:
                        return false;
                }
            })
            .collect(Collectors.toList());
    }

    public void agregarAutor(Autor autor) {
        autores.add(autor);
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public List<Autor> getAutores() {
        return autores;
    }
}
