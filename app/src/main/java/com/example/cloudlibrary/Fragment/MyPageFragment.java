package com.example.cloudlibrary.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cloudlibrary.Adapter.CloudPagerAdapter;
import com.example.cloudlibrary.Adapter.MyPagerListAdapter;
import com.example.cloudlibrary.Data.AllData;
import com.example.cloudlibrary.Data.ListData;
import com.example.cloudlibrary.MovieLikeActivity;
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

public class MyPageFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup my_pager_group;
    private RadioButton want_look;
    private RadioButton now_look;
    private RadioButton have_look;
    private List<ListData> list_data=new ArrayList<>();
    private ListView mypager_like_list;
    private ListData listData;
    private View view;
    private MyPagerListAdapter myPagerListAdapter;
    private AllData allData=new AllData();

    String userphone="";
    String usertype="";

    public static final int MODE_PRIVATE = 0x0000;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public MyPageFragment(){
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_page, container, false);
        my_pager_group = (RadioGroup) view.findViewById(R.id.my_pager_group);
        want_look = (RadioButton) view.findViewById(R.id.want_look);
        now_look = (RadioButton) view.findViewById(R.id.now_look);
        have_look = (RadioButton) view.findViewById(R.id.have_look);
        mypager_like_list=(ListView)view.findViewById(R.id.mypager_like_list);
        mypager_like_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListData listData_flag = list_data.get(i);
                Intent intent=new Intent();
                intent.putExtra("title",listData_flag.getTitle());
                intent.putExtra("scorenum",listData_flag.getScorenum());
                intent.putExtra("usertype",usertype);
                intent.setClass(view.getContext(), MovieLikeActivity.class);
                startActivity(intent);
                Toast.makeText(view.getContext(),listData_flag.getTitle()+listData_flag.getScorenum(),Toast.LENGTH_LONG).show();
            }
        });
        my_pager_group.setOnCheckedChangeListener(this);
        RadioButton[] rbs = new RadioButton[3];
        rbs[0] =want_look;
        rbs[1] = now_look;
        rbs[2] = have_look;
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SharedPreferences sp=getActivity().getSharedPreferences("userdata",MODE_PRIVATE);
        switch (checkedId) {
            case R.id.want_look:
                userphone=sp.getString("user_phone","");
                usertype="想看";
                http_like_query(userphone,usertype);
                break;
            case R.id.now_look:
                userphone=sp.getString("user_phone","");
                usertype="在看";
                http_like_query(userphone,usertype);
                break;
            case R.id.have_look:
                userphone=sp.getString("user_phone","");
                usertype="看过";
                http_like_query(userphone,usertype);
                break;
        }
    }
    public void http_like_query(String userphone,String usertype){
        RequestParams params = new RequestParams("http://"+allData.getUrl()+":5000/android_like_query");
        //params.setMultipart(true);
        params.addBodyParameter("userphone", userphone);
        params.addBodyParameter("usertype", usertype);
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
                        String title=array.getJSONArray(i).getString(1);
                        String scorenum=array.getJSONArray(i).getInt(3)+"";
                        String score=array.getJSONArray(i).getString(5);
                        String url_flag=array.getJSONArray(i).getString(4);
                        listData = new ListData(title, "", "", "", "", "", "", score
                                , "", url_flag, scorenum, "");
                        list_data_flag.add(listData);
                    }
                    list_data = list_data_flag;
                    myPagerListAdapter = new MyPagerListAdapter(getContext(),list_data);
                    mypager_like_list.setAdapter(myPagerListAdapter);
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
}