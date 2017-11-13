package ca.uoit.csci4100u.lab09;

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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ShowLocation extends FragmentActivity implements LocationListener, OnMapReadyCallback {

    private static final int REQUEST_GEOLOCATION_PERMS = 1;
    private LocationManager mLocationManager;
    private GoogleMap mMap;
    private double mLatitude, mLongitude;
    private String mMarkerInfo;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setTrafficEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        verifyGeolocationPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        }
    }

    private void geocode(double latitude, double longitude) {
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> results = geocoder.getFromLocation(latitude, longitude, 1);
                for (Address address : results) {
                    mMarkerInfo += address.getAddressLine(0);
                    mMarkerInfo += address.getAddressLine(1);
                    mMarkerInfo += address.getLocality();
                    mMarkerInfo += address.getAdminArea();
                    mMarkerInfo += address.getCountryName();
                    mMarkerInfo += address.getPostalCode();
                    mMarkerInfo += address.getPhone();
                    mMarkerInfo += address.getUrl();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        MarkerOptions options = new MarkerOptions();
        LatLng position = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(options.position(position).title(mMarkerInfo));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(position));

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
