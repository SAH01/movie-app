package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.Data.AllData;
import com.example.cloudlibrary.Data.ListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieLikeActivity extends AppCompatActivity implements View.OnClickListener{
    TextView movie_title_like;
    TextView movie_star_like;
    TextView movie_director_like;
    TextView movie_type_like;
    TextView movie_area_like;
    TextView movie_date_like;
    TextView movie_summary_like;
    TextView movie_score_like;
    TextView movie_language_like;
    ImageView movie_img_like;
    TextView movie_scorenum_like;
    TextView movie_timelen_like;
    TextView Ten_score_like;
    TextView Ten_vip_like;
    TextView Ai_score_like;
    TextView Ai_vip_like;
    Button movie_want_like;
    Button movie_on_like;
    Button movie_have_like;
    Button movie_remove;

    ListData listData;

    private AllData allData=new AllData();
    private String userphone;
    private String username;
    private String url;
    private String usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_like);
        movie_title_like=(TextView)findViewById(R.id.movie_title_like);
        movie_star_like=(TextView)findViewById(R.id.movie_star_like);
        movie_director_like=(TextView)findViewById(R.id.movie_director_like);
        movie_type_like=(TextView)findViewById(R.id.movie_type_like);
        movie_area_like=(TextView)findViewById(R.id.movie_area_like);
        movie_date_like=(TextView)findViewById(R.id.movie_date_like);
        movie_summary_like=(TextView)findViewById(R.id.movie_summary_like);
        movie_score_like=(TextView)findViewById(R.id.movie_score_like);
        movie_language_like=(TextView)findViewById(R.id.movie_language_like);
        movie_img_like=(ImageView)findViewById(R.id.movie_img_like);
        movie_scorenum_like=(TextView)findViewById(R.id.movie_scorenum_like);
        movie_timelen_like=(TextView)findViewById(R.id.movie_timelen_like);
        Ten_score_like=(TextView)findViewById(R.id.Ten_score_like);
        Ten_vip_like=(TextView)findViewById(R.id.Ten_vip_like);
        Ai_score_like=(TextView)findViewById(R.id.Ai_score_like);
        Ai_vip_like=(TextView)findViewById(R.id.Ai_vip_like);
        movie_want_like=(Button)findViewById(R.id.movie_want_like);
        movie_on_like=(Button)findViewById(R.id.movie_on_like);
        movie_have_like=(Button)findViewById(R.id.movie_have_like);
        movie_remove=(Button)findViewById(R.id.movie_remove);
        movie_remove.setOnClickListener(this);
        movie_want_like.setOnClickListener(this);
        movie_on_like.setOnClickListener(this);
        movie_have_like.setOnClickListener(this);
        Intent intent=getIntent();
        String movie_title_str=intent.getStringExtra("title");
        String movie_scorenum_str=intent.getStringExtra("scorenum");
        usertype=intent.getStringExtra("usertype");
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
                    movie_title_like.setText(array.getString(0));
                    movie_star_like.setText("主演："+array.getString(1));
                    movie_director_like.setText("导演："+array.getString(2));
                    movie_type_like.setText("类型："+array.getString(3));
                    movie_area_like.setText("地区："+array.getString(4));
                    movie_date_like.setText("上映时间："+array.getString(5));
                    movie_summary_like.setText("简介："+array.getString(6));
                    movie_score_like.setText(array.getString(7)+"分");
                    movie_language_like.setText("语言："+array.getString(8));
                    movie_scorenum_like.setText("评价人数："+array.getString(10));
                    movie_timelen_like.setText("时长："+array.getString(11));
                    if(!array.getString(12).equals("0")){
                        Ten_score_like.setText(array.getString(12)+"分");
                        Ten_vip_like.setText(array.getString(13));
                        Log.e("TAG",array.getString(12));
                    }else{
                        Ten_score_like.setText("无此电影");
                        Ten_vip_like.setText("");
                    }
                    if(!array.getString(15).equals("0")){
                        Ai_score_like.setText(array.getString(15)+"分");
                        Ai_vip_like.setText(array.getString(16 ));
                    }else{
                        Ai_score_like.setText("无此电影");
                        Ai_vip_like.setText("");
                    }
                    url=array.getString(9);
                    Bitmap bitmap = getHttpBitmap(url);
                    movie_img_like.setImageBitmap(bitmap);
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
            case R.id.movie_want_like:
                http_like_trans(userphone,movie_title_like.getText().toString(),usertype,movie_scorenum_like.getText().toString().substring(5),"想看");
                break;
            case R.id.movie_on_like:
                http_like_trans(userphone,movie_title_like.getText().toString(),usertype,movie_scorenum_like.getText().toString().substring(5),"在看");
                break;
            case R.id.movie_have_like:
                http_like_trans(userphone,movie_title_like.getText().toString(),usertype,movie_scorenum_like.getText().toString().substring(5),"看过");
                break;
            case R.id.movie_remove:
                MovieRemove(userphone,usertype,movie_title_like.getText().toString(),movie_scorenum_like.getText().toString().substring(5));
                break;
        }
    }

    public void http_like_trans(String userphone,String usermovie,String usertype,String scorenum,String usertype_new){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_user_like_trans");
        //params.setMultipart(true);
        params.addBodyParameter("userphone",userphone);
        params.addBodyParameter("usermovie",usermovie);
        params.addBodyParameter("usertype",usertype);
        params.addBodyParameter("scorenum",scorenum);
        params.addBodyParameter("usertype_new",usertype_new);
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
                        Toast.makeText(MovieLikeActivity.this,"转移成功:"+usertype_new,Toast.LENGTH_LONG).show();
                    }
                    if(Flag==0){
                        Toast.makeText(MovieLikeActivity.this,"转移失败:"+usertype_new,Toast.LENGTH_LONG).show();
                    }
                    if(Flag==-1){
                        Toast.makeText(MovieLikeActivity.this,"已存在:"+usertype,Toast.LENGTH_LONG).show();
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
    public void MovieRemove(String userphone,String usertype,String usermovie,String scorenum){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_delete");
        //params.setMultipart(true);
        params.addBodyParameter("userphone",userphone);
        params.addBodyParameter("usermovie",usermovie);
        params.addBodyParameter("usertype",usertype);
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
                    JSONObject object= null;
                    object = new JSONObject(result);
                    int Flag=object.optInt("data");
                    if(Flag==1){
                        Toast.makeText(MovieLikeActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                    }
                    if(Flag==0){
                        Toast.makeText(MovieLikeActivity.this,"删除失败",Toast.LENGTH_LONG).show();
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