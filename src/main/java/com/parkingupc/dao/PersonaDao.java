package com.parkingupc.dao;

import com.parkingupc.modelo.Persona;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PersonaDao {
    void guardar(Persona persona, String tipoPersona) throws IOException;
    List<Persona> leerTodos(String tipoPersona) throws IOException;
    Optional<Persona> buscarPorId(String id, String tipoPersona) throws IOException;
    void actualizar(Persona persona, String tipoPersona) throws IOException;
    void eliminar(String id, String tipoPersona) throws IOException;
}