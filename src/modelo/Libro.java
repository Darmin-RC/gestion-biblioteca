package modelo;

public class Libro implements ElementoBiblioteca {
    private String titulo;
    private Autor autor;
    private String genero;
    private String descripcion;

    public Libro(String titulo, Autor autor, String genero, String descripcion) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.descripcion = descripcion;
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
