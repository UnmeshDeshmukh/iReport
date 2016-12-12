package com.example.tanvi.ireport.Utility;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Unmesh on 12/3/2016.
 */

public class PostOperations extends AsyncTask<String,String,String> {


    String task;
    public PostOperations(String task){
        this.task =task;
    }

    @Override
    protected String doInBackground(String... params) {
        String JSONResponse = null;
        String JSONData = params[0];
        Log.i("JSONData:",JSONData);
        HttpURLConnection httpURLConnection =null;
        BufferedReader reader = null;
        try{
            URL url=null;
            if(task.equals("edituser")){
                url = new URL("http://35.164.38.196:3333/edituser");
            }else if(task.equals("registercomplaint")){
                url = new URL("http://35.164.38.196:3333/registercomplaint");
            }else if(task.equals("changenotifications")){
                url = new URL("http://35.164.38.196:3333/changenotifications");
            }else if(task.equals("user")){
                url = new URL("http://35.164.38.196:3333/user");
            }

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
            Log.i("Response:",JSONResponse);
            try {
                return JSONResponse;
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
