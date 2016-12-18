package com.example.tanvi.ireport.Utility;

import android.os.AsyncTask;

import com.example.tanvi.ireport.Model.GetComplaintData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Unmesh on 12/4/2016.
 */

public class GetOperationComplaintReport extends AsyncTask<String,Void,ArrayList<GetComplaintData>> {
    @Override
    protected ArrayList<GetComplaintData> doInBackground(String... params) {
        ArrayList<GetComplaintData> complaintReportsArrayList = new ArrayList<>();


        try{

            URL url = new URL("http://35.164.38.196:3332/getcomplaints");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode== HttpURLConnection.HTTP_OK){
                BufferedReader buff = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = buff.readLine();
                while((line!=null)){
                    stringBuilder.append(line);
                    line = buff.readLine();
                }
                String json =stringBuilder.toString();
                JSONObject jsonObject = new JSONObject(json);

                JSONArray complaintsArray = jsonObject.getJSONArray("complaints");
                for(int i=0;i< complaintsArray.length();i++){
                    GetComplaintData getComplaintData = new GetComplaintData();
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
//                    getComplaintData.setUpdated_at(complaintsArray.getJSONObject(i).getString("updated_by"));
                    getComplaintData.setImage(complaintsArray.getJSONObject(i).getString("images"));

                    System.out.println("THE PARSED JSON ARRAY:" + complaintsArray.getJSONObject(i).getInt("id") +"Image is : " + complaintsArray.getJSONObject(i).getString("images")+" The lat is" + complaintsArray.getJSONObject(i).getString("longitude") + complaintsArray.getJSONObject(i).getString("latitude"));
                    complaintReportsArrayList.add(getComplaintData);
                }
                return complaintReportsArrayList;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }






        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<GetComplaintData> getComplaintDatas) {

    }
}
