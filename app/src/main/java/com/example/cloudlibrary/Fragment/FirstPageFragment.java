package com.example.cloudlibrary.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cloudlibrary.Adapter.CloudPagerAdapter;
import com.example.cloudlibrary.Data.AllData;
import com.example.cloudlibrary.Data.ListData;
import com.example.cloudlibrary.MoviePagerActivity;
import com.example.cloudlibrary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class FirstPageFragment extends Fragment {
    AllData allData=new AllData();
    Button search_btn;
    EditText search_input;
    ListData listData;
    List<ListData> list_data;
    ListView search_show;
    CloudPagerAdapter cloudPagerAdapter;
    View view;
    public FirstPageFragment(){
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_first_page, container, false);
        search_btn=view.findViewById(R.id.search_btn);
        search_input=view.findViewById(R.id.search_input);
        search_show=view.findViewById(R.id.search_show);
        search_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String title_star=search_input.getText().toString().trim();
                RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/query_head");
                //params.setMultipart(true);
                params.addBodyParameter("title_star",title_star );
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
                            search_show.setAdapter(cloudPagerAdapter);
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
        search_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
}