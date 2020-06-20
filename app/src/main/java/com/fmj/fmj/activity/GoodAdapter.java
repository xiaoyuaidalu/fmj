package com.fmj.fmj.activity;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fmj.fmj.R;
import com.fmj.fmj.bean.GoodsInfo;

import java.util.List;

/**
 * @author: gaoxinyu
 * @date: 2020/6/20
 */
public class GoodAdapter extends BaseQuickAdapter<GoodsInfo, BaseViewHolder> {
    public GoodAdapter(int layoutResId, @Nullable List<GoodsInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsInfo item) {

        ImageView iv_good = helper.getView(R.id.iv_good);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_num = helper.getView(R.id.tv_num);
        int goodimageId = mContext.getResources().getIdentifier(item.getImageName(), "drawable",mContext.getPackageName());
        iv_good.setImageResource(goodimageId);

        tv_price.setText(item.getPrice()+".00元");
        tv_num.setText("库存: "+item.getNum()+"");
    }
}
