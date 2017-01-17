package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.controller.fragment.PersonalCenterFragment;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.SharedPreferenceUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.MFGT;

public class SettingsActivity extends AppCompatActivity {


    @BindView(R.id.ivUserAvatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserNick)
    TextView tvUserNick;
    @BindView(R.id.layout_user_nickname)
    RelativeLayout layoutUserNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin(this);
        }

    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, ivUserAvatar);
        tvUserName.setText(user.getMuserName());
        tvUserNick.setText(user.getMuserNick());
    }

    @OnClick(R.id.btBack)
    public void btback() {
        FuLiCenterApplication.setUser(null);
        SharedPreferenceUtils.getInstance(this).removeUser();
        MFGT.gotoLogin(this);
        finish();
    }

    @OnClick(R.id.layout_user_nickname)
    public void updateNick() {
        String nick = tvUserNick.getText().toString().trim();
        if (TextUtils.isEmpty(nick)) {

        }
    }

    @OnClick(R.id.ivBack)
    public void ivback() {
        MFGT.finish(this);
    }
}
