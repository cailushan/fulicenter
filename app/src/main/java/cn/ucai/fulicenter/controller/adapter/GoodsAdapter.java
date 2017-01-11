package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_GOODS = 1;
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    String footer;

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }

    boolean isMore;
    boolean isDragging;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GOODS) {
            RecyclerView.ViewHolder holder =
                    new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
            return holder;
        } else {
            RecyclerView.ViewHolder holder =
                    new FootsViewHolder(View.inflate(mContext, R.layout.item_footer, null));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ParentHolder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FootsViewHolder vh = (FootsViewHolder) ParentHolder;
            vh.mTvFooter.setText(getFooter());
            return;
        }

        GoodsViewHolder vh = (GoodsViewHolder) ParentHolder;
        ImageLoader.downloadImg(mContext, vh.mIvGoodsThumb, mList.get(position).getGoodsThumb());
        vh.mTvGoodsName.setText(mList.get(position).getGoodsName());
        vh.mTvGoodsPrice.setText(mList.get(position).getCurrencyPrice());

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_GOODS;
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

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView mTvGoodsPrice;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FootsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_footer)
        RelativeLayout layoutFooter;
        @BindView(R.id.tvfooter)
        TextView mTvFooter;

        FootsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
