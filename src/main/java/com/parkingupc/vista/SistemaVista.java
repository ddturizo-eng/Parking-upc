package com.parkingupc.vista;

import com.parkingupc.dao.*;
import com.parkingupc.modelo.*;
import com.parkingupc.servicio.ParqueoServicio;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SistemaVista {
    private final Scanner scanner;
    private final ParqueoServicio servicio;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public SistemaVista() {
        this.scanner = new Scanner(System.in);
        
        PersonaDao personaDao = new PersonaDaoArchivo();
        VehiculoDao vehiculoDao = new VehiculoDaoArchivo();
        TiqueteDao tiqueteDao = new TiqueteDaoArchivo();
        PuestoDao puestoDao = new PuestoDaoArchivo();
        
        this.servicio = new ParqueoServicio(personaDao, vehiculoDao, tiqueteDao, puestoDao);
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Entrada");
            System.out.println("2. Salida");
            System.out.println("3. Reporte");
            System.out.println("4. Salir");
            System.out.print("Opcion: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1 -> entrada();
                case 2 -> salida();
                case 3 -> reporte();
                case 4 -> continuar = false;
                default -> System.out.println("Opcion invalida");
            }
        }
    }

    private void entrada() {
        System.out.println("\n--- ENTRADA ---");
        
        System.out.print("Tipo persona (1-Est, 2-Doc, 3-Vis): ");
        int tp = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nom = scanner.nextLine();
        System.out.print("Tel: ");
        String tel = scanner.nextLine();
        
        Persona persona = crearPersona(tp, id, nom, tel);
        
        System.out.print("Tipo vehiculo (1-Moto, 2-Carro, 3-Camioneta): ");
        int tv = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        
        Vehiculo vehiculo = crearVehiculo(tv, placa, marca, color);
        
        System.out.print("Hora (HH:mm): ");
        String h = scanner.nextLine();
        LocalTime hora = LocalTime.parse(h, formatter);
        
        String resultado = servicio.registrarEntrada(persona, vehiculo, hora);
        System.out.println(resultado);
    }

    private Persona crearPersona(int tipo, String id, String nom, String tel) {
        return switch (tipo) {
            case 1 -> {
                System.out.print("Codigo est: ");
                String c = scanner.nextLine();
                System.out.print("Programa: ");
                String p = scanner.nextLine();
                yield new Estudiante(id, nom, tel, c, p);
            }
            case 2 -> {
                System.out.print("Codigo emp: ");
                String c = scanner.nextLine();
                System.out.print("Depto: ");
                String d = scanner.nextLine();
                yield new Docente(id, nom, tel, c, d);
            }
            case 3 -> {
                System.out.print("Empresa: ");
                String e = scanner.nextLine();
                System.out.print("Visitar a: ");
                String v = scanner.nextLine();
                yield new Visitante(id, nom, tel, e, v);
            }
            default -> null;
        };
    }

    private Vehiculo crearVehiculo(int tipo, String placa, String marca, String color) {
        return switch (tipo) {
            case 1 -> {
                System.out.print("Cilindraje: ");
                int c = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Cajuela (s/n): ");
                boolean caj = scanner.nextLine().equals("s");
                yield new Moto(placa, marca, color, c, caj);
            }
            case 2 -> {
                System.out.print("Puertas: ");
                int p = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Electrico (s/n): ");
                boolean e = scanner.nextLine().equals("s");
                yield new Carro(placa, marca, color, p, e);
            }
            case 3 -> {
                System.out.print("Ejes: ");
                int e = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Capacidad tons: ");
                double cap = scanner.nextDouble();
                scanner.nextLine();
                yield new Camioneta(placa, marca, color, e, cap);
            }
            default -> null;
        };
    }

    private void salida() {
        System.out.println("\n--- SALIDA ---");
        
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        
        if (!servicio.tieneVehiculo(placa)) {
            System.out.println("Vehiculo no esta en el parqueadero");
            return;
        }
        
        System.out.print("Hora salida (HH:mm): ");
        String h = scanner.nextLine();
        LocalTime hora = LocalTime.parse(h, formatter);
        
        Tiquete t = servicio.registrarSalida(placa, hora);
        if (t != null) {
            System.out.println(t.generarDetalleImpresion());
        } else {
            System.out.println("Error o hora invalida");
        }
    }

    private void reporte() {
        System.out.println(servicio.generarReporte());
    }
}