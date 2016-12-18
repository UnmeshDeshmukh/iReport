package com.example.tanvi.ireport.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanvi.ireport.Model.GetComplaintData;
import com.example.tanvi.ireport.Model.Report_Item;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.GetDataForMapMarker;
import com.example.tanvi.ireport.Utility.ImageLoadTask;
import com.example.tanvi.ireport.Utility.ReportsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisteredUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisteredUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisteredUserFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int imageCount=0;
    ListView myReportsListView;
    private String[] imagesArray = new String[20];
    FloatingActionButton floatingActionButton;
    String email;

    GetComplaintData getComplaintData= new GetComplaintData();
    List<GetComplaintData> complaintReportsArrayList = new ArrayList<GetComplaintData>();
    JSONObject object;
    String user;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegisteredUserFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisteredUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisteredUserFragment newInstance(String param1, String param2) {
        RegisteredUserFragment fragment = new RegisteredUserFragment();
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
//            email = savedInstanceState.getString("Email");
//            System.out.println("In fragment got the email ID !!"+email);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View registeredFragmentView = inflater.inflate(R.layout.fragment_registered_user, container, false);
        user = getArguments().getString("Email");

        object = callToPost();
        parseJSON(object,registeredFragmentView);
        setFragmentView(registeredFragmentView);

        return registeredFragmentView;

    }

    private void setFragmentView(View regView) {
        myReportsListView = (ListView) regView.findViewById(R.id.listViewReports);





        ReportsAdapter adapter = new ReportsAdapter(getContext(), (ArrayList)complaintReportsArrayList);
        myReportsListView.setAdapter(adapter);
        myReportsListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        floatingActionButton = (FloatingActionButton) regView.findViewById(R.id.mapButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new MapViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Email",user);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_content,fragment).commit();

            }
        });

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

    private void parseJSON(JSONObject jsonObject,View registeredFragmentView) {
        JSONArray complaintsArray = null;

        try {
            complaintsArray = jsonObject.getJSONArray("complaints");
            for (int i = 0; i < complaintsArray.length(); i++) {
                GetComplaintData getComplaintData = new GetComplaintData();
                getComplaintData.setId(complaintsArray.getJSONObject(i).getInt("id"));
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



                System.out.println("THE PARSED JSON ARRAY:" + complaintsArray.getJSONObject(i).getInt("id") + "The lat is" + complaintsArray.getJSONObject(i).getString("longitude") + complaintsArray.getJSONObject(i).getString("latitude"));
                complaintReportsArrayList.add(getComplaintData);
                System.out.println("The data coming"+complaintReportsArrayList.get(i).getEmail()+"----------"+complaintReportsArrayList.get(i).getId());
            }

        } catch (JSONException e) {
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment =new ListViewFragment();
        Bundle bundle = new Bundle();

        int complaintId = complaintReportsArrayList.get(i).getId();
        System.out.println(complaintId);
        bundle.putInt("complaintId",complaintId);

        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(this.getId(),fragment).commit();

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
