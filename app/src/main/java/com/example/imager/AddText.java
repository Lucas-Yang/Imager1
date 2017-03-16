package com.example.imager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * Created by user on 2016/12/27.
 */

public class AddText extends Activity  implements View.OnClickListener{
    private Button addbtn, savebtn;
    private Bitmap bitmaptt,bitmapnew,smallbitmap;
    private ImageView imaget;
    private  EditText editText;
    private Uri uri1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);
        uri1 = getIntent().getData();
        bitmaptt = getBitmapFromUri(uri1);
        addbtn = (Button) findViewById(R.id.button_1);
        savebtn = (Button) findViewById(R.id.button_2);
        imaget = (ImageView) findViewById(R.id.imageViewt);
        editText=(EditText)findViewById(R.id.edit_text);
        editText.addTextChangedListener(textWatcher);
        addbtn.setOnClickListener(this);
        savebtn.setOnClickListener(this);
        imaget.setImageBitmap(bitmaptt);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1:
                break;
            case R.id.button_2:
                Helper.saveImageToGallery(this,smallbitmap);
                break;

        }
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            bitmapnew = ImageHelper.getShareingBitmap(bitmaptt, editText, 100);
            try {
                if (bitmapnew != null) {
                    smallbitmap = Helper.zoomBitmap(bitmapnew, bitmapnew.getWidth() / 2, bitmapnew.getHeight() / 2);
                    bitmapnew.recycle();
                    imaget.setImageBitmap(smallbitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };


    private Bitmap getBitmapFromUri(Uri uri)//通过URI得到bitmap
     {
            try {
                // 读取uri所在的图片
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                return bitmap;
            } catch (Exception e) {
                Log.e("[Android]", e.getMessage());
                Log.e("[Android]", "目录为：" + uri);
                e.printStackTrace();
                return null;
            }
        }

}
