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
import android.widget.SeekBar;
import android.widget.Toast;


/**
 * Created by user on 2016/11/29.
 */

public class PrimaryColor extends Activity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener{
    private ImageView mImageView;
    private SeekBar mSeekBarhue, mSeekBarsatur, mSeekBarlum;
    private Button buttonf;
    private static int Max_value = 255;
    private static int Mid_value = 127;
    private  float mhue, msatur, mlum;
    private Bitmap bitmap,bitmapnew;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        uri = getIntent().getData();
        bitmap = getBitmapFromUri(uri);
        mImageView = (ImageView)findViewById(R.id.imageview);
        mSeekBarhue = (SeekBar)findViewById(R.id.hueBar);
        mSeekBarsatur = (SeekBar)findViewById(R.id.saturBar);
        mSeekBarlum = (SeekBar)findViewById(R.id.lumBar);
        buttonf = (Button)findViewById(R.id.buttonf) ;
        buttonf.setOnClickListener(this);
        mSeekBarhue.setOnSeekBarChangeListener(this);
        mSeekBarlum.setOnSeekBarChangeListener(this);
        mSeekBarsatur.setOnSeekBarChangeListener(this);

        mSeekBarhue.setMax(Max_value);
        mSeekBarlum.setMax(Max_value);
        mSeekBarsatur.setMax(Max_value);
        mSeekBarhue.setProgress(Mid_value);
        mSeekBarsatur.setProgress(Mid_value);
        mSeekBarlum.setProgress(Mid_value);
        mImageView.setImageBitmap(bitmap);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.hueBar:
                mhue = (progress - Mid_value)*1.0F/Mid_value*180;
                break;
            case R.id.saturBar:
                msatur = progress*1.0F/Mid_value;
                break;
            case R.id.lumBar:
                mlum = progress*1.0F/Mid_value;
                break;

        }

        bitmapnew = ImageHelper.handleImageEffect(bitmap, mhue, msatur,mlum);
        mImageView.setImageBitmap(bitmapnew);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonf:
                //Toast.makeText(this, "hhh.", Toast.LENGTH_SHORT).show();
                Helper.saveImageToGallery(this,bitmapnew);
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
