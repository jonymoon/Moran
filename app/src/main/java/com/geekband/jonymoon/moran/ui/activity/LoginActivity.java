package com.geekband.jonymoon.moran.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.geekband.jonymoon.moran.R;
import com.geekband.jonymoon.moran.util.StringUtil;

public class LoginActivity extends AppCompatActivity {

//    定义成员变量
    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private CheckBox mRememberPassword;
    private Button mSignIn;
    private Button mSignUp;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

//        对UI小组进行引用
        mEmail = (AutoCompleteTextView) findViewById(R.id.autoTxt_login_email);
        mPassword = (EditText) findViewById(R.id.edt_login_password);
        mRememberPassword = (CheckBox) findViewById(R.id.chk_remember_password);
        mSignIn = (Button) findViewById(R.id.btn_sign_in);
        mSignUp = (Button) findViewById(R.id.btn_sign_up);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String email = pref.getString("email", "");
            String password = pref.getString("password", "");

            mEmail.setText(email);
            mPassword.setText(password);
            mRememberPassword.setChecked(true);
        }

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
//        设置错误提示
        mEmail.setError(null);
        mPassword.setError(null);

        String  email=mEmail.getText().toString().trim();
        String password=mPassword.getText().toString().trim();

        Boolean isValid=true;
        View focusView=null;

//        邮箱验证
        if (StringUtil.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_empty_email));
            focusView=mEmail;
            isValid=false;
        }else if (!StringUtil.isEmail(email)) {
            mEmail.setError(getString(R.string.error_patten_email));
            focusView=mEmail;
            isValid=false;
        }

//        密码验证
        if (StringUtil.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_empty_password));
            focusView=mPassword;
            isValid=false;
        }else if (!StringUtil.isPasswardValid(password)) {
            mPassword.setError(getString(R.string.error_length_password));
            focusView=mPassword;
            isValid=false;
        }


        if (isValid == false) {
            focusView.requestFocus();
        }else if (email.equals("test@moran.com") && password.equals("123456")) {
            editor=pref.edit();
            if (mRememberPassword.isChecked()) {
                editor.putString("email", email);
                editor.putString("password", StringUtil.getMD5(password));
                editor.putBoolean("remember_password", true);
            } else {
                editor.clear();
            }
            editor.commit();

            Toast.makeText(getApplicationContext(), getString(R.string.success_signIn), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_inValid),Toast.LENGTH_SHORT).show();
        }

    }

}
