package com.example.mytext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class enrollActivity extends AppCompatActivity {

    private ImageView iv_eye_enroll;
    private EditText et_enroll_one;
    private EditText et_enroll_two;
    private EditText username_enroll;
    private Button btn_sure;
    private boolean isOpenEye = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        intiView();
        initHide();
        ViewPassword();
        Create();
    }


    private void Create() {
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = username_enroll.getText().toString().trim();
                String password_one = et_enroll_one.getText().toString().trim();
                String password_two = et_enroll_two.getText().toString().trim();

                //判断输入框内容
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(enrollActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password_one)) {
                    Toast.makeText(enrollActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password_two)) {
                    Toast.makeText(enrollActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password_one.equals(password_two)) {
                    Toast.makeText(enrollActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                    /*
                     *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
                     */
                } else if (isExistUserName(username)) {
                    Toast.makeText(enrollActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(enrollActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //把账号、密码和账号标识保存到sp里面
                    /*
                     * 保存账号和密码到SharedPreferences中
                     */
                    saveRegisterInfo(username, password_one);
                    //注册成功后把账号传递到LoginActivity.java中
                    // 返回值到loginActivity显示
                    Intent data = new Intent();
                    data.putExtra("userName", username);
                    setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    enrollActivity.this.finish();
                }
            }
        });
    }

    /*
     * 从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
     */
    private boolean isExistUserName(String userName) {
        boolean has_userName = false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw = sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if (!TextUtils.isEmpty(spPsw)) {
            has_userName = true;
        }
        return has_userName;
    }

    /*
     * 保存账号和密码到SharedPreferences中SharedPreferences
     */
    private void saveRegisterInfo(String userName, String psw) {
        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor = sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw);
        //提交修改 editor.commit();
        editor.commit();
    }



    private void intiView() {
        et_enroll_one = (EditText) findViewById(R.id.et_enroll_one);
        iv_eye_enroll = (ImageView) findViewById(R.id.iv_eye_enroll);
        et_enroll_two = (EditText) findViewById(R.id.et_enroll_two);
        username_enroll = (EditText) findViewById(R.id.username_enroll);
        btn_sure = (Button) findViewById(R.id.btn_sure);
    }


    private void initHide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void ViewPassword() {
        iv_eye_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpenEye) {
                    iv_eye_enroll.setSelected(true);
                    isOpenEye = true;
                    //密码可见
                    et_enroll_one.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    iv_eye_enroll.setSelected(false);
                    isOpenEye = false;
                    //密码不可见
                    et_enroll_one.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }


}