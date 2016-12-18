package com.example.tanvi.ireport.Activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.tanvi.ireport.Model.GetComplaintData;
import com.example.tanvi.ireport.Model.Report_Item;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.GetOperationComplaintReport;
import com.example.tanvi.ireport.Utility.ReportsAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OfficialHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OfficialHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfficialHome extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static String email;
    ListView myReportsListView;
    ArrayList<Report_Item> reportsList;
    FloatingActionButton floatingActionButton;
    ArrayList<GetComplaintData> getComplaintDataArrayList;
    public OfficialHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OfficialHome.
     */
    // TODO: Rename and change types and number of parameters
    public static OfficialHome newInstance(String param1, String param2) {
        OfficialHome fragment = new OfficialHome();
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

        View officialView = inflater.inflate(R.layout.fragment_official_home, container, false);
        getActivity().getActionBar();

        
        System.out.println("Inside Official Activity");
        getComplaintDataArrayList = callToGet();
        setFragmentView(officialView);




        return officialView;
    }

    private void setFragmentView(View officialView) {

        myReportsListView = (ListView) officialView.findViewById(R.id.listViewReportsOfficial);
        ReportsAdapter adapter = new ReportsAdapter(getContext(), (ArrayList)getComplaintDataArrayList);
        myReportsListView.setAdapter(adapter);
        myReportsListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);


        floatingActionButton = (FloatingActionButton) officialView.findViewById(R.id.mapButtonOfficial);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                FragmentManager fragmentManager = getFragmentManager();
//                Fragment fragment = new MapViewFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("Email",user);
//                fragment.setArguments(bundle);
//                fragmentManager.beginTransaction().replace(R.id.main_content,fragment).commit();
                  Intent intent = new Intent(getActivity(),MapOfficialActivity.class);
                  startActivity(intent);


            }
        });





    }

    public ArrayList<GetComplaintData> callToGet(){
        ArrayList<GetComplaintData>   getComplaintData = null;
        try {
            getComplaintData = new GetOperationComplaintReport().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return getComplaintData;
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
        int complaintId = getComplaintDataArrayList.get(i).getId();
        System.out.println(complaintId);
        bundle.putInt("complaintId",complaintId);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(this.getId(),fragment).commit();

    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        ArrayList<GetComplaintData> newGetComplaintDataArrayList = new ArrayList<>();
//        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);
//        for(int i=0;i<getComplaintDataArrayList.size();i++){
//            if(query.equalsIgnoreCase(getComplaintDataArrayList.get(i).getEmail())){
//
//                System.out.println("Email is same");
//
//            }else if(query.equalsIgnoreCase(getComplaintDataArrayList.get(i).getStatus())){
//
//                System.out.println("Status is same");
//
//            }
//        }
//
//        return true;
//
//    }
//
//    @Override
//    public boolean onQueryTextChange(String s) {
//        return false;
//    }

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


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
//        inflater.inflate(R.menu.menu_search,menu);
//        MenuItem menuItem = menu.findItem(R.id.search);
//
//        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
//        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        MenuItemCompat.setActionView(menuItem, searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        searchView.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View v) {
//
//                                          }
//                                      }
//        );
//    }

        /*getActivity().getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextFocusChangeListener((View.OnFocusChangeListener) this);*/

}

