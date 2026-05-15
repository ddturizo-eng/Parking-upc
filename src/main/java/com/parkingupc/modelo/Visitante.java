package com.parkingupc.modelo;

public class Visitante extends Persona {
    private String empresaRemite;
    private String personavisita;

    public Visitante(String id, String nombreCompleto, String telefono, String empresaRemite, String personavisita) {
        super(id, nombreCompleto, telefono);
        this.empresaRemite = empresaRemite;
        this.personavisita = personavisita;
    }

    public String getEmpresaRemite() {
        return empresaRemite;
    }

    public String getPersonavisita() {
        return personavisita;
    }

    @Override
    public double getDescuento() {
        return 0.0;// segun entendí no tiene descuentooo
    }

    @Override
    public String getTipoPersona() {
        return "Visitante";
    }

    @Override
    public String getDatosEspecificos() {
        return "Empresa: " + empresaRemite + ", Visita a: " + personavisita;
    }

    public String serializar() {
        return id + "|" + nombreCompleto + "|" + telefono + "|" + empresaRemite + "|" + personavisita;
    }

    public static Visitante deserializar(String linea) {
        String[] partes = linea.split("\\|");
        return new Visitante(
            partes[0],
            partes[1],
            partes[2],
            partes[3],
            partes[4]
        );
    }
}