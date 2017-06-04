package com.example.alber.prueba10.clases;

/**
 * Created by alber on 24/05/2017.
 */

public class Manga {

    private String nombre;
    private String capitulos;
    private String volumenes;
    private String tipo;
    private String estado;
    private double nota;
    private String imagen;
    private String fechaComienzo;
    private String fechaFin;
    private String genero;
    private String autor;
    private String serializacion;
    private String sinopsis;
    private String nombreOriginal;
    private String link;

    public Manga() {}

    public Manga(String nombre, String capitulos, String volumenes, String tipo, String estado, double nota, String imagen,
                 String fechaComienzo, String fechaFin, String genero, String autor, String serializacion, String sinopsis,
                 String nombreOriginal, String link) {

        this.nombre = nombre;
        this.capitulos = capitulos;
        this.volumenes = volumenes;
        this.tipo = tipo;
        this.estado = estado;
        this.nota = nota;
        this.imagen = imagen;
        this.fechaComienzo = fechaComienzo;
        this.fechaFin = fechaFin;
        this.genero = genero;
        this.autor = autor;
        this.serializacion = serializacion;
        this.sinopsis = sinopsis;
        this.nombreOriginal = nombreOriginal;
        this.link = link;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(String capitulos) {
        this.capitulos = capitulos;
    }

    public String getVolumenes() {
        return volumenes;
    }

    public void setVolumenes(String volumenes) {
        this.volumenes = volumenes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaComienzo() {
        return fechaComienzo;
    }

    public void setFechaComienzo(String fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSerializacion() {
        return serializacion;
    }

    public void setSerializacion(String serializacion) {
        this.serializacion = serializacion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
