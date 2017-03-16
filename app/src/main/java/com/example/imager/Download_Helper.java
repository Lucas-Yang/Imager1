package com.example.imager;

/**
 * Created by user on 2016/12/24.
 *
 */
/*
*这个class主要用于从服务器接受文件
 */
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/20.
 */
public class Download_Helper {
    //加载图片
    public static File getdFile(String path, String filepath) throws Exception {
        URL url = new URL(path);//
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//从网络获得链接
        connection.setRequestMethod("GET");//获得
        connection.setConnectTimeout(5000);//设置超时时间为5s
        connection.setRequestProperty("api-version", "1.0");
        if (connection.getResponseCode() == 200)//检测是否正常返回数据请求 详情参照http协议
        {
            InputStream is = connection.getInputStream();//获得输入流
            File file = new File(filepath);//新建一个file文件
            FileOutputStream fos = new FileOutputStream(file);//对应文件建立输出流
            byte[] buffer = new byte[1024];//新建缓存  用来存储 从网络读取数据 再写入文件
            int len = 0;
            while ((len = is.read(buffer)) != -1) {//当没有读到最后的时候
                fos.write(buffer, 0, len);//将缓存中的存储的文件流秀娥问file文件
            }
            fos.flush();//将缓存中的写入file
            fos.close();
            is.close();//将输入流 输出流关闭
            Log.e("========", "++++++file++++" + file);
            return file;
        }else{
            Log.e("======","-----getResponseCode----"+connection.getResponseCode());
            return null;
        }


    }


}
