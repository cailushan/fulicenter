package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;


/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelUser {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);

    void register(Context context, String username, String usernick, String password, OnCompleteListener<String> listener);

    void updateNick(Context context, String username, String userNick, OnCompleteListener<String> listener);

    void uploadAvatar(Context context, String username, File file, OnCompleteListener<String> listener);

    void collectCount(Context context, String userName, OnCompleteListener<MessageBean> listener);

    void downloadCollect(Context context, String username, int pageId, OnCompleteListener<CollectBean[]> listener);

    void deleteCollects(Context context, String username, int goodsId, OnCompleteListener<MessageBean> listener);

    void getCart(Context context, String userName, OnCompleteListener<CartBean[]> listener);

    void updateCart(Context context, int action, String userName, int goodIs, int count, int cartId, OnCompleteListener<MessageBean> listener);


}
