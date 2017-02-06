package cn.ucai.fulicenter.application;

import android.app.Application;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;
//    private static HashMap<Integer, CartBean> myCartList = new HashMap<>();
//
//    public static HashMap<Integer, CartBean> getMyCartList() {
//        return myCartList;
//    }
//
//    public static void setMyCartList(HashMap<Integer, CartBean> myCartList) {
//        FuLiCenterApplication.myCartList = myCartList;
//    }

    public static FuLiCenterApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ShareSDK.initSDK(this);
    }


    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }
}
