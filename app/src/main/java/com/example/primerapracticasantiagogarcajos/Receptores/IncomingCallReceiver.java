package com.example.primerapracticasantiagogarcajos.Receptores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.primerapracticasantiagogarcajos.LlamadaRepository;
import com.example.primerapracticasantiagogarcajos.Llamadas;
import com.example.primerapracticasantiagogarcajos.MainActivity;

import java.text.SimpleDateFormat;

import static com.example.primerapracticasantiagogarcajos.MainActivity.TAG;

public class IncomingCallReceiver extends BroadcastReceiver {

    Boolean register = false;

    public void onReceive(Context context, Intent intent) {

        try {
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);


        } catch (Exception e) {
            Log.v("Phone Receive Error", " " + e);
        }

    }

    private class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {

            Log.v("MyPhoneListener",state+"   incoming no:"+incomingNumber);

            if(!register) {
                if (state == 1) {

                    long date = System.currentTimeMillis();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy; MM; dd; hh; mm; ss");
                    String dateString = sdf.format(date);
                    Log.v(TAG, "Llamada:" + incomingNumber + "," + dateString);
                    LlamadaRepository llamadaRepository = new LlamadaRepository(MainActivity.application);
                    String nombre = llamadaRepository.getNombreLlamada(incomingNumber);

                    llamadaRepository.insertLlamada(new Llamadas(nombre, incomingNumber, dateString));

                }
                register = true;
            }
        }
    }



}
