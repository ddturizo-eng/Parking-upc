package com.parkingupc.servicio;

import com.parkingupc.dao.PersonaDao;
import com.parkingupc.modelo.*;
import java.io.IOException;
import java.util.*;

public class PersonaServicio {
    private final PersonaDao personaDao;

    public PersonaServicio(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    public void guardar(Persona persona) throws IOException {
        personaDao.guardar(persona, persona.getTipoPersona());
    }

    public Persona buscar(String id, String tipo) throws IOException {
        return personaDao.buscarPorId(id, tipo).orElse(null);
    }

    public List<Persona> listar(String tipo) throws IOException {
        return personaDao.leerTodos(tipo);
    }

    public Persona crear(int tipo, String id, String nombre, String tel, String c1, String c2) {
        return switch (tipo) {
            case 1 -> new Estudiante(id, nombre, tel, c1, c2);
            case 2 -> new Docente(id, nombre, tel, c1, c2);
            case 3 -> new Visitante(id, nombre, tel, c1, c2);
            default -> null;
        };
    }
}