package com.parkingupc.modelo;

public class Puesto {
    private String numero;
    private String tipoVehiculo;
    private boolean ocupado;
    private String placaVehiculo;

    public Puesto(String numero, String tipoVehiculo) {
        this.numero = numero;
        this.tipoVehiculo = tipoVehiculo;
        this.ocupado = false;
        this.placaVehiculo = null;
    }

    public String getNumero() {
        return numero;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void ocupar(String placaVehiculo) {
        this.ocupado = true;
        this.placaVehiculo = placaVehiculo;
    }

    public void liberar() {
        this.ocupado = false;
        this.placaVehiculo = null;
    }

    public String serializar() {
        return numero + "|" + tipoVehiculo + "|" + ocupado + "|" + (placaVehiculo != null ? placaVehiculo : "");
    }

    public static Puesto deserializar(String linea) {
        String[] partes = linea.split("\\|");
        Puesto puesto = new Puesto(partes[0], partes[1]);
        if (Boolean.parseBoolean(partes[2])) {
            puesto.ocupar(partes[3]);
        }
        return puesto;
    }
}