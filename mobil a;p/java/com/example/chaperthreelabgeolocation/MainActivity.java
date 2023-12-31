


 /*           GROUP MEMBERS
             NAME                             ID
      1.ISRAEL BALANGO                        0127/13
      2. BIRUK KIDANE                          0052/13
      3. EDILU SHATA                           0080/13
      4. MULU ABRHAM                           0172/13
      5. KAFITA KANTELA                       0133/13
      6.BOL OGUAK                              0055/13
     
      */


package com.example.chaperthreelabgeolocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public final static int LOCATION_CODE = 1;
    public final static Double[] LATITUDES = {7.0466257, 7.047338407935554,7.049061675005428};
    public final static Double[] LONGITUDES = {38.5001405,38.49966881479905, 38.50398799459389};
    TextView textView_lat, textView_lon, textView_address;
    Button button_getLocation;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_lat = findViewById(R.id.tv_lat);
        textView_lon = findViewById(R.id.tv_lon);
        button_getLocation = findViewById(R.id.location_button);
        textView_address = findViewById(R.id.address);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_CODE);
        }

        button_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getMyLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2, 5, MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double currentLat = location.getLatitude();
        double currentLon = location.getLongitude();
        boolean foundLocation = false;

        // Define the specific places and their corresponding coordinates
        Map<String, LatLng> places = new HashMap<>();
        places.put("INFORMATICS LAB", new LatLng(7.0466257, 38.5001405));
        places.put("Nisir Cyber Security", new LatLng(7.047338407935554, 38.49966881479905));
        places.put("IOT STUDENT DORMITORY", new LatLng(7.049061675005428, 38.50398799459389));

        for (Map.Entry<String, LatLng> entry : places.entrySet()) {
            String placeName = entry.getKey();
            LatLng placeLatLng = entry.getValue();

            double placeLat = placeLatLng.latitude;
            double placeLon = placeLatLng.longitude;

            if (Math.abs(currentLat - placeLat) < 0.0001 && Math.abs(currentLon - placeLon) < 0.0001) {
                textView_lat.setText(Double.toString(placeLat));
                textView_lon.setText(Double.toString(placeLon));
                textView_address.setText(placeName);

                foundLocation = true;
                break;
            }
        }

        if (!foundLocation) {
            Toast.makeText(this, currentLat + "," + currentLon + "," + location.getAccuracy(),
                    Toast.LENGTH_SHORT).show();

            textView_lat.setText(Double.toString(currentLat));
            textView_lon.setText(Double.toString(currentLon));

            try {
                Geocoder coder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = coder.getFromLocation(currentLat, currentLon, 1);
                String myCurrentPlace = addresses.get(0).getAddressLine(0);
                textView_address.setText(myCurrentPlace);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Implementation for onStatusChanged method
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        // Implementation for onProviderEnabled method
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        // Implementation for onProviderDisabled method
    }
     


}
