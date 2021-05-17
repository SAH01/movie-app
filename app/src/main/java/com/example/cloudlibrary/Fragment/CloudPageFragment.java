package com.example.cloudlibrary.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.Adapter.CloudPagerAdapter;
import com.example.cloudlibrary.Data.AllData;
import com.example.cloudlibrary.Data.ListData;
import com.example.cloudlibrary.MoviePagerActivity;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.tools.FlowRadioGroup;
import com.example.cloudlibrary.tools.xUtils3TestActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class CloudPageFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    FlowRadioGroup type_group;
    FlowRadioGroup date_group;
    FlowRadioGroup area_group;
    RadioGroup first_group;
    Button more_show;
    Button less_show;
    TextView test;
    int num=0;
    String[] str_s={"","","","","0"};
    View view;
    ListView class_show;
    CloudPagerAdapter cloudPagerAdapter;

    xUtils3TestActivity xutils=new xUtils3TestActivity();
    List<ListData> list_data=new ArrayList<>();
    ListData listData;

    private AllData allData=new AllData();

    public CloudPageFragment(){
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cloud_page, container, false);
        //控件声明
        type_group=(FlowRadioGroup)view.findViewById(R.id.type_group);
        date_group=(FlowRadioGroup)view.findViewById(R.id.date_group);
        area_group=(FlowRadioGroup)view.findViewById(R.id.area_group);
        first_group=(RadioGroup)view.findViewById(R.id.first_group);
        more_show=(Button)view.findViewById(R.id.more_show);
        less_show=(Button)view.findViewById(R.id.less_show);
        class_show=(ListView)view.findViewById(R.id.class_show);
        //分类排序监听
        type_group.setOnCheckedChangeListener(this);
        date_group.setOnCheckedChangeListener(this);
        area_group.setOnCheckedChangeListener(this);
        first_group.setOnCheckedChangeListener(this);
        //上一页下一页点击事件
        more_show.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                num = num + 20;
                str_s[4] = "" + num;
                RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/query_tag");
                //params.setMultipart(true);
                params.addBodyParameter("type", str_s[0]);
                params.addBodyParameter("date", str_s[1]);
                params.addBodyParameter("area", str_s[2]);
                params.addBodyParameter("first", str_s[3]);
                params.addBodyParameter("num", str_s[4]);
                //params.addBodyParameter("File",new File(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg"),null,"YLY.jpg");
                x.http().get(params, new Callback.CacheCallback<String>() {
                    List<ListData> list_data_flag = new ArrayList<>();

                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }

                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONArray array = object.optJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
//                        map=new HashMap<>();
                                String title = array.getString(i);
                                String[] Str_s;
                                Str_s = title.split(",");
                                String str = "";
                                for (int k = 0; k < Str_s.length; k++) {
                                    if (k == 0) {
                                        Str_s[k] = Str_s[k].substring(2, Str_s[k].length() - 1);
                                    }
                                    if (k == Str_s.length - 1) {
                                        Str_s[k] = Str_s[k].substring(1, Str_s[k].length() - 2);
                                    }
                                    if (k != 7 && k != 0 && k != Str_s.length - 1) {
                                        Str_s[k] = Str_s[k].substring(1, Str_s[k].length() - 1);
                                    }
                                }
                                listData = new ListData(Str_s[0], Str_s[1], Str_s[2], Str_s[6], Str_s[5], Str_s[4], "", Str_s[3]
                                        , "", Str_s[8], Str_s[7], "");
                                list_data_flag.add(listData);
                            }
                            list_data = list_data_flag;
                            cloudPagerAdapter = new CloudPagerAdapter(getContext(), list_data);
                            class_show.setAdapter(cloudPagerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("TAG", "onError==" + ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.e("TAG", "onCancelled==" + cex.toString());
                    }

                    @Override
                    public void onFinished() {
                        Log.e("TAG", "onFinished");
                    }
                });
            }
        });
        less_show.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                num = num - 20;
                if(num<0){
                    num=0;
                }
                str_s[4] = "" + num;
                RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/query_tag");
                //params.setMultipart(true);
                params.addBodyParameter("type", str_s[0]);
                params.addBodyParameter("date", str_s[1]);
                params.addBodyParameter("area", str_s[2]);
                params.addBodyParameter("first", str_s[3]);
                params.addBodyParameter("num", str_s[4]);
                //params.addBodyParameter("File",new File(Environment.getExternalStorageDirectory()+"/test_download/YLY.jpg"),null,"YLY.jpg");
                x.http().get(params, new Callback.CacheCallback<String>() {
                    List<ListData> list_data_flag = new ArrayList<>();

                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }

                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONArray array = object.optJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
//                        map=new HashMap<>();
                                String title = array.getString(i);
                                String[] Str_s;
                                Str_s = title.split(",");
                                String str = "";
                                for (int k = 0; k < Str_s.length; k++) {
                                    if (k == 0) {
                                        Str_s[k] = Str_s[k].substring(2, Str_s[k].length() - 1);
                                    }
                                    if (k == Str_s.length - 1) {
                                        Str_s[k] = Str_s[k].substring(1, Str_s[k].length() - 2);
                                    }
                                    if (k != 7 && k != 0 && k != Str_s.length - 1) {
                                        Str_s[k] = Str_s[k].substring(1, Str_s[k].length() - 1);
                                    }
                                }
                                listData = new ListData(Str_s[0], Str_s[1], Str_s[2], Str_s[6], Str_s[5], Str_s[4], "", Str_s[3]
                                        , "", Str_s[8], Str_s[7], "");
                                list_data_flag.add(listData);
                            }
                            list_data = list_data_flag;
                            cloudPagerAdapter = new CloudPagerAdapter(getContext(), list_data);
                            class_show.setAdapter(cloudPagerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("TAG", "onError==" + ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.e("TAG", "onCancelled==" + cex.toString());
                    }

                    @Override
                    public void onFinished() {
                        Log.e("TAG", "onFinished");
                    }
                });
            }
        });
        //listview嵌套问题解决
        class_show.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                class_show.requestDisallowInterceptTouchEvent(false);
            } else {
                class_show.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });
        //listview点击事件
        class_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListData listData_flag = list_data.get(i);
                Intent intent=new Intent();
                intent.putExtra("title",listData_flag.getTitle());
                intent.putExtra("scorenum",listData_flag.getScorenum());
                intent.setClass(view.getContext(), MoviePagerActivity.class);
                startActivity(intent);
                Toast.makeText(view.getContext(),listData_flag.getTitle()+listData_flag.getScorenum(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    //分类排序搜索
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group==type_group){
            RadioButton type_button=(RadioButton)view.findViewById(checkedId);
            str_s[0]=type_button.getText().toString();
        }
        if(group==date_group){
            RadioButton type_button=(RadioButton)view.findViewById(checkedId);
            str_s[1]=type_button.getText().toString();
        }
        if(group==area_group){
            RadioButton type_button=(RadioButton)view.findViewById(checkedId);
            str_s[2]=type_button.getText().toString();
        }
        if(group==first_group){
            RadioButton type_button=(RadioButton)view.findViewById(checkedId);
            str_s[3]=type_button.getText().toString();
        }
        num=0;
        str_s[4]=""+num;
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/query_tag");
        //params.setMultipart(true);
        params.addBodyParameter("type",str_s[0]);
        params.addBodyParameter("date",str_s[1]);
        params.addBodyParameter("area",str_s[2]);
        params.addBodyParameter("first",str_s[3]);
        params.addBodyParameter("num",str_s[4]);
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
                    cloudPagerAdapter=new CloudPagerAdapter(getContext(),list_data);
                    class_show.setAdapter(cloudPagerAdapter);
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