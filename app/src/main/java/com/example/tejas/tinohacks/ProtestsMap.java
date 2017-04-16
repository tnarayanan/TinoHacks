package com.example.tejas.tinohacks;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Geocoder;
import android.location.Address;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ProtestsMap extends FragmentActivity implements OnMapReadyCallback {

//    private GoogleMap mMap;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_protests_map);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//
//        try{
//            mapFragment.getMapAsync(this);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        setUpMap();
//        LatLng pos = getLocationFromAddress(getApplicationContext(),ProtestOrganization.userAddress);
//        mMap.addMarker(new MarkerOptions().position(pos).title("This protest is organized for this policy: " + "ProtestOrganization.policy").snippet("Time: " + ProtestOrganization.protestTime  + " Date: " + ProtestOrganization.protestDate));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
//    }
//
//

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        Log.i("", strAddress);

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;

            } else {
                Address location = address.get(0);

                Log.i("", address.get(0).toString());


                p1 = new LatLng(location.getLatitude(), location.getLongitude() );

                Log.i("whaaat", p1.toString());

            }


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


//
//    private void setUpMap(){
//        LatLng pos = getLocationFromAddress(getApplicationContext(),ProtestOrganization.userAddress);
//        Toast.makeText(this, pos.toString(), Toast.LENGTH_SHORT).show();
//
//
//
//    }
//
//    public void backButtonClicked(View view){
//
//        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(i);
//    }


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protests_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng TutorialsPoint = new LatLng(21, 57);
       //LatLng pos = getLocationFromAddress(getApplicationContext(),ProtestOrganization.userAddress);
//        mMap.addMarker(new
//                MarkerOptions().position(pos).title("Protest"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }

}
