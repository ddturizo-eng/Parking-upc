package com.parkingupc.modelo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Tiquete {
    private String idTiquete;
    private Persona persona;
    private Vehiculo vehiculo;
    private String numeroPuesto;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private int tiempoTotalHoras;
    private List<DetalleFranja> detalleFranjas;
    private double valorBruto;
    private double descuentoPorcentaje;
    private double valorDescuento;
    private double valorFinal;

    public Tiquete(String idTiquete, Persona persona, Vehiculo vehiculo, String numeroPuesto,
                   LocalTime horaEntrada, LocalTime horaSalida, int tiempoTotalHoras,
                   List<DetalleFranja> detalleFranjas, double valorBruto, double descuentoPorcentaje,
                   double valorDescuento, double valorFinal) {
        this.idTiquete = idTiquete;
        this.persona = persona;
        this.vehiculo = vehiculo;
        this.numeroPuesto = numeroPuesto;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.tiempoTotalHoras = tiempoTotalHoras;
        this.detalleFranjas = detalleFranjas;
        this.valorBruto = valorBruto;
        this.descuentoPorcentaje = descuentoPorcentaje;
        this.valorDescuento = valorDescuento;
        this.valorFinal = valorFinal;
    }

    public String getIdTiquete() {
        return idTiquete;
    }

    public Persona getPersona() {
        return persona;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public String getNumeroPuesto() {
        return numeroPuesto;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public int getTiempoTotalHoras() {
        return tiempoTotalHoras;
    }

    public List<DetalleFranja> getDetalleFranjas() {
        return detalleFranjas;
    }

    public double getValorBruto() {
        return valorBruto;
    }

    public double getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public double getValorDescuento() {
        return valorDescuento;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public String serializar() {
        StringBuilder sb = new StringBuilder();
        sb.append("TIQUETE|").append(idTiquete).append("|");
        sb.append(persona.getId()).append("|").append(persona.getTipoPersona()).append("|");
        sb.append(persona.getNombreCompleto()).append("|").append(persona.getTelefono()).append("|");
        sb.append(persona.getDatosEspecificos().replace("|", "-")).append("|");
        sb.append(vehiculo.getPlaca()).append("|").append(vehiculo.getMarca()).append("|");
        sb.append(vehiculo.getColor()).append("|").append(vehiculo.getTipoVehiculo()).append("|");
        sb.append(vehiculo.getDatosEspecificos().replace("|", "-")).append("|");
        sb.append(numeroPuesto).append("|");
        sb.append(horaEntrada.format(DateTimeFormatter.ofPattern("HH:mm"))).append("|");
        sb.append(horaSalida.format(DateTimeFormatter.ofPattern("HH:mm"))).append("|");
        sb.append(tiempoTotalHoras).append("|");
        sb.append(valorBruto).append("|");
        sb.append(descuentoPorcentaje).append("|");
        sb.append(valorDescuento).append("|");
        sb.append(valorFinal).append("|");
        for (DetalleFranja df : detalleFranjas) {
            sb.append(df.getFranja()).append("=").append(df.getHoras()).append("=").append(df.getTarifa()).append("=").append(df.getSubtotal()).append(";");
        }
        return sb.toString();
    }

    public static Tiquete deserializar(String linea) {
        return null;
    }

    public String generarDetalleImpresion() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════\n");
        sb.append("          TIQUETE DE COBRO - PARKING UPC\n");
        sb.append("═══════════════════════════════════════════\n\n");
        sb.append("DATOS DEL CLIENTE:\n");
        sb.append("  Nombre: ").append(persona.getNombreCompleto()).append("\n");
        sb.append("  Tipo: ").append(persona.getTipoPersona()).append("\n");
        sb.append("  ").append(persona.getDatosEspecificos()).append("\n\n");
        sb.append("DATOS DEL VEHICULO:\n");
        sb.append("  Placa: ").append(vehiculo.getPlaca()).append("\n");
        sb.append("  Marca: ").append(vehiculo.getMarca()).append("\n");
        sb.append("  Color: ").append(vehiculo.getColor()).append("\n");
        sb.append("  Tipo: ").append(vehiculo.getTipoVehiculo()).append("\n");
        sb.append("  ").append(vehiculo.getDatosEspecificos()).append("\n\n");
        sb.append("Puesto Asignado: ").append(numeroPuesto).append("\n\n");
        sb.append("TIEMPO DE PERMANENCIA:\n");
        sb.append("  Entrada: ").append(horaEntrada.format(formatter)).append("\n");
        sb.append("  Salida: ").append(horaSalida.format(formatter)).append("\n");
        sb.append("  Total: ").append(tiempoTotalHoras).append(" hora(s)\n\n");
        sb.append("DETALLE DE FRANJAS HORARIAS:\n");
        for (DetalleFranja df : detalleFranjas) {
            sb.append("  ").append(df.getFranja()).append(": ").append(df.getHoras()).append("h x $");
            sb.append(df.getTarifa()).append(" = $").append(df.getSubtotal()).append("\n");
        }
        sb.append("\n");
        sb.append("VALOR BRUTO: $").append(valorBruto).append("\n");
        sb.append("DESCUENTO (").append((int)(descuentoPorcentaje * 100)).append("%): -$").append(valorDescuento).append("\n");
        sb.append("═══════════════════════════════════════════\n");
        sb.append("TOTAL A PAGAR: $").append(valorFinal).append("\n");
        sb.append("═══════════════════════════════════════════\n");
        return sb.toString();
    }

    public static class DetalleFranja {
        private String franja;
        private int horas;
        private int tarifa;
        private double subtotal;

        public DetalleFranja(String franca, int horas, int tarifa, double subtotal) {
            this.franja = franca;
            this.horas = horas;
            this.tarifa = tarifa;
            this.subtotal = subtotal;
        }

        public String getFranja() {
            return franja;
        }

        public int getHoras() {
            return horas;
        }

        public int getTarifa() {
            return tarifa;
        }

        public double getSubtotal() {
            return subtotal;
        }
    }
}