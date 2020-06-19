package com.fmj.fmj.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fmj.fmj.MyApp;
import com.fmj.fmj.R;
import com.fmj.fmj.bean.MemberInfo;
import com.fmj.fmj.db.DaoSession;
import com.fmj.fmj.db.MemberInfoDao;
import com.hacknife.immersive.Immersive;

import java.util.UUID;

/**
 * Created by Administrator on 2020/6/19.
 * //
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                           O\  =  /O
 * //                        ____/`---'\____
 * //                      .'  \\|     |//  `.
 * //                     /  \\|||  :  |||//  \
 * //                    /  _||||| -:- |||||-  \
 * //                    |   | \\\  -  /// |   |
 * //                    | \_|  ''\---/''  |   |
 * //                    \  .-\__  `-`  ___/-. /
 * //                  ___`. .'  /--.--\  `. . __
 * //               ."" '<  `.___\_<|>_/___.'  >'"".
 * //              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //              \  \ `-.   \_ __\ /__ _/   .-` /  /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * //
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText et_user;
    private EditText et_password;
    private EditText et_name;
    private EditText et_phone;
    private RelativeLayout rl_register;

    private Context mContxt;
    private MemberInfoDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        //设置状态栏背景透明
        Immersive.setContentView(this, R.layout.activity_register, R.color.colorAccent, R.color.colorAccent, false, false);

        mContxt =this;

        MyApp myApp = (MyApp) getApplication();
        DaoSession daoSession =  myApp.getDaoSession();
        userDao = daoSession.getMemberInfoDao();

        initView();
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        rl_register = (RelativeLayout) findViewById(R.id.rl_register);

        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_user.getText())){
                    Toast.makeText(mContxt ,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText())){
                    Toast.makeText(mContxt ,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(et_name.getText())){
                    Toast.makeText(mContxt ,"请输入姓名",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(et_phone.getText())){
                    Toast.makeText(mContxt ,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }

                MemberInfo info = new MemberInfo();
                info.setId(UUID.randomUUID().toString());
                info.setUserName(et_user.getText().toString());
                info.setPassWord(et_password.getText().toString());
                info.setName(et_name.getText().toString());
                info.setPhone(et_phone.getText().toString());
                userDao.insert(info);
            }
        });
    }
}
