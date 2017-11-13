package ca.uoit.csci4100u.lab08;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ShowLocation extends AppCompatActivity implements LocationListener {

    private static final int REQUEST_GEOLOCATION_PERMS = 1;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        verifyGeolocationPermission();
    }

    private void verifyGeolocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            String[] perms = new String[] { Manifest.permission.ACCESS_FINE_LOCATION };
            requestPermissions(perms, REQUEST_GEOLOCATION_PERMS);
        } else {
            verifyGeolocationEnabled();
        }
    }

    private void verifyGeolocationEnabled() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            requestLocationUpdates();
        } else {
            String locationSettings = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            Intent enableGeoloc = new Intent(locationSettings);
            startActivity(enableGeoloc);
        }
    }

    private void requestLocationUpdates() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    private void geocode(double latitude, double longitude) {
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> results = geocoder.getFromLocation(latitude, longitude, 1);
                for (Address address : results) {
                    ((TextView) findViewById(R.id.addressLine1)).setText(address.getAddressLine(0));
                    ((TextView) findViewById(R.id.addressLine2)).setText(address.getAddressLine(1));
                    ((TextView) findViewById(R.id.locality)).setText(address.getLocality());
                    ((TextView) findViewById(R.id.adminArea)).setText(address.getAdminArea());
                    ((TextView) findViewById(R.id.countryName)).setText(address.getCountryName());
                    ((TextView) findViewById(R.id.postalCode)).setText(address.getPostalCode());
                    ((TextView) findViewById(R.id.phoneNumber)).setText(address.getPhone());
                    ((TextView) findViewById(R.id.URL)).setText(address.getUrl());
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        geocode(location.getLatitude(), location.getLongitude());
        Log.i("AKIRA", "onLocationChanged");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {
        Log.i("AKIRA", "Provider ("+ provider +") status changed: " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("AKIRA", "Provider enabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("AKIRA", "Provider disabled: " + provider);
    }
}
