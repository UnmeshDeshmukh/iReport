package com.example.tanvi.ireport.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tanvi.ireport.Model.GetComplaintData;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.GetOperationComplaintReport;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapOfficialActivity extends AppCompatActivity implements OnMapReadyCallback{

    JSONObject object;
    GoogleMap googleMaps;
    ArrayList<GetComplaintData> complaintArrayList = new ArrayList<GetComplaintData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_official);

        callToGet();
        mapMarkerSetup();
    }

    private void mapMarkerSetup() {
        if(googleMaps==null){

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentOfficial);
            mapFragment.getMapAsync(this);
        }
    }


    public void callToGet() {

        try {
            complaintArrayList = new GetOperationComplaintReport().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMaps = googleMap;
        showData(googleMaps);
    }





    private void showData(GoogleMap googleMap) {
        for(int i=0;i<complaintArrayList.size();i++){
            LatLng latLng = new LatLng(Double.parseDouble(complaintArrayList.get(i).getLatitude()),Double.parseDouble(complaintArrayList.get(i).getLongitude()));
            if (i == 0) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng).zoom(13).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(Integer.toString(complaintArrayList.get(i).getId()))
                    .snippet(complaintArrayList.get(i).getDescrition())
                    .position(new LatLng(Double.parseDouble(complaintArrayList.get(i).getLatitude()),Double.parseDouble(complaintArrayList.get(i).getLongitude())))
            );
        }
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.map_info_layout, null);
                TextView complaintId = (TextView) v.findViewById(R.id.complaintidWindow);
                TextView description = (TextView) v.findViewById(R.id.descriptionWindow);
                complaintId.setText(marker.getTitle());
                description.setText(marker.getSnippet());
                return v;

            }
        });
        googleMaps.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                ListViewFragment fragment = new ListViewFragment();
                int complaintId = Integer.parseInt(marker.getTitle());
                bundle.putInt("complaintId",complaintId);
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mapofficiallayout,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mapofficiallayout,fragment).commit();

                return true;
            }
        });
    }
}
