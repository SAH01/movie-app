package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringJoiner;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener,
        TextWatcher {
    private String TAG = "ifu25";

    private ImageButton mIbNavigationBack;
    private LinearLayout mLlLoginPull;
    private View mLlLoginLayer;
    private LinearLayout mLlLoginOptions;
    private EditText mEtLoginUsername;
    private EditText mEtLoginPwd;
    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button login_submit;
    private LinearLayout mLlLoginPwd;
    private ImageView mIvLoginPwdDel;
    private ImageView mIvLoginLogo;
    private LinearLayout mLayBackBar;
    private TextView mTvLoginForgetPwd;
    private Button login_register;

    //全局Toast
    private Toast mToast;

    private int mLogoHeight;
    private int mLogoWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
    }
    private void initView() {
        login_register=(Button)findViewById(R.id.login_register);
        login_register.setOnClickListener(this);
        login_submit=(Button)findViewById(R.id.login_submit);
        login_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register:
                //注册
                startActivity(new Intent(SigninActivity.this, RegisterActivity.class));
                break;
            case R.id.login_submit:
                //登录
                startActivity(new Intent(SigninActivity.this, MainActivity.class));
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