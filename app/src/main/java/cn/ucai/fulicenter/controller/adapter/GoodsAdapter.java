package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.GoodsDetailActivity;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    String footer;
    boolean isMore;

    public String getFooter() {
        return isMore ? "加载更多数据... " : "没有更多数据...";
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }


    public GoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == I.TYPE_ITEM) {
            RecyclerView.ViewHolder holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
            return holder;
        } else {
            RecyclerView.ViewHolder holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ParentHolder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) ParentHolder;
            vh.tvFooter.setText(getFooter());
            return;
        }

        GoodsViewHolder vh = (GoodsViewHolder) ParentHolder;
        ImageLoader.downloadImg(mContext, vh.mIvGoodsThumb, mList.get(position).getGoodsThumb());
        NewGoodsBean goods = mList.get(position);
        vh.mTvGoodsName.setText(mList.get(position).getGoodsName());
        vh.mTvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
        vh.mLayoutGoods.setTag(goods.getGoodsId());

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView mTvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout mLayoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick() {
            int goodsId = (int) mLayoutGoods.getTag();
            mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
                    .putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId));
        }
    }

}
