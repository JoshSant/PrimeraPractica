package com.example.primerapracticasantiagogarcajos;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.primerapracticasantiagogarcajos.util.LlamadasComparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.primerapracticasantiagogarcajos.MainActivity.TAG;

public class Ficheros {

    Context context;

    public Ficheros(Context context) {
        this.context = context.getApplicationContext();
    }

    private List<Llamadas> getLlamadasInternal() {
        List<Llamadas> listaDeLlamadas = new ArrayList<>();
        File f = new File(context.getFilesDir(), "historial.csv");
        try{
            BufferedReader br= new BufferedReader(new FileReader(f));
            String linea;
            StringBuilder texto = new StringBuilder();
            while ((linea = br.readLine()) != null){
                Log.v("xyz", linea);
                listaDeLlamadas.add(fromCSVStringInternal(linea, ";"));
            }
            br.close();
        }catch (IOException e){
        }
        if(listaDeLlamadas.size()!=0) {
            Log.v("xyz lista",listaDeLlamadas.toString());
        }
        return listaDeLlamadas;
    }

    public StringBuilder readLlamadasInternal() {
        File f = new File(context.getFilesDir(), "historial.csv");
        StringBuilder texto = new StringBuilder();
        try{
            BufferedReader br= new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null){
                fromCSVStringInternal(linea, ";");
                texto.append(linea);
                texto.append('\n');
            }
            br.close();
        }catch (IOException e){

        }
        return texto;
    }

    public boolean saveLlamadasInternal(Llamadas l) {
        List<Llamadas> llamadas = getLlamadasInternal();
        llamadas.add(l);
        Collections.sort(llamadas, new LlamadasComparator());
        boolean result = true;
        File f = new File(context.getFilesDir(), "historial.csv");
        FileWriter fw = null;
        f.delete();
        Log.v("xyz",llamadas.get(0).getNombre());
        try {
            fw = new FileWriter(f, true);
            for(int i = 0; i < llamadas.size(); i++) {
                fw.write(llamadas.get(i).toCsvInternal() + "\n");
            }
            fw.flush();
            fw.close();
        }catch (IOException e){
            result = false;
        }
        return result;
    }

    private List<Llamadas> getLlamadasExternal() {
        List<Llamadas> listaDeLlamadas = new ArrayList<>();
        File f = new File(context.getExternalFilesDir(null), "llamadas.csv");
        try{
            BufferedReader br= new BufferedReader(new FileReader(f));
            String linea;
            StringBuilder texto = new StringBuilder();
            while ((linea = br.readLine()) != null){
                Log.v("xyz", linea);
                listaDeLlamadas.add(fromCSVStringExternal(linea, ";"));
            }
            br.close();
        }catch (IOException e){
        }
        if(listaDeLlamadas.size()!=0) {
            Log.v("xyz lista",listaDeLlamadas.toString());
        }
        return listaDeLlamadas;
    }

    public StringBuilder readLlamadasExternal() {
        File f = new File(context.getExternalFilesDir(null), "llamadas.csv");
        StringBuilder texto = new StringBuilder();
        try{
            BufferedReader br= new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null){
                fromCSVStringExternal(linea, ";");
                texto.append(linea);
                texto.append('\n');
            }
            br.close();
        }catch (IOException e){

        }
        return texto;
    }

    public boolean saveLlamadasExternal(Llamadas l) {
        List<Llamadas> llamadas = getLlamadasExternal();
        llamadas.add(l);
        boolean result = true;
        File f = new File(context.getExternalFilesDir(null), "llamadas.csv");
        FileWriter fw = null;
        f.delete();
        Log.v("xyz",llamadas.get(0).getNombre());
        try {
            fw = new FileWriter(f, true);
            for(int i = 0; i < llamadas.size(); i++) {
                fw.write(llamadas.get(i).toCsvExternal() + "\n");
            }
            fw.flush();
            fw.close();
        }catch (IOException e){
            result = false;
        }
        return result;
    }

    public String getNombreLlamada(String telefono){

        Cursor mCursor = context.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[] {ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                        + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL", null,
                ContactsContract.Data.DISPLAY_NAME + " ASC");

        String name = "desconocido";

        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()){

            if(mCursor.getString(mCursor.getColumnIndex
                    (ContactsContract.CommonDataKinds.Phone.NUMBER)).equals(telefono)){
                name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            }
        }
        return name;
    }

    private Llamadas fromCSVStringExternal(String csv, String separador){
        String[] partes = csv.split(separador);
        Llamadas llamadas = null;
        String fecha = "";
        for(int i = 0; i < partes.length; i++){
            partes[i] = partes[i].trim();
        }
        for(int i = 1; i < partes.length-1; i++){
            fecha = fecha + partes[i].trim();
            if(i < partes.length -2){
                fecha = fecha +"; ";
            }
        }
        for (String parte: partes){
            Log.v(TAG, "*" + parte +"*");
        }
        if(partes.length == 8) {
            llamadas = new Llamadas(partes[0].trim(), partes[7].trim() , fecha);
        }
        return llamadas;
    }

    private Llamadas fromCSVStringInternal(String csv, String separador){
        String[] partes = csv.split(separador);
        Llamadas llamadas = null;
        String fecha = "";
        for(int i = 0; i < partes.length; i++){
            partes[i] = partes[i].trim();
        }
        for(int i = 0; i < partes.length-2; i++){
            fecha = fecha + partes[i].trim();
            if(i < partes.length -3){
                fecha = fecha +"; ";
            }
        }
        for (String parte: partes){
            Log.v(TAG, "*" + parte +"*");
        }
        if(partes.length == 8) {
            llamadas = new Llamadas(partes[7].trim(), partes[6].trim(), fecha);
        }
        return llamadas;
    }

}
