package com.example.alber.prueba10.clases;

/**
 * Created by alber on 24/05/2017.
 */

public class Manga {

    private String imagen;
    private double nota;
    private String nombre;
    private String capitulos;
    private String volumenes;
    private String tipo;
    private String estado;
    private String fecha;

    public Manga() {}

    public Manga(String imagen, double nota, String nombre, String capitulos, String volumenes, String tipo, String estado, String fecha) {
        this.imagen = imagen;
        this.nota = nota;
        this.nombre = nombre;
        this.capitulos = capitulos;
        this.volumenes = volumenes;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
