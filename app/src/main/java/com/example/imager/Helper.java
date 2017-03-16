package com.example.imager;

/**
 * Created by user on 2016/12/27.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by user on 2016/12/23.
 */

public class Helper {

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }



    public static void saveImageToGallery(Context context, Bitmap bmp) {
        if (bmp == null){
            Toast.makeText(context, "请先选择图片...", Toast.LENGTH_SHORT).show();
            return;
        }
        // 首先保存图片
        File appDir = new File("/storage/external/DCIM/SAVE/");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "文件未发现.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "保存出错了...", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }catch (Exception e){
            Toast.makeText(context, "保存出错了...", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        // 最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        Toast.makeText(context, "保存成功...", Toast.LENGTH_SHORT).show();
    }
}
