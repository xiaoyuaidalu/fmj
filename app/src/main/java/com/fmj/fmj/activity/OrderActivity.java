package com.fmj.fmj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fmj.fmj.MyApp;
import com.fmj.fmj.R;
import com.fmj.fmj.bean.GoodsInfo;
import com.fmj.fmj.db.DaoSession;
import com.fmj.fmj.db.GoodsInfoDao;
import com.fmj.fmj.db.MemberInfoDao;
import com.fmj.fmj.utils.SPUtils;
import com.hacknife.immersive.Immersive;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private TextView tv_all_price;
    private TextView tv_price;
    private TextView tv_fk;
    private TextView tv_mnum;
    private TextView tv_bb;
    private TextView tv_num;
    private EditText et_num;
    private ImageView iv_good;

    private ImageView rl_5;
    private ImageView rl_10;
    private ImageView rl_20;
    private ImageView rl_50;
    private ImageView rl_100;
    private String good;
    private int price;
    private int num;
    private String id;
    private LinearLayout ll_rmb;
    private int all_price;
    private int yf;
    private GoodsInfoDao goodsInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Immersive.setContentView(this, R.layout.activity_order, R.color.colorAccent, R.color.colorAccent, false, false);
        title = findViewById(R.id.title);
        et_num = findViewById(R.id.et_num);
        tv_all_price = findViewById(R.id.tv_all_price);
        tv_price = findViewById(R.id.tv_price);
        iv_good = findViewById(R.id.iv_good);
        tv_fk = findViewById(R.id.tv_fk);
        ll_rmb = findViewById(R.id.ll_rmb);
        tv_mnum = findViewById(R.id.tv_mnum);
        tv_bb = findViewById(R.id.tv_bb);
        tv_num = findViewById(R.id.tv_num);

        rl_5 = findViewById(R.id.rl_5);
        rl_10 = findViewById(R.id.rl_10);
        rl_20 = findViewById(R.id.rl_20);
        rl_50 = findViewById(R.id.rl_50);
        rl_100 = findViewById(R.id.rl_100);
        rl_5.setOnClickListener(this);
        rl_10.setOnClickListener(this);
        rl_20.setOnClickListener(this);
        rl_50.setOnClickListener(this);
        rl_100.setOnClickListener(this);
        ll_rmb.setVisibility(View.GONE);
        title.setText("尊敬的" + SPUtils.getString(this, "name", "") + ",请结账!");

        MyApp myApp = (MyApp) getApplication();
        DaoSession daoSession = myApp.getDaoSession();
        goodsInfoDao = daoSession.getGoodsInfoDao();


        Intent datainten = getIntent();
        good = datainten.getStringExtra("good");


        id = datainten.getStringExtra("id");
        num = datainten.getIntExtra("num", -1);
        price = datainten.getIntExtra("price", -1);
        tv_num.setText("库存: "+num);
        int goodimageId = getResources().getIdentifier(good, "drawable", getPackageName());
        iv_good.setImageResource(goodimageId);

        tv_price.setText("单价: " + price + ".00元");
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("0".equals(s.toString())) {
                    et_num.setText("1");
                    tv_all_price.setText(price + ".00元");
                } else if ("".equals(s.toString())) {
                    tv_all_price.setText("0.00元");
                } else {
                    int num1 = Integer.parseInt(s.toString());
                    if (num1 > num) {
                        et_num.setText("" + num);
                        tv_all_price.setText(num * price + ".00元");
                    } else {
                        tv_all_price.setText(num1 * price + ".00元");
                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_fk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("立即付款".equals(tv_fk.getText().toString())) {
                    tv_fk.setText("请投入纸币");
                    ll_rmb.setVisibility(View.VISIBLE);
                    tv_mnum.setText(et_num.getText().toString());
                    et_num.setVisibility(View.GONE);
                    tv_mnum.setVisibility(View.VISIBLE);

                    all_price = Integer.parseInt(tv_all_price.getText().toString().replace(".00元", ""));
                }
            }
        });

        tv_bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_5:
                yf = 5;
                break;
            case R.id.rl_10:
                yf = 10;
                break;
            case R.id.rl_20:
                yf = 20;
                break;
            case R.id.rl_50:
                yf = 50;
                break;
            case R.id.rl_100:
                yf = 100;
                break;


        }
        if (all_price > yf) {
            all_price = all_price - yf;
            tv_fk.setText("已投入" + yf + "元,还差" + all_price + "元");
        } else if (all_price == yf) {
            tv_fk.setText("已付款,请拿商品");
            ll_rmb.setVisibility(View.GONE);
            tv_bb.setVisibility(View.VISIBLE);
            setgood();
        } else {
            all_price = yf - all_price;
            tv_fk.setText("已付款,请拿商品,找零" + all_price + "元");
            ll_rmb.setVisibility(View.GONE);
            tv_bb.setVisibility(View.VISIBLE);
            setgood();
        }
    }

    private void setgood() {
        List<GoodsInfo> infos = goodsInfoDao.queryRaw("where ID=?", id);
        int synmu =  infos.get(0).getNum()-Integer.parseInt(tv_mnum.getText().toString());
        infos.get(0).setNum(synmu);
        goodsInfoDao.update(infos.get(0));
    }
}
