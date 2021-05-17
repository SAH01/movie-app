package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.Adapter.CloudPagerAdapter;
import com.example.cloudlibrary.Data.AllData;
import com.example.cloudlibrary.Data.ListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.db.table.TableEntity;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviePagerActivity extends AppCompatActivity implements View.OnClickListener{
    TextView movie_title;
    TextView movie_star;
    TextView movie_director;
    TextView movie_type;
    TextView movie_area;
    TextView movie_date;
    TextView movie_summary;
    TextView movie_score;
    TextView movie_language;
    ImageView movie_img;
    TextView movie_scorenum;
    TextView movie_timelen;
    TextView Ten_score;
    TextView Ten_vip;
    TextView Ai_score;
    TextView Ai_vip;
    Button movie_want;
    Button movie_on;
    Button movie_have;

    ListData listData;

    private AllData allData=new AllData();
    private String userphone;
    private String username;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pager);
        movie_title=(TextView)findViewById(R.id.movie_title);
        movie_star=(TextView)findViewById(R.id.movie_star);
        movie_director=(TextView)findViewById(R.id.movie_director);
        movie_type=(TextView)findViewById(R.id.movie_type);
        movie_area=(TextView)findViewById(R.id.movie_area);
        movie_date=(TextView)findViewById(R.id.movie_date);
        movie_summary=(TextView)findViewById(R.id.movie_summary);
        movie_score=(TextView)findViewById(R.id.movie_score);
        movie_language=(TextView)findViewById(R.id.movie_language);
        movie_img=(ImageView)findViewById(R.id.movie_img);
        movie_scorenum=(TextView)findViewById(R.id.movie_scorenum);
        movie_timelen=(TextView)findViewById(R.id.movie_timelen);
        Ten_score=(TextView)findViewById(R.id.Ten_score);
        Ten_vip=(TextView)findViewById(R.id.Ten_vip);
        Ai_score=(TextView)findViewById(R.id.Ai_score);
        Ai_vip=(TextView)findViewById(R.id.Ai_vip);
        movie_want=(Button)findViewById(R.id.movie_want);
        movie_on=(Button)findViewById(R.id.movie_on);
        movie_have=(Button)findViewById(R.id.movie_have);
        movie_want.setOnClickListener(this);
        movie_on.setOnClickListener(this);
        movie_have.setOnClickListener(this);
        Intent intent=getIntent();
        String movie_title_str=intent.getStringExtra("title");
        String movie_scorenum_str=intent.getStringExtra("scorenum");
        set_movie(movie_title_str,movie_scorenum_str);
        SharedPreferences sp=getSharedPreferences("userdata",MODE_PRIVATE);
        userphone=(sp.getString("user_phone",""));
        username=(sp.getString("user_name",""));
    }
    public void set_movie(String title,String scorenum){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_query");
        //params.setMultipart(true);
        params.addBodyParameter("title",title);
        params.addBodyParameter("scorenum",scorenum);
        //params.addBodyParameter("File",new File(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg"),null,"YLY.jpg");
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    JSONArray array=object.optJSONArray("data");
                    movie_title.setText(array.getString(0));
                    movie_star.setText("主演："+array.getString(1));
                    movie_director.setText("导演："+array.getString(2));
                    movie_type.setText("类型："+array.getString(3));
                    movie_area.setText("地区："+array.getString(4));
                    movie_date.setText("上映时间："+array.getString(5));
                    movie_summary.setText("简介："+array.getString(6));
                    movie_score.setText(array.getString(7)+"分");
                    movie_language.setText("语言："+array.getString(8));
                    movie_scorenum.setText("评价人数："+array.getString(10));
                    movie_timelen.setText("时长："+array.getString(11));
                    if(!array.getString(12).equals("0")){
                        Ten_score.setText(array.getString(12)+"分");
                        Ten_vip.setText(array.getString(13));
                        Log.e("TAG",array.getString(12));
                    }else{
                        Ten_score.setText("无此电影");
                        Ten_vip.setText("");
                    }
                    if(!array.getString(15).equals("0")){
                        Ai_score.setText(array.getString(15)+"分");
                        Ai_vip.setText(array.getString(16 ));
                    }else{
                        Ai_score.setText("无此电影");
                        Ai_vip.setText("");
                    }
                    url=array.getString(9);
                    Bitmap bitmap = getHttpBitmap(url);
                    movie_img.setImageBitmap(bitmap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","onError=="+ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG","onCancelled=="+cex.toString());
            }

            @Override
            public void onFinished() {
                Log.e("TAG","onFinished");
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_want:
                http_like(userphone,movie_title.getText().toString(),"想看",movie_scorenum.getText().toString().substring(5)
                        ,url,movie_score.getText().toString().substring(0,3));
                break;
            case R.id.movie_on:
                http_like(userphone,movie_title.getText().toString(),"在看",movie_scorenum.getText().toString().substring(5)
                        ,url,movie_score.getText().toString().substring(0,3));
                break;
            case R.id.movie_have:
                http_like(userphone,movie_title.getText().toString(),"看过",movie_scorenum.getText().toString().substring(5)
                        ,url,movie_score.getText().toString().substring(0,3));
                break;
        }
    }
    public void http_like(String userphone,String usermovie,String usertype,String scorenum,String img,String score){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_like");
        //params.setMultipart(true);
        params.addBodyParameter("userphone",userphone);
        params.addBodyParameter("usermovie",usermovie);
        params.addBodyParameter("usertype",usertype);
        params.addBodyParameter("scorenum",scorenum);
        params.addBodyParameter("url",url);
        params.addBodyParameter("score",score);
        //params.addBodyParameter("File",new File(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg"),null,"YLY.jpg");
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object= null;
                    object = new JSONObject(result);
                    int Flag=object.optInt("data");
                    if(Flag==1){
                        Toast.makeText(MoviePagerActivity.this,"收藏成功："+usertype,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MoviePagerActivity.this,"收藏失败",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","onError=="+ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG","onCancelled=="+cex.toString());
            }

            @Override
            public void onFinished() {
                Log.e("TAG","onFinished");
            }
        });
    }
}