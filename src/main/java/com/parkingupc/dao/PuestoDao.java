package com.parkingupc.dao;

import com.parkingupc.modelo.Puesto;
import java.io.IOException;
import java.util.List;

public interface PuestoDao {
    void guardar(Puesto puesto) throws IOException;
    List<Puesto> leerTodos() throws IOException;
    void guardarTodos(List<Puesto> puestos) throws IOException;
}