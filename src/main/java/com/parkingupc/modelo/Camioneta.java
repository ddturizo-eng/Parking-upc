package com.parkingupc.modelo;


//clase Camioneta que hereda de Vehiculo y tiene atributos específicos como número de ejes y capacidad de carga
public class Camioneta extends Vehiculo {
    private int numeroEjes;
    private double capacidadCarga;

    public Camioneta(String placa, String marca, String color, int numeroEjes, double capacidadCarga) {
        super(placa, marca, color, "Camioneta");
        this.numeroEjes = numeroEjes;
        this.capacidadCarga = capacidadCarga;
    }

    public int getNumeroEjes() {
        return numeroEjes;
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    @Override
    public String getDatosEspecificos() {
        return "Ejes: " + numeroEjes + ", Capacidad: " + capacidadCarga + " toneladas";
    }

    public String serializar() {
        return placa + "|" + marca + "|" + color + "|" + numeroEjes + "|" + capacidadCarga;
    }

    // metodo que ayuda a deserializar una línea de texto y convertirla en un objeto Camioneta ( para mostrar en txt fino)
    public static Camioneta deserializar(String linea) {
        String[] partes = linea.split("\\|");
        return new Camioneta(
            partes[0],
            partes[1],
            partes[2],
            Integer.parseInt(partes[3]),
            Double.parseDouble(partes[4])
        );
    }
}