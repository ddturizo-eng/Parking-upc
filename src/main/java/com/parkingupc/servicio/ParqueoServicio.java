package com.parkingupc.servicio;

import com.parkingupc.dao.*;
import com.parkingupc.modelo.*;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ParqueoServicio {
    private final PersonaDao personaDao;
    private final VehiculoDao vehiculoDao;
    private final TiqueteDao tiqueteDao;
    private final PuestoDao puestoDao;

    private static final int[][] TARIFAS = {{800, 1000, 1500}, {1500, 2000, 2500}, {2000, 2500, 3500}};

    private final Map<String, Puesto> puestosOcupados = new HashMap<>();
    private double totalRecaudado = 0;
    private int contadorTiquetes = 1;

    public ParqueoServicio(PersonaDao personaDao, VehiculoDao vehiculoDao, 
                          TiqueteDao tiqueteDao, PuestoDao puestoDao) {
        this.personaDao = personaDao;
        this.vehiculoDao = vehiculoDao;
        this.tiqueteDao = tiqueteDao;
        this.puestoDao = puestoDao;
    }

    public String registrarEntrada(Persona persona, Vehiculo vehiculo, LocalTime hora) {
        try {
            if (hora.getHour() < 6 || hora.getHour() >= 23) {
                return "Hora inválida (06:00-23:00)";
            }
            
            long ocupados = puestosOcupados.values().stream()
                .filter(p -> p.getTipoVehiculo().equals(vehiculo.getTipoVehiculo())).count();
            
            int limite = vehiculo.getTipoVehiculo().equals("Moto") ? 10 : 
                       vehiculo.getTipoVehiculo().equals("Carro") ? 15 : 5;
            
            if (ocupados >= limite) {
                return "No hay puestos disponibles";
            }
            
            personaDao.guardar(persona, persona.getTipoPersona());
            vehiculoDao.guardar(vehiculo, vehiculo.getTipoVehiculo());
            
            List<Puesto> todos = puestoDao.leerTodos();
            Puesto puesto = todos.stream()
                .filter(p -> p.getTipoVehiculo().equals(vehiculo.getTipoVehiculo()) && !p.isOcupado())
                .findFirst().orElse(null);
            
            if (puesto == null) return "No hay puestos disponibles";
            
            puesto.ocupar(vehiculo.getPlaca());
            puestosOcupados.put(vehiculo.getPlaca(), puesto);
            puestoDao.guardarTodos(todos);
            
            return "Entrada registrada - Puesto: " + puesto.getNumero();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    public Tiquete registrarSalida(String placa, LocalTime hora) {
        try {
            if (hora.getHour() < 6 || hora.getHour() >= 23) {
                return null;
            }
            
            Puesto puesto = puestosOcupados.get(placa);
            if (puesto == null) return null;
            
            Optional<Vehiculo> optV = vehiculoDao.buscarPorPlaca(placa, "Moto");
            if (optV.isEmpty()) optV = vehiculoDao.buscarPorPlaca(placa, "Carro");
            if (optV.isEmpty()) optV = vehiculoDao.buscarPorPlaca(placa, "Camioneta");
            Vehiculo vehiculo = optV.orElse(null);
            
            List<Persona> personas = new ArrayList<>();
            try { personas.addAll(personaDao.leerTodos("Estudiante")); } catch (Exception e) {}
            try { personas.addAll(personaDao.leerTodos("Docente")); } catch (Exception e) {}
            try { personas.addAll(personaDao.leerTodos("Visitante")); } catch (Exception e) {}
            
            Persona persona = personas.stream()
                .filter(p -> placa.startsWith(p.getId().substring(0, 3)))
                .findFirst().orElse(null);
            
            LocalTime entrada = LocalTime.of(6, 0);
            List<Tiquete.DetalleFranja> detalle = new ArrayList<>();
            
            int indice = vehiculo.getTipoVehiculo().equals("Moto") ? 0 : 
                         vehiculo.getTipoVehiculo().equals("Carro") ? 1 : 2;
            
            long horasManana = ChronoUnit.HOURS.between(entrada, LocalTime.of(12, 0));
            if (horasManana > 0) {
                detalle.add(new Tiquete.DetalleFranja("Manana", (int) horasManana, TARIFAS[indice][0], horasManana * TARIFAS[indice][0]));
            }
            
            long horasTarde = ChronoUnit.HOURS.between(LocalTime.of(12, 0), LocalTime.of(19, 0));
            if (horasTarde > 0 && hora.isAfter(LocalTime.of(12, 0))) {
                detalle.add(new Tiquete.DetalleFranja("Tarde", (int) horasTarde, TARIFAS[indice][1], horasTarde * TARIFAS[indice][1]));
            }
            
            long horasNoche = ChronoUnit.HOURS.between(LocalTime.of(19, 0), hora);
            if (horasNoche > 0) {
                detalle.add(new Tiquete.DetalleFranja("Noche", (int) horasNoche, TARIFAS[indice][2], horasNoche * TARIFAS[indice][2]));
            }
            
            int tiempo = detalle.stream().mapToInt(Tiquete.DetalleFranja::getHoras).sum();
            double valorBruto = detalle.stream().mapToDouble(Tiquete.DetalleFranja::getSubtotal).sum();
            
            double descuento = persona != null ? persona.getDescuento() : 0;
            double valor = valorBruto * (1 - descuento);
            
            String id = "T-" + contadorTiquetes++;
            Tiquete tiquete = new Tiquete(id, persona, vehiculo, puesto.getNumero(), 
                entrada, hora, tiempo, detalle, valorBruto, descuento, 
                valorBruto * descuento, valor);
            
            tiqueteDao.guardar(tiquete);
            
            puesto.liberar();
            puestosOcupados.remove(placa);
            totalRecaudado += valor;
            puestoDao.guardarTodos(puestoDao.leerTodos());
            
            return tiquete;
        } catch (IOException e) {
            return null;
        }
    }

    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PARKING UPC ===\n\n");
        sb.append("Moto: ").append(contar("Moto")).append("/10\n");
        sb.append("Carro: ").append(contar("Carro")).append("/15\n");
        sb.append("Camioneta: ").append(contar("Camioneta")).append("/5\n\n");
        sb.append("Total recaudo: $").append(totalRecaudado).append("\n");
        
        if (!puestosOcupados.isEmpty()) {
            sb.append("\nVehiculos:\n");
            for (var e : puestosOcupados.entrySet()) {
                sb.append("- ").append(e.getKey()).append(" -> Puesto ").append(e.getValue().getNumero()).append("\n");
            }
        }
        
        return sb.toString();
    }

    private int contar(String tipo) {
        return (int) puestosOcupados.values().stream()
            .filter(p -> p.getTipoVehiculo().equals(tipo)).count();
    }

    public boolean tieneVehiculo(String placa) {
        return puestosOcupados.containsKey(placa);
    }
}