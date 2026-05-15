package com.parkingupc.dao;

import com.parkingupc.modelo.Camioneta;
import com.parkingupc.modelo.Carro;
import com.parkingupc.modelo.Moto;
import com.parkingupc.modelo.Vehiculo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehiculoDaoArchivo implements VehiculoDao {
    private static final String RUTA_DATOS = "datos/";

    @Override
    public void guardar(Vehiculo vehiculo, String tipoVehiculo) throws IOException {
        String archivo = obtenerArchivo(tipoVehiculo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            String linea = serializar(vehiculo, tipoVehiculo);
            bw.write(linea);
            bw.newLine();
        }
    }

    @Override
    public List<Vehiculo> leerTodos(String tipoVehiculo) throws IOException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String archivo = obtenerArchivo(tipoVehiculo);
        java.io.File file = new java.io.File(archivo);
        if (!file.exists()) {
            return vehiculos;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Vehiculo vehiculo = deserializar(linea, tipoVehiculo);
                if (vehiculo != null) {
                    vehiculos.add(vehiculo);
                }
            }
        }
        return vehiculos;
    }

    @Override
    public Optional<Vehiculo> buscarPorPlaca(String placa, String tipoVehiculo) throws IOException {
        List<Vehiculo> vehiculos = leerTodos(tipoVehiculo);
        return vehiculos.stream()
                .filter(v -> v.getPlaca().equals(placa))
                .findFirst();
    }

    @Override
    public void actualizar(Vehiculo vehiculo, String tipoVehiculo) throws IOException {
        List<Vehiculo> vehiculos = leerTodos(tipoVehiculo);
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getPlaca().equals(vehiculo.getPlaca())) {
                vehiculos.set(i, vehiculo);
                break;
            }
        }
        guardarTodos(vehiculos, tipoVehiculo);
    }

    @Override
    public void eliminar(String placa, String tipoVehiculo) throws IOException {
        List<Vehiculo> vehiculos = leerTodos(tipoVehiculo);
        vehiculos.removeIf(v -> v.getPlaca().equals(placa));
        guardarTodos(vehiculos, tipoVehiculo);
    }

    private String obtenerArchivo(String tipoVehiculo) {
        return switch (tipoVehiculo) {
            case "Moto" -> RUTA_DATOS + "motos.txt";
            case "Carro" -> RUTA_DATOS + "carros.txt";
            case "Camioneta" -> RUTA_DATOS + "camionetas.txt";
            default -> RUTA_DATOS + "desconocido.txt";
        };
    }

    private String serializar(Vehiculo vehiculo, String tipoVehiculo) {
        if (vehiculo instanceof Moto m) {
            return m.serializar();
        } else if (vehiculo instanceof Carro c) {
            return c.serializar();
        } else if (vehiculo instanceof Camioneta ca) {
            return ca.serializar();
        }
        return "";
    }

    private Vehiculo deserializar(String linea, String tipoVehiculo) {
        return switch (tipoVehiculo) {
            case "Moto" -> Moto.deserializar(linea);
            case "Carro" -> Carro.deserializar(linea);
            case "Camioneta" -> Camioneta.deserializar(linea);
            default -> null;
        };
    }

    private void guardarTodos(List<Vehiculo> vehiculos, String tipoVehiculo) throws IOException {
        String archivo = obtenerArchivo(tipoVehiculo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Vehiculo vehiculo : vehiculos) {
                bw.write(serializar(vehiculo, tipoVehiculo));
                bw.newLine();
            }
        }
    }
}