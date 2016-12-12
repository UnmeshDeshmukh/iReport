package com.example.tanvi.ireport.Utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanvi.ireport.Model.GetComplaintData;
import com.example.tanvi.ireport.R;
import com.example.tanvi.ireport.Model.Report_Item;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by tanvi on 12/2/2016.
 */

public class ReportsAdapter extends BaseAdapter {

    Context context;

    List<GetComplaintData> reportList;

    public ReportsAdapter(Context context, ArrayList<GetComplaintData> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    private class ViewHolder{

        ImageView imageView;
        TextView textName;
        TextView textStatus;
        TextView textDateTime;

    }


    @Override
    public int getCount() {
        return reportList.size();
    }

    @Override
    public Object getItem(int i) {

        return reportList.get(i);

    }

    @Override
    public long getItemId(int i) {

        return reportList.indexOf(getItem(i));
    }

    public View getView(int position, View convertView, ViewGroup parent){


        ViewHolder holder =null;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.list_row,null);

            holder = new ViewHolder();
            holder.textName = (TextView) convertView.findViewById(R.id.reportName);
            holder.textDateTime = (TextView) convertView.findViewById(R.id.dateTime);
            holder.textStatus = (TextView) convertView.findViewById(R.id.status);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

//        Report_Item report = (Report_Item)getItem(position);
//
//        holder.textName.setText(report.getName());
//        holder.textDateTime.setText(report.getDateTime());
//        holder.textStatus.setText(report.getStatus());
//        holder.imageView.setImageResource(report.getImageURL());



        GetComplaintData complaintData = (GetComplaintData) getItem(position);
        holder.textName.setText(String.valueOf(complaintData.getId()));
        holder.textDateTime.setText(complaintData.getCreated_at());
        holder.textStatus.setText(complaintData.getStatus());
//        try {
////            Bitmap imageBitmap = new ImageLoadTask().execute(String.valueOf(complaintData.getImage())).get();
//
//
//  //          holder.imageView.setImageBitmap(imageBitmap);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }




        return convertView;


    }






}
