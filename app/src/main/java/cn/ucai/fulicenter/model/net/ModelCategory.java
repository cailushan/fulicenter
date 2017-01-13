package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class ModelCategory implements IModelCategory {
    @Override
    public void downData(Context context, OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);

    }

    @Override
    public void downData(Context context, int parentId, OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, String.valueOf(parentId))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);

    }
}
