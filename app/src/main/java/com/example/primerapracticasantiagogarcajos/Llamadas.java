package com.example.primerapracticasantiagogarcajos;

import android.util.Log;

import static com.example.primerapracticasantiagogarcajos.MainActivity.TAG;

public class Llamadas {

    private String nombre;
    private String telefono;
    private String fechLlam;

    public Llamadas(String nombre, String telefono, String fechLlam) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechLlam = fechLlam;
    }

    public String toCsvExternal(){
        return nombre + "; " + fechLlam +"; " + telefono;
    }

    public String toCsvInternal(){
        return fechLlam + "; " + telefono +"; " + nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechLlam() {
        return fechLlam;
    }

    public void setFechLlam(String fechLlam) {
        this.fechLlam = fechLlam;
    }
}
