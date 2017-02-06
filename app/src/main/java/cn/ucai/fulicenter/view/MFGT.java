package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.controller.activity.CategoryChildActivity;
import cn.ucai.fulicenter.controller.activity.CollectActivity;
import cn.ucai.fulicenter.controller.activity.GoodsDetailActivity;
import cn.ucai.fulicenter.controller.activity.LoginActivity;
import cn.ucai.fulicenter.controller.activity.OrderActivity;
import cn.ucai.fulicenter.controller.activity.RegisterActivity;
import cn.ucai.fulicenter.controller.activity.SettingsActivity;
import cn.ucai.fulicenter.controller.activity.UpdateNickActivity;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void startActivity(Activity context, Class<?> clz) {
        context.startActivity(new Intent(context, clz));
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoBoutiqueChild(Context context, BoutiqueBean boutiqueBean) {
        Intent intent = new Intent(context, BoutiqueChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, boutiqueBean.getId());
        intent.putExtra(I.Boutique.TITLE, boutiqueBean.getTitle());
        startActivity((Activity) context, intent);
    }

    public static void gotoGoodsDetail(Context context, int goodsId) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId);
        startActivity((Activity) context, intent);
    }

    public static void gotoCategoryChild(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent(context, CategoryChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, catId);
        intent.putExtra(I.CategoryGroup.NAME, groupName);
        intent.putExtra(I.CategoryChild.DATA, list);
        startActivity((Activity) context, intent);
    }

    public static void gotoLogin(Activity context) {
        context.startActivityForResult(new Intent(context, LoginActivity.class), I.REQUEST_CODE_LOGIN);
    }

    public static void gotoLogin(Activity context, int code) {
        context.startActivityForResult(new Intent(context, LoginActivity.class), code);
    }

    public static void gotoRegister(LoginActivity registerActivity) {
        startActivity(registerActivity, RegisterActivity.class);
    }

    public static void gotoSettings(Activity activity) {
        startActivity(activity, SettingsActivity.class);
    }

    public static void gotoUpdateNick(Activity activity) {
        activity.startActivityForResult(new Intent(activity, UpdateNickActivity.class), I.REQUEST_CODE_NICK);
    }

    public static void gotoCollect(FragmentActivity activity) {
        startActivity(activity, CollectActivity.class);
    }

    public static void gotoOrder(FragmentActivity activity, int payPrice) {
        Intent intent = new Intent(activity, OrderActivity.class);
        intent.putExtra(I.Cart.PAY_PRICE, payPrice);
        startActivity(activity, intent);

    }
}
