package com.example.alber.prueba10.clases;

/**
 * Creado por alber
 */
public class Anime {

    private String nombre;
    private String episodios;
    private String tipo;
    private String estado;
    private double nota;
    private String imagen;
    private String fechaComienzo;
    private String nombreOriginal;
    private String fechaFin;
    private String popularidad;
    private String duracion;
    private String pegi;
    private String productores;
    private String estudio;
    private String genero;
    private String sinopsis;
    private String enlaceTrailer;
    private String temporada;
    private String fuente;

    public Anime() {}

    public Anime(String nombre, String episodios, String tipo, String estado, double nota, String imagen, String fechaComienzo,
                 String nombreOriginal, String fechaFin, String popularidad, String duracion, String pegi, String productores,
                 String estudio, String genero, String sinopsis, String enlaceTrailer, String temporada, String fuente) {

        this.nombre = nombre;
        this.episodios = episodios;
        this.tipo = tipo;
        this.estado = estado;
        this.nota = nota;
        this.imagen = imagen;
        this.fechaComienzo = fechaComienzo;
        this.nombreOriginal = nombreOriginal;
        this.fechaFin = fechaFin;
        this.popularidad = popularidad;
        this.duracion = duracion;
        this.pegi = pegi;
        this.productores = productores;
        this.estudio = estudio;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.enlaceTrailer = enlaceTrailer;
        this.temporada = temporada;
        this.fuente = fuente;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen() {
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

    public String getFechaComienzo() {
        return fechaComienzo;
    }

    public void setFechaComienzo(String fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(String popularidad) {
        this.popularidad = popularidad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    public String getProductores() {
        return productores;
    }

    public void setProductores(String productores) {
        this.productores = productores;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getEnlaceTrailer() {
        return enlaceTrailer;
    }

    public void setEnlaceTrailer(String enlaceTrailer) {
        this.enlaceTrailer = enlaceTrailer;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
}
