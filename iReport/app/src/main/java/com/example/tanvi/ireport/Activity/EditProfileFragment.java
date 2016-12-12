package com.example.tanvi.ireport.Activity;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tanvi.ireport.Model.UserData;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.GetUserDataForProfile;
import com.example.tanvi.ireport.Utility.PostOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText firstNameText,lastNameTextText,phoneNumberText,emailIdText,screenNameText,stateNameText,cityNameText;
    Button makeChanges,submitChanges;
    JSONObject object;
    String user;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        final View editProfileView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        user = getArguments().getString("Email");
        screenNameText = (EditText) editProfileView.findViewById(R.id.screenNameEditText);
        emailIdText = (EditText) editProfileView.findViewById(R.id.emailEditText);
        firstNameText = (EditText) editProfileView.findViewById(R.id.firstNameEditText);
        lastNameTextText = (EditText) editProfileView.findViewById(R.id.lastNameEditText);
        phoneNumberText = (EditText)editProfileView.findViewById(R.id.phoneNoEditText);
        stateNameText = (EditText)editProfileView.findViewById(R.id.stateEditText);
        cityNameText = (EditText)editProfileView.findViewById(R.id.cityNameEditText);
        makeChanges = (Button) editProfileView.findViewById(R.id.makeChangesButton);
        submitChanges = (Button) editProfileView.findViewById(R.id.confirmChangeButton);
        freezeText(editProfileView);
        object = callToGetData();



        makeChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setAllTextViewsEditable(editProfileView);


            }
        });

        submitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltoPostAction();
            }
        });
        return editProfileView;
    }

    private void parseJSON() {
        UserData userData = new UserData();
        JSONArray userDataArray = null;

        try {
            userDataArray = object.getJSONArray("user");
            for(int i=0;i< userDataArray.length();i++) {
                userData.setFirstName(userDataArray.getJSONObject(i).getString("firstname")==null?" ":userDataArray.getJSONObject(i).getString("firstname"));
                userData.setLastName(userDataArray.getJSONObject(i).getString("lastname")==null ?" ":userDataArray.getJSONObject(i).getString("lastname") );
                userData.setCity(userDataArray.getJSONObject(i).getString("city")==null ? " ":userDataArray.getJSONObject(i).getString("city"));
                userData.setState(userDataArray.getJSONObject(i).getString("state")==null ? " ":userDataArray.getJSONObject(i).getString("state"));
                userData.setScreenName(userDataArray.getJSONObject(i).getString("screenname")==null ? " ":userDataArray.getJSONObject(i).getString("screenname"));
                userData.setPhoneNo(userDataArray.getJSONObject(i).getString("phone")==null ? " ":userDataArray.getJSONObject(i).getString("phone"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        emailIdText.setText(userData.getEmail());
        screenNameText.setText(userData.getScreenName());
        firstNameText.setText(userData.getFirstName());
        lastNameTextText.setText(userData.getLastName());
        phoneNumberText.setText(userData.getPhoneNo());
        stateNameText.setText(userData.getState());
        cityNameText.setText(userData.getCity());


    }

    private JSONObject callToGetData() {
        final JSONObject userEmail = new JSONObject();
        try{
            userEmail.put("email",user);
            object = new GetUserDataForProfile().execute(String.valueOf(userEmail)).get();
            parseJSON();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    private void setAllTextViewsEditable(View editProfileView) {
        screenNameText.setFocusable(true);
        screenNameText.setClickable(true);
        emailIdText.setClickable(true);
        emailIdText.setFocusable(true);
        firstNameText.setFocusable(true);
        firstNameText.setClickable(true);
        lastNameTextText.setFocusable(true);
        lastNameTextText.setClickable(true);
        phoneNumberText.setFocusable(true);
        phoneNumberText.setClickable(true);
        stateNameText.setClickable(true);
        stateNameText.setFocusable(true);
        cityNameText.setFocusable(true);
        cityNameText.setClickable(true);
        submitChanges.setVisibility(editProfileView.VISIBLE);
        makeChanges.setVisibility(editProfileView.INVISIBLE);
    }

    private void freezeText(View editProfileView) {


        screenNameText.setClickable(false);
        emailIdText.setClickable(false);


        firstNameText.setClickable(false);

        lastNameTextText.setClickable(false);

        phoneNumberText.setClickable(false);
        stateNameText.setClickable(false);


        cityNameText.setClickable(false);
        submitChanges.setVisibility(editProfileView.INVISIBLE);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void calltoPostAction(){
        UserData userData = new UserData();
        userData.setEmail(emailIdText.getText().toString());
        userData.setScreenName(screenNameText.getText().toString());
        userData.setFirstName(firstNameText.getText().toString());
        userData.setLastName(lastNameTextText.getText().toString());
        userData.setPhoneNo(phoneNumberText.getText().toString());
        userData.setCity(cityNameText.getText().toString());
        final JSONObject userDict = new JSONObject();

        try {
            userDict.put("email",userData.getEmail());
            userDict.put("fname",userData.getFirstName());
            userDict.put("lname",userData.getLastName());
            userDict.put("screenname",userData.getScreenName());
            userDict.put("phone",userData.getPhoneNo());
            userDict.put("state",userData.getState());
            userDict.put("city",userData.getCity());

        }catch (JSONException ex){
            ex.printStackTrace();
        }
        new PostOperations("edituser").execute(String.valueOf(userDict));
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