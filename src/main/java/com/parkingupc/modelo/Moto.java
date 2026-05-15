package com.parkingupc.modelo;


public class Moto extends Vehiculo {
    private int cilindraje;
    private boolean tieneCajuela;

    public Moto(String placa, String marca, String color, int cilindraje, boolean tieneCajuela) {
        super(placa, marca, color, "Moto");// aplicando la herencia como con la clase animal y gato
        this.cilindraje = cilindraje;
        this.tieneCajuela = tieneCajuela;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    public boolean isTieneCajuela() {
        return tieneCajuela;
    }

    @Override
    public String getDatosEspecificos() {
        return "Cilindraje: " + cilindraje + "cc, Cajuela: " + (tieneCajuela ? "Si" : "No");//espeficaciones del vehiculo
    }

    public String serializar() {
        return placa + "|" + marca + "|" + color + "|" + cilindraje + "|" + tieneCajuela;
    }

    public static Moto deserializar(String linea) {
        String[] partes = linea.split("\\|");
        return new Moto(
            partes[0],
            partes[1],
            partes[2],
            Integer.parseInt(partes[3]),
            Boolean.parseBoolean(partes[4])
        );
    }
}