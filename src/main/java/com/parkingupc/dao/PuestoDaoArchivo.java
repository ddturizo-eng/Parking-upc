package com.parkingupc.dao;

import com.parkingupc.modelo.Puesto;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PuestoDaoArchivo implements PuestoDao {
    private static final String RUTA_ARCHIVO = "datos/puestos.txt";

    @Override
    public void guardar(Puesto puesto) throws IOException {
        List<Puesto> puestos = leerTodos();
        puestos.add(puesto);
        guardarTodos(puestos);
    }

    @Override
    public List<Puesto> leerTodos() throws IOException {
        List<Puesto> puestos = new ArrayList<>();
        java.io.File file = new java.io.File(RUTA_ARCHIVO);
        if (!file.exists()) {
            return inicializarPuestos();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Puesto puesto = Puesto.deserializar(linea);
                if (puesto != null) {
                    puestos.add(puesto);
                }
            }
        }
        return puestos.isEmpty() ? inicializarPuestos() : puestos;
    }

    @Override
    public void guardarTodos(List<Puesto> puestos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Puesto puesto : puestos) {
                bw.write(puesto.serializar());
                bw.newLine();
            }
        }
    }

    private List<Puesto> inicializarPuestos() {
        List<Puesto> puestos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            puestos.add(new Puesto("M-" + i, "Moto"));
        }
        for (int i = 1; i <= 15; i++) {
            puestos.add(new Puesto("C-" + i, "Carro"));
        }
        for (int i = 1; i <= 5; i++) {
            puestos.add(new Puesto("CA-" + i, "Camioneta"));
        }
        return puestos;
    }
}