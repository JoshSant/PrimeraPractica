package com.example.primerapracticasantiagogarcajos;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    private final int REEQUEST_CODE_PERMISION = 4;

    public static final String TAG = MainActivity.class.getName() + "xyzyx";

    private TextView tvLLamadas;
    private Button btListaInt;
    private Button btListaExt;
    private LlamadaRepository llamadaRepository;
    public static Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(permisosAceptados() == false) {
            pedirPermisos();
        }

        llamadaRepository = new LlamadaRepository(this);

        application = this.getApplication();

        init();
    }



    private void init() {

        tvLLamadas = findViewById(R.id.tvLLamadas);
        btListaInt = findViewById(R.id.btListInterna);
        btListaExt = findViewById(R.id.btListExterna);

        btListaInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permisosAceptados()==true) {
                    tvLLamadas.setText(llamadaRepository.recibirListInterna());
                }else {
                    pedirPermisos();
                }
            }
        });
        btListaExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permisosAceptados()==true) {
                    tvLLamadas.setText(llamadaRepository.recibirListExterna());
                }else {
                    pedirPermisos();
                }
            }
        });

    }

    private void pedirPermisos(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS},
                    REEQUEST_CODE_PERMISION);
        }
    }

    private boolean permisosAceptados() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permisoState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int permisoCallLog = checkSelfPermission(Manifest.permission.READ_CALL_LOG);
            int permisoContacts = checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if (permisoContacts == PackageManager.PERMISSION_GRANTED &&
                    permisoState == PackageManager.PERMISSION_GRANTED &&
                    permisoCallLog == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

}