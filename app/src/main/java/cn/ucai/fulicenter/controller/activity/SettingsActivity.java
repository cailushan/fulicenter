package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharedPreferenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.MFGT;

public class SettingsActivity extends AppCompatActivity {
    OnSetAvatarListener onSetAvatarListener;
    IModelUser model;


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
        MFGT.gotoUpdateNick(this);
    }

    @OnClick(R.id.ivBack)
    public void ivback() {
        MFGT.finish(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_NICK) {
            tvUserNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        } else if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        }
        onSetAvatarListener.setAvatar(requestCode, data, ivUserAvatar);
    }

    private void uploadAvatar() {
        User user = FuLiCenterApplication.getUser();
        final ProgressDialog dialog = new ProgressDialog(this);
        model = new ModelUser();
        dialog.setMessage(getString(R.string.update_user_avatar));
        dialog.show();
        File file = null;
        file = new File(String.valueOf(OnSetAvatarListener.getAvatarFile(this,
                I.AVATAR_TYPE_USER_PATH + "/" + user.getMuserName() + user.getMavatarSuffix())));
        model.uploadAvatar(this,
                user.getMuserName(), file, new OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        int msg = R.string.update_user_avatar_fail;
                        if (s != null) {
                            Result result = ResultUtils.getListResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    msg = R.string.update_user_avatar_success;
                                }
                            }
                        }
                        CommonUtils.showLongToast(msg);
                        dialog.dismiss();

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    @OnClick(R.id.layout_user_name)
    public void onClickName() {
        CommonUtils.showLongToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.user_head_avatar)
    public void onClickAvatar() {
        onSetAvatarListener = new OnSetAvatarListener(this,
                R.id.user_head_avatar,
                FuLiCenterApplication.getUser().getMuserName()
                , I.AVATAR_TYPE_USER_PATH);
    }
}
