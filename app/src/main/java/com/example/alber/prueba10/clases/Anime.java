package com.example.alber.prueba10.clases;

/**
 * Creado por alber
 */
public class Anime {

    private String imagen;
    private double nota;
    private String nombre;
    private String episodios;
    private String tipo;
    private String estado;
    private String fecha;

    public Anime() {}

    public Anime(String imagen, double nota, String nombre, String tipo, String estado, String episodios, String fecha) {
        this.imagen = imagen;
        this.nota = nota;
        this.nombre = nombre;
        this.episodios = episodios;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
    }

    public void setImagen(String/*int*/ imagen) {
        this.imagen = imagen;
    }

    public String/*int*/ getImagen() {
        return imagen;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public double getNota() {
        return nota;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setEpisodios(String episodios) {
        this.episodios = episodios;
    }

    public String getEpisodios() {
        return episodios;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado(){
        return estado;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

}
