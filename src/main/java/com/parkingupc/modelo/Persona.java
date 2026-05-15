package com.parkingupc.modelo;


//clase base para las demas entidades
public abstract class Persona {
    protected String id;
    protected String nombreCompleto;
    protected String telefono;

    public Persona(String id, String nombreCompleto, String telefono) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public abstract double getDescuento();

    public abstract String getTipoPersona();

    public abstract String getDatosEspecificos();
}