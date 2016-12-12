package com.example.tanvi.ireport.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.example.tanvi.ireport.Model.AddressClass;

import java.util.List;
import java.util.Locale;


/**
 * Created by Unmesh on 12/2/2016.
 */

public class LocationSettings extends Activity {


    Context context;
    Geocoder geocoder;
    List<Address> addressList;

    public LocationSettings(Activity activity) {
            this.context = activity.getApplicationContext();
    }

    //ProgressDialog progressDialog;
    LocationManager locationManager;
    LocationListener locationListener;
    double lat, lng;

    public void getCoordinates() {


//        if (lat == 0 && lng == 0) {
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Fetching Current Location...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

//        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
//                if (lat != 0 && lng != 0) {
//                    progressDialog.dismiss();
//                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {


                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
                return;
        }
    }

    private void getLocation() {

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
    }

    public AddressClass getAddress(){
        AddressClass addressClass = new AddressClass();
        getCoordinates();
        geocoder = new Geocoder(context, Locale.getDefault());
        try{
            addressList = geocoder.getFromLocation(lat,lng,1);
            addressClass.setStateName(addressList.get(0).getAdminArea());
            addressClass.setCityName(addressList.get(0).getLocality());
            addressClass.setCountryName(addressList.get(0).getCountryName());
            addressClass.setZipCode(addressList.get(0).getPostalCode());
            addressClass.setStreetAddress(addressList.get(0).toString());
            addressClass.setLatitude(Double.toString(lat));
            addressClass.setLongitude(Double.toString(lng));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return addressClass;

    }


}

