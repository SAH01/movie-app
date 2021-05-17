package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudlibrary.Adapter.CloudPagerAdapter;
import com.example.cloudlibrary.Data.AllData;
import com.example.cloudlibrary.Data.ListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private EditText register_username;
    private EditText register_userpass;
    private EditText register_userpass2;
    private EditText register_userphone;
    private EditText register_code;
    private TextView register_getcode;
    private Button register_submit;
    private int type = 1;
    private AllData allData=new AllData();
    private CountDownTimer mCountDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            register_getcode.setText((l / 1000 )+ "秒后可重发");
        }

        @Override
        public void onFinish() {
            register_getcode.setEnabled(true);
            register_getcode.setText("获取验证码");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_username = (EditText) findViewById(R.id.register_username);
        register_userpass = (EditText) findViewById(R.id.register_userpass);
        register_userpass2 = (EditText) findViewById(R.id.register_userpass2);
        register_userphone= (EditText) findViewById(R.id.register_userphone);
//        register_code = (EditText) findViewById(R.id.register_code);
//        register_getcode = (TextView)findViewById(R.id.register_getcode);
        register_submit = (Button) findViewById(R.id.register_submit);
//        register_getcode.setOnClickListener(this);
        register_submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int flag=1;
        String phone = register_userphone.getText().toString().trim();
//        String code =register_code.getText().toString().trim();
        String password = register_userpass.getText().toString().trim();
        String repassword = register_userpass2.getText().toString().trim();
        String name = register_username.getText().toString().trim();
        judgePhoneNums(phone,flag);
        if(password.isEmpty()){
            Toast.makeText(this, "密码不能为空",Toast.LENGTH_SHORT).show();
            flag=0;
        }
        if(name.isEmpty()){
            Toast.makeText(this, "用户昵称不能为空",Toast.LENGTH_SHORT).show();
            flag=0;
        }
        if(!password.equals(repassword)){
            Toast.makeText(this, "两次密码不一样",Toast.LENGTH_SHORT).show();
            flag=0;
        }
        if(flag==1){
            RequestParams params = new RequestParams("http://"+ allData.getUrl() +":5000/android_register");
            //params.setMultipart(true);
            params.addBodyParameter("name", name);
            params.addBodyParameter("phone", phone);
            params.addBodyParameter("password", password);
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
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent();
                            intent.putExtra("user_password",password);
                            intent.putExtra("user_phone",phone);
                            intent.putExtra("user_name",name);
                            setResult(1,intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_LONG).show();
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
        }
//        switch (v.getId()) {
//            case R.id.register_getcode:
//                register_getcode.requestFocus();
//                if (!judgePhoneNums(phone)) {
//                    return;
//                } else {
//                    mCountDownTimer.start();
//                    //这里的服务器接口是无效的需要换成你自己获取验证码的接口地址
//                    RequestParams params = new RequestParams("https://www.baidu.com/?tn=40020637_6_oem_dg");
//                    String str = String.valueOf(type);
//                    //需要携带的参数
//                    params.addBodyParameter("phone", phone);
//                    params.addBodyParameter("type", str);
//                    x.http().get(params, new Callback.CacheCallback<String>() {
//                        @Override
//                        public void onSuccess(String result) {
//                            Log.e(TAG, result.toString());
//                            register_code.setText(result.toString());
//                        }
//
//                        @Override
//                        public void onError(Throwable ex, boolean isOnCallback) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(CancelledException cex) {
//
//                        }
//
//                        @Override
//                        public void onFinished() {
//
//                        }
//
//                        @Override
//                        public boolean onCache(String result) {
//                            return false;
//                        }
//                    });
//                }
//                break;
//            case R.id.register_submit:
//                if (!judgePhoneNums(phone)) {
//                    return;
//                } else if(TextUtils.isEmpty(code)) {
//                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
//                } else if(password.length() < 6 ) {
//                    Toast.makeText(this, "请输入长度大于6位的密码", Toast.LENGTH_SHORT).show();
//                } else if(!TextUtils.equals(password,repassword)) {
//                    Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
//                } else if(register_submit == v) {
//                    //这里的服务器接口是无效的需要换成你自己的注册接口地址
//                    RequestParams params = new RequestParams("http://api.php/Public/smsRegister");
//                    //需要带入的参数
//                    params.addBodyParameter("phone", phone);
//                    params.addBodyParameter("captcha", code);
//                    params.addBodyParameter("password", password);
//                    params.addBodyParameter("repassword", repassword);
//                    x.http().post(params, new Callback.CacheCallback<String>() {
//                        @Override
//                        public void onSuccess(String result) {
//                            Log.e(TAG, result.toString());
//                        }
//
//                        @Override
//                        public void onError(Throwable ex, boolean isOnCallback) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(CancelledException cex) {
//
//                        }
//
//                        @Override
//                        public void onFinished() {
//
//                        }
//
//                        @Override
//                        public boolean onCache(String result) {
//                            return false;
//                        }
//                    });
//                    break;
//                }
//        }
    }
    private boolean judgePhoneNums(String phoneNums,int flag) {
        if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "请输入正确的手机号",Toast.LENGTH_SHORT).show();
        flag=0;
        return false;
    }

    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        String strTel = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(strTel);
    }
}