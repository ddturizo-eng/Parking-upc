package com.parkingupc.modelo;

public class Carro extends Vehiculo {
    private int numeroPuertas;
    private boolean esElectrico;

    public Carro(String placa, String marca, String color, int numeroPuertas, boolean esElectrico) {
        super(placa, marca, color, "Carro");
        this.numeroPuertas = numeroPuertas;
        this.esElectrico = esElectrico;
    }

    public int getNumeroPuertas() {
        return numeroPuertas;
    }

    public boolean isEsElectrico() {
        return esElectrico;
    }

    @Override
    public String getDatosEspecificos() {
        return "Puertas: " + numeroPuertas + ", Electrico: " + (esElectrico ? "Si" : "No");
    }

    public String serializar() {
        return placa + "|" + marca + "|" + color + "|" + numeroPuertas + "|" + esElectrico;
    }

    public static Carro deserializar(String linea) {
        String[] partes = linea.split("\\|");
        return new Carro(
            partes[0],
            partes[1],
            partes[2],
            Integer.parseInt(partes[3]),
            Boolean.parseBoolean(partes[4])
        );
    }
}