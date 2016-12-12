package com.example.tanvi.ireport.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.PostOperations;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Switch emailuponStatusChange,reportAnonymously,litterEmailUpdates;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    JSONObject userDict = new JSONObject();
    private OnFragmentInteractionListener mListener;
    Boolean litterEmailUpdatesBoolean,emailUponStatusChangeBoolean,reportAnonymouslyBoolean;
    Button submitButton;
    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);
        String user = getArguments().getString("Email");
        submitButton = (Button) settingsView.findViewById(R.id.changeValues);

        userDict = toggleSwtichMethod(settingsView);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostOperations("changenotifications").execute(String.valueOf(userDict));
            }
        });

        return settingsView;


    }

    private JSONObject toggleSwtichMethod(View settingsView) {
        emailuponStatusChange = (Switch) settingsView.findViewById(R.id.emailOnStatusChangeSwitch);
        litterEmailUpdates = (Switch) settingsView.findViewById(R.id.litterReportAnySwitch);
        reportAnonymously = (Switch) settingsView.findViewById(R.id.anonymousSwitch);

        emailuponStatusChange.setChecked(true);
        emailUponStatusChangeBoolean = true;
        litterEmailUpdates.setChecked(true);
        litterEmailUpdatesBoolean = true;
        reportAnonymously.setChecked(false);
        reportAnonymouslyBoolean = Boolean.FALSE;

        emailuponStatusChange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    emailUponStatusChangeBoolean = true;


                    JSONObject jsonObject= new JSONObject();
                    try{
                        userDict.put("notification",litterEmailUpdatesBoolean);
                        userDict.put("emailnotification",emailUponStatusChangeBoolean);
                        userDict.put("anonymous",reportAnonymouslyBoolean);
                    }catch (Exception ex){
                    }

                }else {
                    emailUponStatusChangeBoolean = false;


                    JSONObject jsonObject= new JSONObject();
                    try{
                        userDict.put("notification",litterEmailUpdatesBoolean);
                        userDict.put("emailnotification",emailUponStatusChangeBoolean);
                        userDict.put("anonymous",reportAnonymouslyBoolean);
                    }catch (Exception ex){

                    }

                }
            }
        });

        litterEmailUpdates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //TODO POST CALL

                    litterEmailUpdatesBoolean = Boolean.TRUE;

                    try{
                        userDict.put("notification",litterEmailUpdatesBoolean);
                        userDict.put("emailnotification",emailUponStatusChangeBoolean);
                        userDict.put("anonymous",reportAnonymouslyBoolean);

                    }catch (Exception ex){

                    }

                }else {

                    litterEmailUpdatesBoolean = Boolean.FALSE;


                    try{
                        userDict.put("notification",litterEmailUpdatesBoolean);
                        userDict.put("emailnotification",emailUponStatusChangeBoolean);
                        userDict.put("anonymous",reportAnonymouslyBoolean);

                    }catch (Exception ex){

                    }

                }
            }
        });

        reportAnonymously.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    emailUponStatusChangeBoolean = Boolean.FALSE;
                    litterEmailUpdatesBoolean = Boolean.FALSE;
                    reportAnonymouslyBoolean = Boolean.TRUE;
                    emailuponStatusChange.setChecked(false);
                    litterEmailUpdates.setChecked(false);
                    final JSONObject userDict = new JSONObject();
                    JSONObject jsonObject= new JSONObject();

                    try{
                        userDict.put("notification",litterEmailUpdatesBoolean);
                        userDict.put("emailnotification",emailUponStatusChangeBoolean);
                        userDict.put("anonymous",reportAnonymouslyBoolean);

                    }catch (Exception ex){

                    }


                }else{

                    emailUponStatusChangeBoolean = Boolean.TRUE;
                    litterEmailUpdatesBoolean = Boolean.TRUE;
                    reportAnonymouslyBoolean = Boolean.FALSE;
                    emailuponStatusChange.setChecked(true);
                    litterEmailUpdates.setChecked(true);


                    try{
                        userDict.put("notification",litterEmailUpdatesBoolean);
                        userDict.put("emailnotification",emailUponStatusChangeBoolean);
                        userDict.put("anonymous",reportAnonymouslyBoolean);

                    }catch (Exception ex){

                    }

                }

            }
        });
        try {
            userDict.put("email","aniketd.khaire@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userDict;
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