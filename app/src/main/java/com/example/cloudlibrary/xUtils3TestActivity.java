package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class xUtils3TestActivity extends AppCompatActivity implements View.OnClickListener{
    private Button test_download;
    private ProgressBar progressbar;
    private Button test_upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_utils3_test);
        test_download=(Button)findViewById(R.id.test_download);
        progressbar=(ProgressBar)findViewById(R.id.test_progressbar);
        test_upload=(Button)findViewById(R.id.test_upload);
        test_upload.setOnClickListener(this);
        test_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_download:
                downloadFile();
                break;
            case R.id.test_upload:
                uploadFile();
                break;
        }

    }
    public void uploadFile(){
        RequestParams params = new RequestParams("http://10.92.5.115:8080/BuyIII/BusinessManServlet");
        //params.setMultipart(true);
        params.addBodyParameter("itemid","1234");
        params.addBodyParameter("itemname","写入数据库测试");
        params.addBodyParameter("itemprice","1000");
        params.addBodyParameter("itemnum","1000");
        //params.addBodyParameter("File",new File(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg"),null,"YLY.jpg");
        x.http().post(params, new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Toast.makeText(xUtils3TestActivity.this,"上传完成",Toast.LENGTH_LONG).show();
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
    public void downloadFile(){
        RequestParams params = new RequestParams("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Fca3c577fca3ed067015ba144d11c4bcdd61348c4.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621306046&t=66837f7202d0d974bd0be653b2754dea");
        params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg");
        params.setCancelFast(true);
        params.setAutoRename(true);
        params.setAutoResume(true);
        params.setExecutor(new PriorityExecutor(3,true));
        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {
                Log.e("TAG","onWaiting");
            }

            @Override
            public void onStarted() {
                Log.e("TAG","onStarted");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressbar.setMax((int)total);
                progressbar.setProgress((int)current);
                Log.e("TAG","onLoading=="+current+"/"+total+",isDownloading=="+isDownloading);
            }

            @Override
            public void onSuccess(File result) {
                Log.e("TAG","onSuccess=="+result.toString());
                Toast.makeText(xUtils3TestActivity.this,"下载完成",Toast.LENGTH_LONG).show();
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