package org.example.tapesp2024.vistas;

public class Impresion {


    public int getNarchivo() {
        return Narchivo;
    }

    public void setNarchivo(int narchivo) {
        Narchivo = narchivo;
    }

    public String getNomarchivo() {
        return Nomarchivo;
    }

    public void setNomarchivo(String nomarchivo) {
        Nomarchivo = nomarchivo;
    }

    public int getNhojas() {
        return Nhojas;
    }

    public void setNhojas(int nhojas) {
        Nhojas = nhojas;
    }

    public String getHacceso() {
        return Hacceso;
    }

    public void setHacceso(String hacceso) {
        Hacceso = hacceso;
    }

    public Impresion(int narchivo, String nomarchivo, int nhojas, String hacceso) {
        Narchivo = narchivo;
        Nomarchivo = nomarchivo;
        Nhojas = nhojas;
        Hacceso = hacceso;
    }

    int Narchivo;
    String Nomarchivo;
    int Nhojas;
    String Hacceso;
}