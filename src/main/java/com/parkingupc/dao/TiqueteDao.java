package com.parkingupc.dao;

import com.parkingupc.modelo.Tiquete;
import java.io.IOException;
import java.util.List;

public interface TiqueteDao {
    void guardar(Tiquete tiquete) throws IOException;
    List<Tiquete> leerTodos() throws IOException;
    void guardarTodos(List<Tiquete> tiquetes) throws IOException;
}