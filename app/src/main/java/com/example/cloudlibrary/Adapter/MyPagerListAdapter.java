package com.example.cloudlibrary.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cloudlibrary.Data.ListData;
import com.example.cloudlibrary.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyPagerListAdapter extends BaseAdapter {
    private List<ListData> list_data=new ArrayList<>();
    private Context context;
    public MyPagerListAdapter(Context context, List<ListData> list_data){
        this.context=context;
        this.list_data=list_data;
    }
    @Override
    public int getCount() {
        return list_data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.mypager_like_list,null);
        }
        ImageView picture_list=(ImageView)convertView.findViewById(R.id.picture_list);
        TextView name_list=(TextView)convertView.findViewById(R.id.name_list);
        TextView score_list=(TextView)convertView.findViewById(R.id.score_list);
        ListData listData=list_data.get(position);
        name_list.setText(listData.getTitle());
        score_list.setText(listData.getScore());
        String url=listData.getImg();
        Bitmap bitmap = getHttpBitmap(url);
        picture_list.setImageBitmap(bitmap);
        return convertView;
    }
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            Log.e("TAG",conn.toString());
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //设置请求方式
            conn.setRequestMethod("GET");
            //不使用缓存
            conn.setUseCaches(false);
            //响应的状态码
            int code = conn.getResponseCode();
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
