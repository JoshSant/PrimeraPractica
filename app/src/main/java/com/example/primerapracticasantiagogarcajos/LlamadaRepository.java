package com.example.primerapracticasantiagogarcajos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class LlamadaRepository {

    Ficheros ficheros;

    public LlamadaRepository(Context context) {
        this.ficheros = new Ficheros(context);
    }

    public void insertLlamada(Llamadas l) {

        LlamadasApplication.EjecutorDeHilos.execute(new Runnable() {
            @Override
            public void run() {
                Log.v("xyzLlamada","Llamada" + l.getNombre());
                ficheros.saveLlamadasInternal(l);
                ficheros.saveLlamadasExternal(l);
            }
        });
    }

    public StringBuilder recibirListInterna() {
        return ficheros.readLlamadasInternal();
    }

    public StringBuilder recibirListExterna() {
        return ficheros.readLlamadasExternal();
    }

    public String getNombreLlamada(String telefono){
        return ficheros.getNombreLlamada(telefono);
    }

}
