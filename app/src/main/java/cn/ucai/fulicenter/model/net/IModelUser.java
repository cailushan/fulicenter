package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.User;


/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelUser {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);

    void register(Context context, String username, String usernick, String password, OnCompleteListener<String> listener);
}
