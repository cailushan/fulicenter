package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;


/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelGoods {
    void downData(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener);

    void isCollect(Context context, int goodsId, String username, OnCompleteListener<MessageBean> listener);
}
