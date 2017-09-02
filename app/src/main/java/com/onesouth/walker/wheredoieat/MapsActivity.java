package com.onesouth.walker.wheredoieat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PROXIMITY_RADIUS = 5;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;

    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;

    private Location currentLocation;
    private Marker currentMarker;


    private boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        googleApiClient = new GoogleApiClient.Builder(this)

                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        getCurrentLocation();
    }

    private void loadNearByPlaces(double latitude, double longitude)    {

//
//        googleMap.clear();
//        Intent i = getIntent();
//        String type = "food";
//
//        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
//        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
//        googlePlacesUrl.append("&types=").append(type);
//        googlePlacesUrl.append("&sensor=true");
//        googlePlacesUrl.append("&key=" + getString(R.string.google_maps_key));
//
//        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject result) {
//
//                        //Log.i(TAG, "onResponse: Result= " + result.toString());
//                        parseLocationResult(result);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Log.e(TAG, "onErrorResponse: Error= " + error);
////                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
//                    }
//                });


    }



    public void getCurrentLocation(){
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        if (mLocationPermissionGranted) {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            LatLng tempLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            if(currentMarker != null){
                currentMarker.remove();
            }

            currentMarker = googleMap.addMarker(new MarkerOptions().position(tempLatLng).title("Current Location"));
            Toast.makeText(this, "Lat: " + tempLatLng.latitude + ", Long: " + tempLatLng.longitude, Toast.LENGTH_SHORT).show();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tempLatLng, DEFAULT_ZOOM));
        }
    }

    //Set Current Location
    public void OnFATClick(View view){
        //Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();

        getCurrentLocation();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
