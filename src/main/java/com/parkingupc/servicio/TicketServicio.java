package com.parkingupc.servicio;

import com.parkingupc.dao.TiqueteDao;
import com.parkingupc.modelo.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class TicketServicio {
    private final TiqueteDao tiqueteDao;
    private int contador = 1;

    public TicketServicio(TiqueteDao tiqueteDao) {
        this.tiqueteDao = tiqueteDao;
    }

    public void guardar(Tiquete tiquete) throws IOException {
        tiqueteDao.guardar(tiquete);
    }

    public List<Tiquete> listar() throws IOException {
        return tiqueteDao.leerTodos();
    }

    public String nuevoId() {
        return "T-" + contador++;
    }

    public Tiquete crear(String id, Persona p, Vehiculo v, String puesto, LocalTime entrada, LocalTime salida,
                     int tiempo, List<Tiquete.DetalleFranja> df, double bruto, double desc, double valDesc, double valFinal) {
        return new Tiquete(id, p, v, puesto, entrada, salida, tiempo, df, bruto, desc, valDesc, valFinal);
    }
}