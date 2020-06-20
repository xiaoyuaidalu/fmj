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
import com.fmj.fmj.bean.GoodsInfo;
import com.fmj.fmj.bean.MemberInfo;
import com.fmj.fmj.db.DaoSession;
import com.fmj.fmj.db.GoodsInfoDao;
import com.fmj.fmj.db.MemberInfoDao;
import com.fmj.fmj.utils.SPUtils;
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
    private RelativeLayout rl_custom;

    private Context mContxt;
    private MemberInfoDao userDao;
    private GoodsInfoDao goodsInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        Immersive.setContentView(this, R.layout.activity_login, R.color.colorAccent, R.color.colorAccent, false, false);
        mContxt =this;

        MyApp myApp = (MyApp) getApplication();
        DaoSession daoSession =  myApp.getDaoSession();
        userDao = daoSession.getMemberInfoDao();
        goodsInfoDao = daoSession.getGoodsInfoDao();



        if (SPUtils.getBoolean(this , "isFirst" ,true)){
            //装机第一次的时候填充商品
            GoodsInfo info = new GoodsInfo();
            info.setId("bskl");
            info.setGoodNames("百事可乐");
            info.setImageName("goods_bskl");
            info.setNum(100);
            info.setPrice(2);
            goodsInfoDao.insert(info);

            GoodsInfo info1 = new GoodsInfo();
            info1.setId("kkkl");
            info1.setGoodNames("可口可乐");
            info1.setImageName("goods_kkkl");
            info1.setNum(100);
            info1.setPrice(2);
            goodsInfoDao.insert(info1);

            GoodsInfo info2 = new GoodsInfo();
            info2.setId("wt");
            info2.setGoodNames("维他");
            info2.setImageName("goods_wt");
            info2.setNum(100);
            info2.setPrice(5);
            goodsInfoDao.insert(info2);

            GoodsInfo info3 = new GoodsInfo();
            info3.setId("wznn");
            info3.setGoodNames("旺仔牛奶");
            info3.setImageName("goods_wznn");
            info3.setNum(100);
            info3.setPrice(4);
            goodsInfoDao.insert(info3);

            GoodsInfo info4 = new GoodsInfo();
            info4.setId("xb");
            info4.setGoodNames("雪碧");
            info4.setImageName("goods_xb");
            info4.setNum(100);
            info4.setPrice(2);
            goodsInfoDao.insert(info4);

            GoodsInfo info5 = new GoodsInfo();
            info5.setId("xbk");
            info5.setGoodNames("星巴克");
            info5.setImageName("goods_xbk");
            info5.setNum(100);
            info5.setPrice(6);
            goodsInfoDao.insert(info5);

            SPUtils.putBoolean(this , "isFirst" ,false);
        }

        initView();


    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);

        rl_register = (RelativeLayout) findViewById(R.id.rl_register);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        rl_custom = (RelativeLayout) findViewById(R.id.rl_custom);

        rl_login.setOnClickListener(new View.OnClickListener() {
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
                //登录
               List<MemberInfo> infos = userDao.queryRaw("where USER_NAME=? and PASS_WORD=?",et_user.getText().toString() ,et_password.getText().toString());
               if (infos.size()>0){
                   Toast.makeText(mContxt ,"登录成功!",Toast.LENGTH_SHORT).show();

                   SPUtils.putString(mContxt , "name" ,infos.get(0).getName());
                   SPUtils.putString(mContxt , "id" ,infos.get(0).getId());

                   Intent intent = new Intent(mContxt , MainActivity.class);
                   startActivity(intent);
                   finish();

               }else {
                   Toast.makeText(mContxt ,"用户名或密码错误",Toast.LENGTH_SHORT).show();
               }
            }
        });

        rl_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.putString(mContxt , "name" ,"游客");
                //SPUtils.putString(mContxt , "id" ,infos.get(0).getId());
                Intent intent = new Intent(mContxt , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContxt , RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
