package com.parkingupc.dao;

import com.parkingupc.modelo.Docente;
import com.parkingupc.modelo.Estudiante;
import com.parkingupc.modelo.Persona;
import com.parkingupc.modelo.Visitante;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonaDaoArchivo implements PersonaDao {
    private static final String RUTA_DATOS = "datos/";
    private static final String DELIMITADOR = "|";

    @Override
    public void guardar(Persona persona, String tipoPersona) throws IOException {
        String archivo = obtenerArchivo(tipoPersona);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            String linea = serializar(persona, tipoPersona);
            bw.write(linea);
            bw.newLine();
        }
    }

    @Override
    public List<Persona> leerTodos(String tipoPersona) throws IOException {
        List<Persona> personas = new ArrayList<>();
        String archivo = obtenerArchivo(tipoPersona);
        java.io.File file = new java.io.File(archivo);
        if (!file.exists()) {
            return personas;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Persona persona = deserializar(linea, tipoPersona);
                if (persona != null) {
                    personas.add(persona);
                }
            }
        }
        return personas;
    }

    @Override
    public Optional<Persona> buscarPorId(String id, String tipoPersona) throws IOException {
        List<Persona> personas = leerTodos(tipoPersona);
        return personas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public void actualizar(Persona persona, String tipoPersona) throws IOException {
        List<Persona> personas = leerTodos(tipoPersona);
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getId().equals(persona.getId())) {
                personas.set(i, persona);
                break;
            }
        }
        guardarTodos(personas, tipoPersona);
    }

    @Override
    public void eliminar(String id, String tipoPersona) throws IOException {
        List<Persona> personas = leerTodos(tipoPersona);
        personas.removeIf(p -> p.getId().equals(id));
        guardarTodos(personas, tipoPersona);
    }

    private String obtenerArchivo(String tipoPersona) {
        return switch (tipoPersona) {
            case "Estudiante" -> RUTA_DATOS + "estudiantes.txt";
            case "Docente" -> RUTA_DATOS + "docentes.txt";
            case "Visitante" -> RUTA_DATOS + "visitantes.txt";
            default -> RUTA_DATOS + "desconocido.txt";
        };
    }

    private String serializar(Persona persona, String tipoPersona) {
        if (persona instanceof Estudiante e) {
            return e.serializar();
        } else if (persona instanceof Docente d) {
            return d.serializar();
        } else if (persona instanceof Visitante v) {
            return v.serializar();
        }
        return "";
    }

    private Persona deserializar(String linea, String tipoPersona) {
        return switch (tipoPersona) {
            case "Estudiante" -> Estudiante.deserializar(linea);
            case "Docente" -> Docente.deserializar(linea);
            case "Visitante" -> Visitante.deserializar(linea);
            default -> null;
        };
    }

    private void guardarTodos(List<Persona> personas, String tipoPersona) throws IOException {
        String archivo = obtenerArchivo(tipoPersona);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Persona persona : personas) {
                bw.write(serializar(persona, tipoPersona));
                bw.newLine();
            }
        }
    }
}