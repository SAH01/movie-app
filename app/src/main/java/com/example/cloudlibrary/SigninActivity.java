package com.example.cloudlibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.Data.AllData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.StringJoiner;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener,
        TextWatcher {
//    private String TAG = "ifu25";
//
//    private ImageButton mIbNavigationBack;
//    private LinearLayout mLlLoginPull;
//    private View mLlLoginLayer;
//    private LinearLayout mLlLoginOptions;
//    private EditText mEtLoginUsername;
//    private EditText mEtLoginPwd;
//    private LinearLayout mLlLoginUsername;
//    private ImageView mIvLoginUsernameDel;
//    private Button login_submit;
//    private LinearLayout mLlLoginPwd;
//    private ImageView mIvLoginPwdDel;
//    private ImageView mIvLoginLogo;
//    private LinearLayout mLayBackBar;
//    private TextView mTvLoginForgetPwd;
//    private Button login_register;
//
//    //全局Toast
//    private Toast mToast;
//
//    private int mLogoHeight;
//    private int mLogoWidth;
    private EditText login_phone;
    private EditText login_pass;
    private Button login_register;
    private Button login_submit;
    private AllData allData=new AllData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            Log.e("TAG",data.getStringExtra("user_password")+data.getStringExtra("user_phone"));
            login_pass.setText(data.getStringExtra("user_password"));
            login_phone.setText(data.getStringExtra("user_phone"));
            String user_name=data.getStringExtra("user_phone");
            SharedPreferences.Editor editor=getSharedPreferences("userdata",MODE_PRIVATE).edit();
            editor.clear();
            editor.putString("user_password",login_pass.getText().toString());
            editor.putString("user_phone",login_phone.getText().toString());
            editor.putString("user_name",user_name);
            editor.commit();
        }
    }
    private void initView() {
        login_register=(Button)findViewById(R.id.login_register);
        login_register.setOnClickListener(this);
        login_submit=(Button)findViewById(R.id.login_submit);
        login_submit.setOnClickListener(this);
        login_phone=(EditText)findViewById(R.id.login_phone);
        login_pass=(EditText)findViewById(R.id.login_pass);
        SharedPreferences sp=getSharedPreferences("userdata",MODE_PRIVATE);
        login_phone.setText(sp.getString("user_phone",""));
        login_pass.setText(sp.getString("user_password",""));
        String name=sp.getString("user_name","");
        Log.e("TAG",login_pass.getText().toString()+","+login_phone.getText().toString()+","+name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register:
                //注册
                startActivityForResult(new Intent(SigninActivity.this, RegisterActivity.class),1);
                break;
            case R.id.login_submit:
                //登录
                RequestParams params = new RequestParams("http://"+ allData.getUrl() +":5000/android_query_user");
                //params.setMultipart(true);
                String phone=login_phone.getText().toString();
                params.addBodyParameter("phone", phone);
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
                            JSONArray array=object.optJSONArray("data");
                            if(login_pass.getText().toString().equals(array.getString(1)))
                            {
                                SharedPreferences.Editor editor=getSharedPreferences("userdata",MODE_PRIVATE).edit();
                                editor.clear();
                                editor.putString("user_password",login_pass.getText().toString());
                                editor.putString("user_phone",login_phone.getText().toString());
                                editor.putString("user_name",array.getString(3));
                                editor.commit();
                                Toast.makeText(SigninActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SigninActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(SigninActivity.this,"密码或手机号错误",Toast.LENGTH_LONG).show();
                            }
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
                break;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onGlobalLayout() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}