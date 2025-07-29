package com.example.appweb.MODELO;

public class Asistencia {
    private int id;
    private int idUsuario;
    private String tipo; // entrada o salida
    private String fecha;
    private String hora;

    public Asistencia(int id, int idUsuario, String tipo, String fecha, String hora) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() { return id; }
    public int getIdUsuario() { return idUsuario; }
    public String getTipo() { return tipo; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }

    public void setId(int id) { this.id = id; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setHora(String hora) { this.hora = hora; }

    public String toJson() {
        return String.format("{\"id\":%d,\"idUsuario\":%d,\"tipo\":\"%s\",\"fecha\":\"%s\",\"hora\":\"%s\"}",
                id, idUsuario, tipo, fecha, hora);
    }
}
