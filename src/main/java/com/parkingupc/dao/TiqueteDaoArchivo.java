package com.parkingupc.dao;

import com.parkingupc.modelo.Tiquete;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TiqueteDaoArchivo implements TiqueteDao {
    private static final String RUTA_ARCHIVO = "datos/tiquetes.txt";

    @Override
    public void guardar(Tiquete tiquete) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            bw.write(tiquete.serializar());
            bw.newLine();
        }
    }

    @Override
    public List<Tiquete> leerTodos() throws IOException {
        List<Tiquete> tiquetes = new ArrayList<>();
        java.io.File file = new java.io.File(RUTA_ARCHIVO);
        if (!file.exists()) {
            return tiquetes;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
            }
        }
        return tiquetes;
    }

    @Override
    public void guardarTodos(List<Tiquete> tiquetes) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Tiquete tiquete : tiquetes) {
                bw.write(tiquete.serializar());
                bw.newLine();
            }
        }
    }
}