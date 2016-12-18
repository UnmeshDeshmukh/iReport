package com.example.tanvi.ireport.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tanvi.ireport.Model.GetComplaintData;
import com.example.tanvi.ireport.Model.Report_Item;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.GetOperationComplaintReport;
import com.example.tanvi.ireport.Utility.ReportsAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.tanvi.ireport.R.id.main_content;

public class OfficialActivity extends FragmentActivity implements AdapterView.OnItemClickListener {

    private static String email;
    ListView myReportsListView;
    ArrayList<Report_Item> reportsList;
    FloatingActionButton floatingActionButton;
    ArrayList<GetComplaintData> getComplaintDataArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);


        Intent intent = getIntent();


        System.out.println("Inside Official Activity");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OfficialHome officialHome = new OfficialHome();
        fragmentTransaction.add(R.id.activity_official,officialHome);
        fragmentTransaction.commit();



//        getComplaintDataArrayList = callToGet();
//        setFragmentView();




        //GET CALL CODE TO BE INSERTED IN HOME FRAGMENT
//        Button getData = (Button) notifView.findViewById(R.id.GetData);
//        getData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    getComplaintDataArrayList = new GetOperationComplaintReport().execute().get();
//                    System.out.println("ArrayList"+getComplaintDataArrayList);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

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

    private void setFragmentView() {

        myReportsListView = (ListView) findViewById(R.id.listViewReportsOfficial);
        ReportsAdapter adapter = new ReportsAdapter(getApplicationContext(), (ArrayList)getComplaintDataArrayList);
        myReportsListView.setAdapter(adapter);
        myReportsListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);


        floatingActionButton = (FloatingActionButton) findViewById(R.id.mapButtonOfficial);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                FragmentManager fragmentManager = getFragmentManager();
//                Fragment fragment = new MapViewFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("Email",user);
//                fragment.setArguments(bundle);
//                fragmentManager.beginTransaction().replace(R.id.main_content,fragment).commit();

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//        android.app.FragmentManager fragmentManager = getFragmentManager();
//        Fragment fragment =new ListViewFragment();
//        Bundle bundle = new Bundle();
//        int complaintId = getComplaintDataArrayList.get(i).getId();
//        System.out.println(complaintId);
//        bundle.putInt("complaintId",complaintId);
//        fragment.setArguments(bundle);
        //fragmentManager.beginTransaction().replace(this.get,fragment).commit();


        //fragmentManager.beginTransaction().replace(R.id.main_content_official,fragment).commit();
        //fragmentManager.beginTransaction().addToBackStack(null);

    }




}
