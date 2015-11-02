package com.geekband.jonymoon.moran.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.geekband.jonymoon.moran.R;

public class LoginActivity extends AppCompatActivity {

//    定义成员变量
    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private CheckBox mRememberPassword;
    private Button mSingIn;
    private Button mSingUp;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        对UI小组进行引用
        mEmail = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPassword = (EditText) findViewById(R.id.login_password);
        mRememberPassword = (CheckBox) findViewById(R.id.remember_password);
        mSingIn = (Button) findViewById(R.id.login_button);
        mSingUp = (Button) findViewById(R.id.rign_up_button);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String email = pref.getString("email", "");
            String password = pref.getString("password", "");

            mEmail.setText(email);
            mPassword.setText(password);
            mRememberPassword.setChecked(true);
        }

        mSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
/*        mSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private boolean isEmailValid(String email) {
        return email.contains("@")==false;
    }

    private boolean isPasswardValid(String passward) {
        return passward.length() < 6;
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
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_empty_email));
            focusView=mEmail;
            isValid=false;
        }else if (isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_patten_email));
            focusView=mEmail;
            isValid=false;
        }

//        密码验证
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_empty_password));
            focusView=mPassword;
            isValid=false;
        }else if (isPasswardValid(password)) {
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
                editor.putString("password", password);
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
