package com.fmj.fmj.activity;

import android.app.FragmentBreadCrumbs;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private MemberInfoDao userDao;
    private GoodsInfoDao goodsInfoDao;

    private RecyclerView rv_goods;

    private List<GoodsInfo> goodsInfoList;

    private GoodAdapter goodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Immersive.setContentView(this, R.layout.activity_main, R.color.colorAccent, R.color.colorAccent, false, false);
        title = findViewById(R.id.title);
        title.setText("尊敬的" + SPUtils.getString(this, "name", "") + ",欢迎光临!");

        MyApp myApp = (MyApp) getApplication();
        DaoSession daoSession = myApp.getDaoSession();
        userDao = daoSession.getMemberInfoDao();
        goodsInfoDao = daoSession.getGoodsInfoDao();

        goodsInfoList = new ArrayList<>();
        goodsInfoList.addAll(goodsInfoDao.loadAll());


        rv_goods = findViewById(R.id.rv_goods);
        rv_goods.setLayoutManager(new GridLayoutManager(this, 2));
        goodAdapter = new GoodAdapter(R.layout.item_goods ,goodsInfoList) ;
        rv_goods.setAdapter(goodAdapter);

        goodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (goodsInfoList.get(position).getNum()==0){
                    Toast.makeText(MainActivity.this ,"没有库存,无法购买!",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this , OrderActivity.class);
                    intent.putExtra("good" ,goodsInfoList.get(position).getImageName());
                    intent.putExtra("price",goodsInfoList.get(position).getPrice());
                    intent.putExtra("num",goodsInfoList.get(position).getNum());
                    intent.putExtra("id",goodsInfoList.get(position).getId());
                    startActivity(intent);
                }

            }
        });
    }
}
