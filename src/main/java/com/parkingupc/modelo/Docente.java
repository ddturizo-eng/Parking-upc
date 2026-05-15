package com.parkingupc.modelo;

public class Docente extends Persona {
    private String codigoEmpleado;
    private String departamento;

    public Docente(String id, String nombreCompleto, String telefono, String codigoEmpleado, String departamento) {
        super(id, nombreCompleto, telefono);
        this.codigoEmpleado = codigoEmpleado;
        this.departamento = departamento;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public String getDepartamento() {
        return departamento;
    }

    @Override
    public double getDescuento() {
        return 0.20;
    }

    @Override
    public String getTipoPersona() {
        return "Docente";
    }

    @Override
    public String getDatosEspecificos() {
        return "Codigo: " + codigoEmpleado + ", Departamento: " + departamento;
    }

    public String serializar() {
        return id + "|" + nombreCompleto + "|" + telefono + "|" + codigoEmpleado + "|" + departamento;// siguiendo el mismo patron para todas las clases
    }

    public static Docente deserializar(String linea) {
        String[] partes = linea.split("\\|");
        return new Docente(
            partes[0],
            partes[1],
            partes[2],
            partes[3],
            partes[4]
        );
    }
}