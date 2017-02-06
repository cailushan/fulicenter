package cn.ucai.fulicenter.controller.adapter;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    int listPosition;
    IModelUser model;
    User user;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        this.mList = list;
        model = new ModelUser();
        user = FuLiCenterApplication.getUser();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder =
                new CartViewHolder(View.inflate(mContext, R.layout.item_cart, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ParentHolder, final int position) {
        listPosition = position;
        CartViewHolder vh = (CartViewHolder) ParentHolder;
        CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, vh.mivGoodsThumb, goods.getGoodsThumb());
            vh.mtvGoodsName.setText(goods.getGoodsName());
            vh.mtvGoodsPrice.setText(goods.getCurrencyPrice());
        }
        vh.mtvCartCount.setText("(" + cartBean.getCount() + ")");
        vh.mchkSelect.setChecked(mList.get(listPosition).isChecked());
        vh.mchkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               mList.get(listPosition).setChecked(isChecked);
               Log.e("adapter","checkListener sendBroadcast");
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
            }
        });
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

    @OnClick({R.id.ivAddCart, R.id.ivReduceCart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAddCart:
                model.updateCart(mContext, I.ACTION_CART_ADD, user.getMuserName(),
                        mList.get(listPosition).getGoodsId(), 1, mList.get(listPosition).getId(), new OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result != null && result.isSuccess()) {
                                    mList.get(listPosition).setCount(mList.get(listPosition).getCount() + 1);
                                    mContext.sendBroadcast(new Intent(I.REQUEST_UPDATE_CART));
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                break;
            case R.id.ivReduceCart:
                final int count = mList.get(listPosition).getCount();
                int action = I.ACTION_CART_UPDATE;
                if (count > 1) {
                    // update
                    action = I.ACTION_CART_UPDATE;
                } else {
                    // del
                    action = I.ACTION_CART_DEL;
                }
                model.updateCart(mContext, action, user.getMuserName(), mList.get(listPosition).getGoodsId(), count - 1, mList.get(listPosition).getId()
                        , new OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result != null && result.isSuccess()) {
                                    if (count <= 1) {
                                        mList.remove(listPosition);
                                    } else {
                                        mList.get(listPosition).setCount(count - 1);
                                    }
                                    mContext.sendBroadcast(new Intent(I.REQUEST_UPDATE_CART));
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                break;
        }
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.ivReduceCart)
        ImageView mivReduceCart;
        @BindView(R.id.tvGoodsPrice)
        TextView mtvGoodsPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @OnCheckedChanged(R.id.chkSelect)
    public void checkListener(boolean checked) {
        mList.get(listPosition).setChecked(checked);
        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
    }

}
