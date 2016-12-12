package com.example.tanvi.ireport.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tanvi.ireport.Activity.HomeFragment;
import com.example.tanvi.ireport.R;
//import com.example.cmpe277project.litterreport.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Unmesh on 12/2/2016.
 */

public class ImageClick extends Activity {

    Context context;
    ImageView imageView;
    Button clickPicture;
    Uri file;
    public ImageClick(Context context){
        this.context = context;

    }


    public void takePicture(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            clickPicture = (Button) findViewById(R.id.clickPicture);
//            clickPicture.setEnabled(false);
//            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
//        }

        file = Uri.fromFile(getMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT,file);
        HomeFragment homeFragment= new HomeFragment();
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode==RESULT_OK){
                imageView = (ImageView) findViewById(R.id.captureImageView);
                imageView.setVisibility(imageView.VISIBLE);
                imageView.setImageURI(file);
                Fragment fragment = new HomeFragment();
            }
        }

    }

    private File getMediaFile() {
        File imageStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"CameraComplaintPics");
        if(!imageStorage.exists()){
           if(!imageStorage.mkdirs())
                return null;
        }
        String imageTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(imageStorage.getPath() + File.separator+ "Report-"+imageTimeStamp+".jpg");

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                clickPicture = (Button) findViewById(R.id.clickPicture);
//                clickPicture.setEnabled(true);
//            }
//        }
//    }
}
