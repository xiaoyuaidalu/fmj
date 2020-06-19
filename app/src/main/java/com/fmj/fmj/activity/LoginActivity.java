package com.fmj.fmj.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.List;
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
public class LoginActivity extends AppCompatActivity {

    private EditText et_user;
    private EditText et_password;
    private RelativeLayout rl_register;
    private RelativeLayout rl_login;

    private Context mContxt;
    private MemberInfoDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        Immersive.setContentView(this, R.layout.activity_login, R.color.colorAccent, R.color.colorAccent, false, false);
        mContxt =this;

        MyApp myApp = (MyApp) getApplication();
        DaoSession daoSession =  myApp.getDaoSession();
        userDao = daoSession.getMemberInfoDao();

        initView();


    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);

        rl_register = (RelativeLayout) findViewById(R.id.rl_register);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录
               List<MemberInfo> infos = userDao.queryRaw("where USER_NAME=? and PASS_WORD=?",et_user.getText().toString() ,et_password.getText().toString());
               infos.get(0).getPassWord();
            }
        });

        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContxt , RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}
