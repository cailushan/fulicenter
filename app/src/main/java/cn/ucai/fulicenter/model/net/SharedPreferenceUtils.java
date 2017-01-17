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
    private SharedPreferences.Editor mEditor;
    public static final String SHARE_KEY_USER_NAME = "share_key_user_name";

    public SharedPreferenceUtils(Context context) {
        preferences = context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = preferences.edit();
    }

    public static SharedPreferenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceUtils(context);
        }
        return instance;
    }

    public void saveUser(String username) {
        mEditor.putString(SHARE_KEY_USER_NAME, username);
        mEditor.commit();

    }

    public String getUser() {
        return preferences.getString(SHARE_KEY_USER_NAME, null);
    }

    public void removeUser() {
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }

}
