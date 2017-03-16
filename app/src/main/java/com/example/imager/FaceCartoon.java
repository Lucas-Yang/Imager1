package com.example.imager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;



/**
 * Created by user on 2016/12/27.
 */

public class FaceCartoon extends Activity implements View.OnClickListener {
    private Button addbtn, savebtn;
    private Bitmap bitmapf,newBitmap;
    private ImageView imagef;
    private static String URL = "http://172.31.8.6:8080/fileUpload/file_upload";  //服务器地址

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_cartoon);
        Uri uri = getIntent().getData();
        bitmapf = getBitmapFromUri(uri);

        addbtn = (Button) findViewById(R.id.button_a);
        savebtn = (Button) findViewById(R.id.button_b);
        imagef = (ImageView) findViewById(R.id.imageViewf);
        addbtn.setOnClickListener(this);
        savebtn.setOnClickListener(this);
        imagef.setImageBitmap(bitmapf);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_a:
                //图片上传服务器并且下载服务器传回的图片
                File sendFile = new File("/storage/external/DCIM/");   //传送给服务器的图片文件
                File appDir = new File("/storage/external/DCIM/Btm_File/");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                String path = "/storage/external/DCIM/Btm_File/";
                try {
                    sendFile = Btm_FileHelper.saveFile(bitmapf, path, fileName);
                }catch (IOException e) {
                    e.printStackTrace();
                }

                Upload_Helper.uploadFile(sendFile,URL);
                //下面程序用于接收从服务器传来的数据
                String getFileName = System.currentTimeMillis() + ".jpg";
                String getPath = "/storage/external/DCIM/Get_File/";
                String filepath = getPath + getFileName;
                File getFile = new File("/storage/external/DCIM/Get_File/");
                try {
                    getFile = Download_Helper.getdFile(URL, filepath);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                //以下程序用于将服务器端传来的File转换成Bitmap，并且显示出来
                //Bitmap newBitmap;
                newBitmap = Btm_FileHelper.btmFile(filepath);
                imagef.setImageBitmap(newBitmap);
                break;
            case R.id.button_b:
                Helper.saveImageToGallery(this,bitmapf);
                break;

        }
    }
    private Bitmap getBitmapFromUri(Uri uri)//通过URI得到bitmap
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }
}

