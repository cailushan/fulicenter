package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

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

}
