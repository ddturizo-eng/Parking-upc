package com.parkingupc.servicio;

import com.parkingupc.dao.VehiculoDao;
import com.parkingupc.modelo.*;
import java.io.IOException;
import java.util.*;

public class VehiculoServicio {
    private final VehiculoDao vehiculoDao;

    public VehiculoServicio(VehiculoDao vehiculoDao) {
        this.vehiculoDao = vehiculoDao;
    }

    public void guardar(Vehiculo vehiculo) throws IOException {
        vehiculoDao.guardar(vehiculo, vehiculo.getTipoVehiculo());
    }

    public Vehiculo buscar(String placa) throws IOException {
        for (String tipo : Arrays.asList("Moto", "Carro", "Camioneta")) {
            var v = vehiculoDao.buscarPorPlaca(placa, tipo);
            if (v.isPresent()) return v.get();
        }
        return null;
    }

    public Vehiculo crear(int tipo, String placa, String marca, String color, Object val1, Object val2) {
        return switch (tipo) {
            case 1 -> new Moto(placa, marca, color, (int) val1, (boolean) val2);
            case 2 -> new Carro(placa, marca, color, (int) val1, (boolean) val2);
            case 3 -> new Camioneta(placa, marca, color, (int) val1, (double) val2);
            default -> null;
        };
    }
}