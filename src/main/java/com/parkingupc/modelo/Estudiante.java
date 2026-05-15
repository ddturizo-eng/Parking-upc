package com.parkingupc.modelo;

// clase que represnta a un estudiante
public class Estudiante extends Persona {
    private String codigoEstudiantil;
    private String programaAcademico;

    public Estudiante(String id, String nombreCompleto, String telefono, String codigoEstudiantil, String programaAcademico) {
        super(id, nombreCompleto, telefono);
        this.codigoEstudiantil = codigoEstudiantil;
        this.programaAcademico = programaAcademico;
    }

    public String getCodigoEstudiantil() {
        return codigoEstudiantil;
    }

    public String getProgramaAcademico() {
        return programaAcademico;
    }

    @Override
    public double getDescuento() {
        return 0.30;
    }

    @Override
    public String getTipoPersona() {
        return "Estudiante";
    }

    @Override
    public String getDatosEspecificos() {
        return "Codigo: " + codigoEstudiantil + ", Programa: " + programaAcademico;
    }

    public String serializar() {
        return id + "|" + nombreCompleto + "|" + telefono + "|" + codigoEstudiantil + "|" + programaAcademico;
    }

    public static Estudiante deserializar(String linea) {
        String[] partes = linea.split("\\|");
        return new Estudiante(
            partes[0],
            partes[1],
            partes[2],
            partes[3],
            partes[4]
        );
    }
}