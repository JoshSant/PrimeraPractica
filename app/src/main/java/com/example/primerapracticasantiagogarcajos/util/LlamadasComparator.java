package com.example.primerapracticasantiagogarcajos.util;

import com.example.primerapracticasantiagogarcajos.Llamadas;

import java.util.Comparator;

public class LlamadasComparator implements Comparator<Llamadas> {

    @Override
    public int compare(Llamadas o1, Llamadas o2) {
        int sort = o1.getNombre().compareTo(o2.getNombre());
        if (sort == 0){
            sort = o1.getFechLlam().compareTo(o2.getFechLlam());
        }
        return sort;
    }

}
