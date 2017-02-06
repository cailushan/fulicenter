package cn.ucai.fulicenter.controller.adapter;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;

public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CartBean> mList;
    IModelUser model;
    User user;

    public CartAdapter(Context context, ArrayList<CartBean> mList) {
        mContext = context;
        this.mList = mList;
        model = new ModelUser();
        user = FuLiCenterApplication.getUser();
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder vh = new CartViewHolder(View.inflate(mContext, R.layout.item_cart, null));
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartViewHolder vh = (CartViewHolder) holder;
        vh.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<CartBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chkSelect)
        CheckBox mchkSelect;
        @BindView(R.id.ivGoodsThumb)
        ImageView mivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mtvGoodsName;
        @BindView(R.id.ivAddCart)
        ImageView mivAddCart;
        @BindView(R.id.tvCartCount)
        TextView mtvCartCount;
        @BindView(R.id.ivDeleCart)
        ImageView mivReduceCart;
        @BindView(R.id.tvGoodsPrice)
        TextView mtvGoodsPrice;
        int listPosition;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            listPosition = position;
            GoodsDetailsBean detailsBean = mList.get(position).getGoods();
            if (detailsBean != null) {
                ImageLoader.downloadImg(mContext, mivGoodsThumb, detailsBean.getGoodsThumb());
                mtvGoodsName.setText(detailsBean.getGoodsName());
                mtvGoodsPrice.setText(detailsBean.getCurrencyPrice());
            }
            mtvCartCount.setText("(" + mList.get(position).getCount() + ")");
            mchkSelect.setChecked(mList.get(listPosition).isChecked());
        }

        @OnCheckedChanged(R.id.chkSelect)
        public void checkListener(boolean checked) {
            mList.get(listPosition).setChecked(checked);
            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
        }

        @OnClick(R.id.ivAddCart)
        public void addCart() {
            model.updateCart(mContext, I.ACTION_CART_ADD, user.getMuserName(),
                    mList.get(listPosition).getGoodsId(), 1, mList.get(listPosition).getId(),
                    new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                mList.get(listPosition).setCount(mList.get(listPosition).getCount() + 1);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
        }

        @OnClick(R.id.ivDeleCart)
        public void delCart() {
            final int count = mList.get(listPosition).getCount();
            int action = I.ACTION_CART_UPDATE;
            if (count > 1) {
                //update
                action = I.ACTION_CART_UPDATE;
            } else {
                //del
                action = I.ACTION_CART_DEL;
            }
            model.updateCart(mContext, action, user.getMuserName(),
                    mList.get(listPosition).getGoodsId(), count - 1, mList.get(listPosition).getId(),
                    new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                if (count <= 1) {
                                    mList.remove(listPosition);
                                } else {
                                    mList.get(listPosition).setCount(count - 1);
                                }
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
        }
    }
}






