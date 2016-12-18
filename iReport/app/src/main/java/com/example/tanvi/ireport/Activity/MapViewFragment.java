package com.example.tanvi.ireport.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tanvi.ireport.Model.GetComplaintData;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.GetDataForMapMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapViewFragment extends Fragment implements OnMapReadyCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentActivity context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String user;
    private OnFragmentInteractionListener mListener;
    ArrayList<GetComplaintData> complaintReportsArrayList = new ArrayList<>();
    //GetComplaintData getComplaintData= new GetComplaintData();
    MapView mapView;
    JSONObject object;
    GoogleMap googleMaps;
    GetComplaintData getComplaintData;
    public MapViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapViewFragment newInstance(String param1, String param2) {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mapFragmentView = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapMarkerSetUp(mapFragmentView);
        user = getArguments().getString("Email");
        object = callToPost();
        return mapFragmentView;




    }

    private JSONObject callToPost() {
        final JSONObject userDict = new JSONObject();
        JSONObject jsonObject= new JSONObject();
        try{
            userDict.put("email",user);

        }catch (Exception ex){

        }
        try {
            jsonObject = new GetDataForMapMarker().execute(String.valueOf(userDict)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void parseJSON(JSONObject jsonObject,GoogleMap googleMaps){
        JSONArray complaintsArray = null;
        try {
            complaintsArray = jsonObject.getJSONArray("complaints");
            for(int i=0;i< complaintsArray.length();i++){
                //
                getComplaintData = new GetComplaintData();
                getComplaintData.setId(complaintsArray.getJSONObject(i).getInt("id"));
                getComplaintData.setDescrition(complaintsArray.getJSONObject(i).getString("description"));
                getComplaintData.setPriority(complaintsArray.getJSONObject(i).getString("priority"));
                getComplaintData.setStatus(complaintsArray.getJSONObject(i).getString("status"));
                getComplaintData.setLabel(complaintsArray.getJSONObject(i).getString("label"));
                getComplaintData.setAccesslevel(complaintsArray.getJSONObject(i).getString("accesslevel"));
                getComplaintData.setSize(complaintsArray.getJSONObject(i).getString("size"));
                getComplaintData.setLongitude(complaintsArray.getJSONObject(i).getString("longitude"));
                getComplaintData.setLatitude(complaintsArray.getJSONObject(i).getString("latitude"));
                getComplaintData.setStreet(complaintsArray.getJSONObject(i).getString("street"));
                getComplaintData.setState(complaintsArray.getJSONObject(i).getString("state"));
                getComplaintData.setEmail(complaintsArray.getJSONObject(i).getString("email"));
                getComplaintData.setReported_by(complaintsArray.getJSONObject(i).getString("reported_by"));
                getComplaintData.setCreated_at(complaintsArray.getJSONObject(i).getString("created_at"));

                System.out.println("THE PARSED JSON ARRAY:"+complaintsArray.getJSONObject(i).getInt("id")+"The lat is"+complaintsArray.getJSONObject(i).getString("longitude")+complaintsArray.getJSONObject(i).getString("latitude"));
                LatLng latLng = new LatLng(Double.parseDouble(getComplaintData.getLatitude()),Double.parseDouble(getComplaintData.getLongitude()));
                if (i == 0) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng).zoom(13).build();
                    googleMaps.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }


                complaintReportsArrayList.add(getComplaintData);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void mapMarkerSetUp(View mapFragmentView) {
        if(googleMaps==null){
            android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            //SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(this);
        }

    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        context = (FragmentActivity) getActivity();
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMaps = googleMap;
        parseJSON(object,googleMaps);
        for(int i=0;i<complaintReportsArrayList.size();i++){
            googleMaps.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(Integer.toString(complaintReportsArrayList.get(i).getId()))
                    .snippet(complaintReportsArrayList.get(i).getDescrition())
                    .position(new LatLng(Double.parseDouble(complaintReportsArrayList.get(i).getLatitude()),Double.parseDouble(complaintReportsArrayList.get(i).getLongitude()))));
            System.out.println("The Id before:"+complaintReportsArrayList.get(i).getId());
        }
        googleMaps.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }
            @Override
            public View getInfoContents(Marker marker) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.map_info_layout, null);
                TextView complaintId = (TextView) v.findViewById(R.id.complaintidWindow);
                TextView description = (TextView) v.findViewById(R.id.descriptionWindow);
                complaintId.setText(marker.getTitle());
                System.out.println("The Marker Title:"+marker.getId()+"-----------"+marker.getSnippet());
                description.setText(marker.getSnippet());
                return v;
            }
        });
        googleMaps.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment =new ListViewFragment();
                Bundle bundle = new Bundle();

                int complaintId = Integer.parseInt(marker.getTitle());
                System.out.println(complaintId);
                bundle.putInt("complaintId",complaintId);

                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(getId(),fragment).commit();
                return true;
            }
        });



    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
