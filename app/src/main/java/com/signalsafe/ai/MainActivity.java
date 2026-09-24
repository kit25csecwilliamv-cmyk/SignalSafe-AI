package com.signalsafe.ai;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends ComponentActivity {

    private static final int PERMISSION_REQUEST = 100;
    private BluetoothAdapter bluetoothAdapter;
    private TextView networkStatus;
    private TextView lastAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkStatus = findViewById(R.id.networkStatus);
        lastAlert = findViewById(R.id.lastAlert);
        MaterialButton sosButton = findViewById(R.id.sosButton);
        Button scanButton = findViewById(R.id.scanButton);
        Button locationButton = findViewById(R.id.locationButton);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        requestPermissionsIfNeeded();

        sosButton.setOnClickListener(v -> sendSOS());
        scanButton.setOnClickListener(v -> scanNearbyDevices());
        locationButton.setOnClickListener(v ->
                Toast.makeText(this, "Location sharing module ready.", Toast.LENGTH_SHORT).show());
    }

    private void requestPermissionsIfNeeded() {
        if (android.os.Build.VERSION.SDK_INT >= 31) {
            String[] permissions = {
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
        }
    }

    private void sendSOS() {
        lastAlert.setText("SOS created • HIGH PRIORITY • Ready to relay");
        Toast.makeText(this, "SOS alert created", Toast.LENGTH_LONG).show();
        // Next step: connect this event to BLE/Wi-Fi Direct relay and Firebase sync.
    }

    private void scanNearbyDevices() {
        if (bluetoothAdapter == null) {
            networkStatus.setText("Bluetooth unavailable on this device");
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            try {
                startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
            } catch (SecurityException e) {
                Toast.makeText(this, "Bluetooth permission required.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        networkStatus.setText("Local network active • scanning for nearby devices...");
        Toast.makeText(this, "Nearby-device scan started", Toast.LENGTH_SHORT).show();
        // Next step: implement Bluetooth LE discovery/advertising.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST) {
            networkStatus.setText("Local communication permissions checked");
        }
    }
}
