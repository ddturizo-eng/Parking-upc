package com.parkingupc.modelo;

public abstract class Vehiculo {
    protected String placa;
    protected String marca;
    protected String color;
    protected String tipoVehiculo;

    public Vehiculo(String placa, String marca, String color, String tipoVehiculo) {
        this.placa = placa;
        this.marca = marca;
        this.color = color;
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getColor() {
        return color;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public abstract String getDatosEspecificos();
}