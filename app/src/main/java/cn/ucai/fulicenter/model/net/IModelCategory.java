package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;


/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelCategory {
    void downData(Context context, OnCompleteListener<CategoryGroupBean[]> listener);

    void downData(Context context, int parentId, OnCompleteListener<CategoryChildBean[]> listener);
}
