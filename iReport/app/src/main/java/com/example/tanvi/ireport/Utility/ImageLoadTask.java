package com.example.tanvi.ireport.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.tanvi.ireport.Model.GetComplaintData;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<String, String, Bitmap> {

    private String url;

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);


    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL urlConnection = null;
        try {
            url = strings[0];
            urlConnection  = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            publishProgress("Getting Reports");
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

    }

}