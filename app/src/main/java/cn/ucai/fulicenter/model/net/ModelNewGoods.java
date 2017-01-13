package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class ModelNewGoods implements IModelNewGoods {
    @Override
    public void downData(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener) {
        if (catId == 0) {
            OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
            utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                    .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(catId))
                    .addParam(I.PAGE_ID, String.valueOf(pageId))
                    .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                    .targetClass(NewGoodsBean[].class)
                    .execute(listener);

        } else {
            OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
            utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                    .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(catId))
                    .addParam(I.PAGE_ID, String.valueOf(pageId))
                    .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                    .targetClass(NewGoodsBean[].class)
                    .execute(listener);
        }
    }
}
