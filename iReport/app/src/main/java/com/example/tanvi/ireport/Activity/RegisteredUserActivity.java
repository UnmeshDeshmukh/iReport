package com.example.tanvi.ireport.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanvi.ireport.Model.Report_Item;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Utility.ReportsAdapter;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisteredUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String HOMETAB = "home";
    private static final String NOTIFICATIONSTAB = "notifications";
    private static final String SETTINGSTAB = "settings";
    private static final String PROFILETAB = "editprofile";
    private static int navigationItemIndex = 0;
    public  static String CURRENTTAB = HOMETAB;
    private String[] activityNameTitles;
    public boolean loadHomeFragmentOnBackPress = true;
    private Handler mHandler;
    ActionBarDrawerToggle toggle;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    ListView myReportsListView;
    ArrayList<Report_Item> reportsList;
    FloatingActionButton floatingActionButton;

    private static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_resgistered_user);

        //Toast.makeText(getApplicationContext(),"User is : ",Toast.LENGTH_SHORT).show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        loadNavHeader();
        setUpNavigationView();
        if(savedInstanceState==null){
            navigationItemIndex =0;
            CURRENTTAB = HOMETAB;

            Fragment fragment = new HomeFragment();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent intent = getIntent();
        email = intent.getStringExtra("SignedInUser");
        System.out.println("The signed in user is:"+email);
    }



    private void setUpNavigationView() {
    }

    private void loadNavHeader() {
        //TODO ADD USERNAME AND EMAIL ID using the TEXTVIEWS of header

    }
/*
* Function which returns tab which was selected by user
 */
//
//    private void loadFragment(){
//        selectNavMenuItem();
//        setTitleofToolbar();
//        if(getSupportFragmentManager().findFragmentByTag(CURRENTTAB)!=null){
//            toggle.syncState();
//            return;
//        }
//
//        Runnable pendingTasks = new Runnable() {
//            @Override
//            public void run() {
//                Fragment fragment = getHomeFragment();
//            }
//        };
//
//    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resgistered_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                fragment = new RegisteredUserFragment();
                break;
            case R.id.nav_profile:
                fragment = new EditProfileFragment();
                break;
            case R.id.nav_notifications:
                fragment = new NotificationsFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.nav_reportComplaint:
                fragment = new ReportNewComplaint();
                break;

            case R.id.nav_logout: FirebaseAuth.getInstance().signOut();
                                  LoginManager.getInstance().logOut();
                                    Intent intent = new Intent(RegisteredUserActivity.this,LoginActivity.class);
                                    startActivity(intent);
                  break;
        }
        if (fragment!=null){
            System.out.println("In fragment the email id is "+email);
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString("Email",email);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.frame,fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
