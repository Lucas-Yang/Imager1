package com.example.imager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;




public class MainActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private Button selectImageBtn, addText, faceCartoon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectImageBtn = (Button) findViewById(R.id.button1);
        addText = (Button) findViewById(R.id.button2);
        faceCartoon = (Button) findViewById(R.id.button3);
        selectImageBtn.setOnClickListener(this);
        addText.setOnClickListener(this);
        faceCartoon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(Intent.ACTION_PICK);
                intent2.setType("image/*");
                startActivityForResult(intent2, 2);
                break;
            case R.id.button3:
                Intent intent3 = new Intent(Intent.ACTION_PICK);
                intent3.setType("image/*");
                startActivityForResult(intent3, 3);
                break;

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == 1) {
              Uri originalUri1 = intent.getData();
               intent = new Intent(MainActivity.this, PrimaryColor.class);
               intent.setData(originalUri1);
               startActivity(intent);
            }
        if (resultCode == RESULT_OK && requestCode == 2) {
            Uri originalUri2 = intent.getData();
            intent = new Intent(MainActivity.this, AddText.class);
            intent.setData(originalUri2);
            startActivity(intent);
        }
        if (resultCode == RESULT_OK && requestCode == 3) {
            Uri originalUri3 = intent.getData();
            intent = new Intent(MainActivity.this, FaceCartoon.class);
            intent.setData(originalUri3);
            startActivity(intent);
        }
        }

}