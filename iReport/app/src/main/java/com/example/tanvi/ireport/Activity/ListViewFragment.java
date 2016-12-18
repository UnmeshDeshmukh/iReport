package com.example.tanvi.ireport.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tanvi.ireport.Model.GetComplaintData;
import com.example.tanvi.ireport.Model.LitterComplaint;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.GetComplaintById;
import com.example.tanvi.ireport.Utility.GetDataForMapMarker;
import com.example.tanvi.ireport.Utility.ImageLoadTask;
import com.example.tanvi.ireport.Utility.PostOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.R.attr.id;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  ListViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LocationManager locationManager;
    LocationListener locationListener;
    int complaintId;
    Double lat=0.00;
    Double lng =0.00;
    // TODO: Rename and change types of parameters

    TextView txtStatus, txtSeverity, txtDescription, txtSize, txtLocation;
    ImageView complaintView;

    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    private OnFragmentInteractionListener mListener;
    Double intentLat,intentLong;
    JSONObject object;
    Spinner sizeSpinner;
    GetComplaintData getComplaintData= new GetComplaintData();
    public ListViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListViewFragment newInstance(String param1, String param2) {
        ListViewFragment fragment = new ListViewFragment();
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
        View listView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        sizeSpinner = (Spinner) listView.findViewById(R.id.statusSpinner);
        txtSeverity = (TextView) listView.findViewById(R.id.textSeverity);
        txtSize = (TextView) listView.findViewById(R.id.textSize);
        txtLocation = (TextView) listView.findViewById(R.id.textLocation);
        txtDescription = (TextView) listView.findViewById(R.id.textDescription);
        complaintView = (ImageView) listView.findViewById(R.id.reportImage);
        complaintId = getArguments().getInt("complaintId");
        setSpinner(listView);
        //getCoordinates();
        object = getComplaintDetails();
        parseJSON(object);
        setValues();
        sizeSpinner.setEnabled(true);
//        if(isAtLocation(lat,lng,intentLat,intentLong)){
//            sizeSpinner.setEnabled(true);
//        }
        return listView;
    }

    private void setSpinner(View homeView) {


        ArrayAdapter<CharSequence> sizeArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.litterStatusArrayUser, android.R.layout.simple_spinner_item);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeArrayAdapter);
//        sizeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                adapterView.getItemAtPosition(i).toString();
//                callToPost(adapterView.getItemAtPosition(i).toString());
//            }
//        });


        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i).toString();
                callToPost(adapterView.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void callToPost(String s) {
        final JSONObject userDict = new JSONObject();
        JSONObject jsonObject= new JSONObject();
        try{
            userDict.put("id",complaintId);
            userDict.put("status",s);

        }catch (Exception ex){

        }
        new PostOperations("complaint").execute(String.valueOf(userDict));
    }

    private JSONObject getComplaintDetails(){
        final JSONObject userDict = new JSONObject();
        JSONObject jsonObject= new JSONObject();
        try{
            userDict.put("id",complaintId);
        }catch (Exception ex){
        }
        try {
            jsonObject = new GetComplaintById().execute(String.valueOf(userDict)).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void parseJSON(JSONObject jsonObject){
        JSONArray complaintsArray = null;
        try {
            complaintsArray = jsonObject.getJSONArray("complaint");
            for(int i=0;i< complaintsArray.length();i++){
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
                getComplaintData.setImage(complaintsArray.getJSONObject(i).getString("images"));
//                    getComplaintData.setUpdated_at(complaintsArray.getJSONObject(i).getString("updated_by"));
                intentLat = Double.parseDouble(getComplaintData.getLatitude());
                intentLong = Double.parseDouble(getComplaintData.getLongitude());
                System.out.println("THE PARSED JSON ARRAY:"+complaintsArray.getJSONObject(i).getInt("id")+"The lat is"+complaintsArray.getJSONObject(i).getString("longitude")+complaintsArray.getJSONObject(i).getString("latitude"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public void setValues(){

        txtSize.setText(getComplaintData.getSize()==null ?" ":getComplaintData.getSize());
        txtDescription.setText(getComplaintData.getDescrition()==null ?" ":getComplaintData.getDescrition());
        txtSeverity.setText(getComplaintData.getPriority() ==null ?" ":getComplaintData.getPriority());
        txtLocation.setText(getComplaintData.getStreet() ==null ?" ":getComplaintData.getStreet());
        try {
            complaintView.setImageBitmap(new ImageLoadTask().execute(getComplaintData.getImage()).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
        super.onAttach(context);

    }

    private void getCoordinates() {

        if (lat == 0 && lng == 0) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Fetching Current Location...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

//        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();


                if (lat != 0 && lng != 0) {
                    progressDialog.dismiss();

                }

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
                getContext().startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    public static boolean isAtLocation(double incidentLat, double incidentLong, double userLat,  double userLong){

        final int R = 6371;       //Radius of the earth

        Double latDistance = Math.toRadians(userLat - incidentLat);
        Double longDistance = Math.toRadians(userLong - incidentLong);

        Double a = Math.sin(latDistance/2)
                * Math.sin(latDistance/2)
                + Math.cos(Math.toRadians(incidentLat))
                * Math.cos(Math.toRadians(userLat))
                * Math.sin(longDistance / 2)
                * Math.sin(longDistance / 2);

        if(a * 3.28 > 30)
            return false;
        else
            return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
