package com.example.tanvi.ireport.Utility;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tanvi.ireport.Model.GetComplaintData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Unmesh on 12/6/2016.
 */


public class GetDataForMapMarker extends AsyncTask<String,Void,JSONObject> {


    @Override
    protected JSONObject doInBackground(String... strings) {
        ArrayList<GetComplaintData> complaintReportsArrayList = new ArrayList<>();
        String JSONResponse = null;
        JSONObject JSONObject;
        String JSONData = strings[0];
        Log.i("JSONData:",JSONData);
        HttpURLConnection httpURLConnection =null;
        BufferedReader reader = null;
        try{

            URL url = new URL("http://35.164.38.196:3333/getcomplaints");


            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            Writer writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
            writer.write(JSONData);
            writer.close();

            InputStream inputStream = httpURLConnection.getInputStream();

            StringBuffer stringBuffer = new StringBuffer();
            if(inputStream==null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while((inputLine=reader.readLine())!=null){
                stringBuffer.append(inputLine + "\n");
            }
            if(stringBuffer.length()==0){
                return null;
            }
            JSONResponse = stringBuffer.toString();
            JSONObject = new JSONObject(JSONResponse);
            Log.i("Response:",JSONResponse);
            try {

                return JSONObject;
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return null;

        }catch (Exception ex){
            ex.printStackTrace();
        }


        return null;

    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
