package com.geekband.jonymoon.moran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.geekband.jonymoon.moran.ApplicationContext;
import com.geekband.jonymoon.moran.R;
import com.geekband.jonymoon.moran.util.NetworkStatus;
import com.geekband.jonymoon.moran.util.StreamUtil;
import com.geekband.jonymoon.moran.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {
    private static final int SUCCESS = 1;
    private static final int ERROR = 0;
    private  AutoCompleteTextView mEmail;
    private EditText mpassword;
    private EditText mConfirmPassword;
    private EditText mNickname;
    private Button mSignUp;
    private Button mSignIn;
    private ApplicationContext mAppContext;
    private static final String mPath="/user/register";
    private Handler handler;

    {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case SUCCESS:
                        Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
                        break;
                    case ERROR:
                        Toast.makeText(getApplicationContext(),"注册失败", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }

            }
        };
    }
    private View.OnClickListener listener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_sign_in:
                    signIn();
                    break;
                case R.id.btn_sign_up:
                    signUp();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        mEmail = (AutoCompleteTextView) findViewById(R.id.sign_up_email);
        mpassword = (EditText) findViewById(R.id.edt_sign_password);
        mConfirmPassword = (EditText) findViewById(R.id.edt_confirm_password);
        mNickname = (EditText) findViewById(R.id.edt_nickname);
        mSignIn = (Button) findViewById(R.id.btn_sign_in);
        mSignUp = (Button) findViewById(R.id.btn_sign_up);
        mAppContext= (ApplicationContext) getApplication();

        mSignUp.setOnClickListener(listener);
        mSignIn.setOnClickListener(listener);

    }
    private void signUp() {
        mNickname.setError(null);
        mEmail.setError(null);
        mpassword.setError(null);
        mConfirmPassword.setError(null);

        final String nickName=mNickname.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mpassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        Boolean isValid = true;
        View focusView=null;

//        昵称验证
        if (StringUtil.isEmpty(nickName)) {
            mNickname.setError(getString(R.string.error_empty_nickname));
            focusView=mNickname;
            isValid=false;
        }else if (StringUtil.isNicknameValid(nickName)==false) {
            mNickname.setError(getString(R.string.error_patten_nickname));
            focusView=mNickname;
            isValid=false;
        }

        //验证邮箱
        if (StringUtil.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_empty_email));
            focusView=mEmail;
            isValid=false;
        }else if (StringUtil.isEmail(email)==false) {
            mEmail.setError(getString(R.string.error_patten_email));
            focusView=mEmail;
            isValid=false;
        }

        //密码验证
        if (StringUtil.isEmpty(password)) {
            mpassword.setError(getString(R.string.error_empty_password));
            focusView=mpassword;
            isValid=false;
        }else if (StringUtil.isPasswardValid(password)==false) {
            mpassword.setError(getString(R.string.error_length_password));
            focusView=mpassword;
            isValid=false;
        }

        //密码确认
        if (StringUtil.isEmpty(confirmPassword)) {
            mConfirmPassword.setError(getString(R.string.error_confirm_password));
            focusView=mConfirmPassword;
            isValid=false;
        }else if (password.equals(confirmPassword)==false) {
            mConfirmPassword.setError(getString(R.string.error_different_password));
            focusView=mConfirmPassword;
            isValid=false;
        }

        if (isValid == false) {
            focusView.requestFocus();
        } else {
            if (NetworkStatus.isNetworkConnected(getApplicationContext())) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            //获取密码的MD5值，并截取前20个字符。
                            String psd = StringUtil.getMD5(password).substring(0, 20);
                            String gbid="GeekBank-G2015020144";//获取个人ID
                            JSONObject user = new JSONObject();//创建JSON对象
                            user.put("username", nickName);
                            user.put("email", email);
                            user.put("password", psd);
                            user.put("GeekBandID", gbid);
                            //从全局类获取访问路径
                            String url = mAppContext.getUrl(mPath);
                            doPostRequest(url,user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            } else {

                Toast.makeText(getApplicationContext(),
                        getString(R.string.unavailable_network_connection),Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void signIn() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);

    }


    private void doPostRequest(String path, JSONObject date) {
        try {
            //获得传输实体
            byte[] entity = date.toString().getBytes("UTF-8");
            URL url = new URL(path);

            //获取HttpURLConnection实例
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(entity.length));
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(entity);
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream=connection.getInputStream();
                byte[] is = StreamUtil.readInputStream(inputStream);
                String json = new String(is);
                JSONObject jsonObject = new JSONObject(json);
                int status=jsonObject.getInt("status");
                String message=jsonObject.getString("message");
                //创建消息对象
                Message msg=Message.obtain();
                msg.obj=json;
                handler.sendMessage(msg);
                msg.what=SUCCESS;
                msg.obj=message;
                handler.sendMessage(msg);
            } else {
                Message msg=Message.obtain();
                msg.what=ERROR;
                handler.sendMessage(msg);
            }

        } catch (UnsupportedEncodingException e) {//编码方面异常
            e.printStackTrace();
        } catch (MalformedURLException e) {//路径异常
            e.printStackTrace();
        } catch (ProtocolException e) {//协议异常
            e.printStackTrace();
        } catch (IOException e) {//输入输出方面异常
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
