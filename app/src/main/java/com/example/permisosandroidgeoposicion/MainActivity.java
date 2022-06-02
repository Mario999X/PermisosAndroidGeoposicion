package com.example.permisosandroidgeoposicion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // escribimos el permiso en el manifest
    // <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    // <uses-feature android:name="android.hardware.location.gps" />

    Button btnGps;
    TextView textMensaje;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscarPermisos();

        enlazarComponentes();

        listeners();

    }

    private void buscarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permiso no aceptado, vamos a solicitarlo
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 999);
        }
    }

    private void enlazarComponentes() {
        btnGps = findViewById(R.id.btnGps);
        textMensaje = findViewById(R.id.textView);
    }

    private void listeners() {

        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                System.out.println("Click");
                Toast.makeText(MainActivity.this, "Obteniendo datos...", Toast.LENGTH_SHORT).show();
                // se define un listener que responde al cambio de localizacion.
                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        Toast.makeText(MainActivity.this, "Datos obtenidos", Toast.LENGTH_SHORT).show();
                        System.out.println("Funciona");
                        textMensaje.setText("Latitud: " + location.getLatitude() +"\nAltidud: " + location.getAltitude());

                    }
                };
                // registro del listener
                int permisosCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
            }
        });
    }

}