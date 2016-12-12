package com.example.tanvi.ireport.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.example.tanvi.ireport.Model.AddressClass;
import com.example.tanvi.ireport.Model.LitterComplaint;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.LocationSettings;
import com.example.tanvi.ireport.Utility.PostOperations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportNewComplaint.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportNewComplaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportNewComplaint extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    LocationSettings locationSettings;
    Context context = null;
    //ProgressDialog progressDialog;
    LocationManager locationManager;
    LocationListener locationListener;
    Double lat=0.00;
    Double lng =0.00;
    AddressClass addressClass = new AddressClass();
    ProgressDialog progressDialog;
    Button submitButton;
    LitterComplaint litterComplaint = new LitterComplaint();
    Uri file;
    ImageView imageView;
    Button clickPicture,getLocationButton;
    List<Address> addressList;
    private OnFragmentInteractionListener mListener;
    String user,fileURL;
    public ReportNewComplaint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportNewComplaint.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportNewComplaint newInstance(String param1, String param2) {
        ReportNewComplaint fragment = new ReportNewComplaint();
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View reportView = inflater.inflate(R.layout.fragment_report_new_complaint, container, false);

        user = getArguments().getString("Email");
        locationSettings = new LocationSettings(getActivity());
        EditText description = (EditText) reportView.findViewById(R.id.descriptionTextBox);
        litterComplaint.setDescription(description.getText().toString());
        setSpinner(reportView);


        clickPicture = (Button) reportView.findViewById(R.id.clickPicture);
        imageView = (ImageView) reportView.findViewById(R.id.captureImageView);
        imageView.setVisibility(reportView.INVISIBLE);
        getLocationButton = (Button) reportView.findViewById(R.id.getLocationButton);
        //  final ImageClick imgClick = new ImageClick(getContext());


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            clickPicture.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
        }


        clickPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture(view);
            }
        });

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCoordinates();
            }
        });

        submitButton = (Button) reportView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltoPostAction();
            }
        });


        return reportView;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

                addressClass.setLatitude(Double.toString(lat));
                addressClass.setLongitude(Double.toString(lng));
                if (lat != 0 && lng != 0) {
                    progressDialog.dismiss();
                    addressClass = getAddress();
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

    /*
    *Function That Checks the Permission to access the folders required to take the image or upload it
    * Also gets the Image file from Directory
     */
    public void takePicture(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            clickPicture.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        file = Uri.fromFile(getMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT,file);
        startActivityForResult(intent,100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100){
            if(resultCode==RESULT_OK){
                imageView.setVisibility(imageView.VISIBLE);
                imageView.setImageURI(file);
//                Fragment fragment = new HomeFragment();
                Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(getContext()));

                try {
                    Map uploadResult = cloudinary.uploader().upload(file.getPath(), ObjectUtils.asMap("public_id", ""));
                    fileURL = uploadResult.get("url").toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Fragment fragment = new ReportNewComplaint();
            }
        }

    }
    /*
    *Functions returns the media file from the directory
     */

    private File getMediaFile() {
        File imageStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"CameraComplaintPics");
        if(!imageStorage.exists()){
            if(!imageStorage.mkdirs())
                return null;
        }
        String imageTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(imageStorage.getPath() + File.separator+ "Report-"+imageTimeStamp+".jpg");

    }


    /*
    * *Checking the Permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                    clickPicture.setEnabled(true);
                }
                return;
        }
    }


    /**
     ** Function sets spinner and implements on Item Selected Listener
     **/
    private void setSpinner(View homeView) {

        Spinner sizeSpinner = (Spinner) homeView.findViewById(R.id.litterSizeSpinner);

        ArrayAdapter<CharSequence> sizeArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.litterSizeArray, android.R.layout.simple_spinner_item);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeArrayAdapter);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                litterComplaint.setLitterSize(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner severityLevelSpinner = (Spinner) homeView.findViewById(R.id.severityLevelSpinner);
        ArrayAdapter<CharSequence> severityLevelArrayAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.severityLevelArray, android.R.layout.simple_spinner_item);
        severityLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        severityLevelSpinner.setAdapter(severityLevelArrayAdapter);
        severityLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                litterComplaint.setSeverityLevel(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    /**
     ** This Method returns Location
     */

    private void getLocation() {

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
    }

    /**
     * Function that sets all the address values according the current location
     */

    public AddressClass getAddress(){
        AddressClass addressClass = new AddressClass();

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try{
            addressList = geocoder.getFromLocation(lat,lng,2);
            addressClass.setStateName(addressList.get(0).getAdminArea());
            addressClass.setCityName(addressList.get(0).getLocality());
            addressClass.setCountryName(addressList.get(0).getCountryName());
            addressClass.setZipCode(addressList.get(0).getPostalCode());
            addressClass.setStreetAddress(addressList.get(0).getAddressLine(0));
            System.out.println("Address is:"+addressClass.getStreetAddress()+addressClass.getCityName()+addressClass.getStateName()+addressClass.getZipCode());

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return addressClass;

    }

    /**
     * Function Call to the POST Method
     *
     */

    public void calltoPostAction(){




        final JSONObject userDict = new JSONObject();

        try {
            userDict.put("latitude",lat);
            userDict.put("longitude",lng);
            userDict.put("description",litterComplaint.getDescription());
            userDict.put("priority",litterComplaint.getSeverityLevel());
            userDict.put("size",litterComplaint.getLitterSize());
            userDict.put("street",addressClass.getStreetAddress());
            userDict.put("city",addressClass.getCityName());
            userDict.put("state",addressClass.getStateName());
            userDict.put("image",fileURL);
            userDict.put("email",user);

        }catch (JSONException ex){
            ex.printStackTrace();
        }
        new PostOperations("registercomplaint").execute(String.valueOf(userDict));
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