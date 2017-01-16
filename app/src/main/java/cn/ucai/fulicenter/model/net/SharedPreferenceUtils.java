package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class SharedPreferenceUtils {
    private static final String SHARE_PREFERENCE_NAME = "cn.ucai.fulicenter_user";
    private static final String SHARE_PREFERENCE_NAME_USERNAME = "cn.ucai.fulicenter_user_username";
    private static SharedPreferenceUtils instance;
    private static SharedPreferences preferences;

    public SharedPreferenceUtils(Context context) {
        preferences = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceUtils(context);
        }
        return instance;
    }

    public static void saveUser(String username) {
        preferences.edit().putString(SHARE_PREFERENCE_NAME_USERNAME, username).commit();

    }

    public static String getUser() {
        return preferences.getString(SHARE_PREFERENCE_NAME_USERNAME, null);
    }

}
