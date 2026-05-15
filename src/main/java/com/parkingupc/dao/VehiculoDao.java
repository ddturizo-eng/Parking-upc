package com.parkingupc.dao;

import com.parkingupc.modelo.Vehiculo;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VehiculoDao {
    void guardar(Vehiculo vehiculo, String tipoVehiculo) throws IOException;
    List<Vehiculo> leerTodos(String tipoVehiculo) throws IOException;
    Optional<Vehiculo> buscarPorPlaca(String placa, String tipoVehiculo) throws IOException;
    void actualizar(Vehiculo vehiculo, String tipoVehiculo) throws IOException;
    void eliminar(String placa, String tipoVehiculo) throws IOException;
}