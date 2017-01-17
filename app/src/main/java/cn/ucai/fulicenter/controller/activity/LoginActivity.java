package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharedPreferenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.MFGT;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnUrl)
    Button btnUrl;
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    IModelUser model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.btnLogin, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btnLogin:
                checkInput();
                break;
            case R.id.btnRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
        } else {
            login(userName, password);
        }
    }

    private void login(final String userName, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        model = new ModelUser();
        model.login(this, userName, password, new OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (s != null) {
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    User user = (User) result.getRetData();
                                    boolean saveUser = UserDao.getInstance().saveUser(user);
                                    Log.e("Login", "saveUser=" + saveUser);
                                    SharedPreferenceUtils.getInstance(LoginActivity.this).saveUser(user.getMuserName());
                                    FuLiCenterApplication.setUser(user);
                                    MFGT.finish(LoginActivity.this);
                                } else {
                                    if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                        CommonUtils.showLongToast(R.string.login_fail_unknow_user);
                                    }
                                    if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                        CommonUtils.showLongToast(R.string.login_fail_error_password);
                                    }
                                }
                            } else {
                                CommonUtils.showLongToast(R.string.login_fail);
                            }
                        } else {
                            CommonUtils.showLongToast(R.string.login_fail);
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        dialog.dismiss();
                        CommonUtils.showLongToast(R.string.login_fail);
                    }
                }

        );

    }

}
