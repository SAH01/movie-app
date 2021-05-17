package com.example.cloudlibrary.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.Data.ListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class xUtils3TestActivity {//extends AppCompatActivity implements View.OnClickListener{
//    private Button test_download;
//    private ProgressBar progressbar;
//    private Button test_upload;
//    private TextView test_text;
    private String type;
    private String date;
    private String area;
    private String first;
    private String num;
    private ListData listData;
    private List<ListData> list_data;

    public xUtils3TestActivity() {
    }

    public List<ListData> getList_data() {
        return list_data;
    }

    public void setList_data(List<ListData> list_data) {
        this.list_data = list_data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_x_utils3_test);
//        test_download=(Button)findViewById(R.id.test_download);
//        progressbar=(ProgressBar)findViewById(R.id.test_progressbar);
//        test_upload=(Button)findViewById(R.id.test_upload);
//        test_text=(TextView)findViewById(R.id.test_text);
//        test_upload.setOnClickListener(this);
//        test_download.setOnClickListener(this);
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.test_download:
//                downloadFile();
//                break;
//            case R.id.test_upload:
//                uploadFile();
//                break;
//        }
//
//    }
    public void return_data(){
        RequestParams params = new RequestParams("http://10.98.16.79:5000/query_tag");
        //params.setMultipart(true);
        params.addBodyParameter("type",type);
        params.addBodyParameter("date",date);
        params.addBodyParameter("area",area);
        params.addBodyParameter("first",first);
        params.addBodyParameter("num",num);
        //params.addBodyParameter("File",new File(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg"),null,"YLY.jpg");
        x.http().get(params, new Callback.CacheCallback<String>() {
            List<ListData> list_data_flag=new ArrayList<>();
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    JSONArray array=object.optJSONArray("data");
                    for(int i=0;i<array.length();i++){
//                        map=new HashMap<>();
                        String title=array.getString(i);
                        String[] Str_s;
                        Str_s=title.split(",");
                        String str="";
                        for(int k=0;k<Str_s.length;k++){
                            if(k==0){
                                Str_s[k]=Str_s[k].substring(2,Str_s[k].length()-1);
                            }
                            if(k== Str_s.length-1){
                                Str_s[k]=Str_s[k].substring(1,Str_s[k].length()-2);
                            }
                            if(k!=7&&k!=0&&k!=Str_s.length-1){
                                Str_s[k]=Str_s[k].substring(1,Str_s[k].length()-1);
                            }
                        }
                        listData=new ListData(Str_s[0],Str_s[1],Str_s[2],Str_s[6],Str_s[5],Str_s[4],"",Str_s[3]
                                ,"",Str_s[8],Str_s[7],"");
                        list_data_flag.add(listData);
                    }
                    list_data=list_data_flag;
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
//    public void downloadFile(){
//        RequestParams params = new RequestParams("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Fca3c577fca3ed067015ba144d11c4bcdd61348c4.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621306046&t=66837f7202d0d974bd0be653b2754dea");
//        params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg");
//        params.setCancelFast(true);
//        params.setAutoRename(true);
//        params.setAutoResume(true);
//        params.setExecutor(new PriorityExecutor(3,true));
//        x.http().get(params, new Callback.ProgressCallback<File>() {
//
//            @Override
//            public void onWaiting() {
//                Log.e("TAG","onWaiting");
//            }
//
//            @Override
//            public void onStarted() {
//                Log.e("TAG","onStarted");
//            }
//
//            @Override
//            public void onLoading(long total, long current, boolean isDownloading) {
//                progressbar.setMax((int)total);
//                progressbar.setProgress((int)current);
//                Log.e("TAG","onLoading=="+current+"/"+total+",isDownloading=="+isDownloading);
//            }
//
//            @Override
//            public void onSuccess(File result) {
//                Log.e("TAG","onSuccess=="+result.toString());
//                Toast.makeText(xUtils3TestActivity.this,"下载完成",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("TAG","onError=="+ex.toString());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                Log.e("TAG","onCancelled=="+cex.toString());
//            }
//
//            @Override
//            public void onFinished() {
//                Log.e("TAG","onFinished");
//            }
//        });
//    }
}