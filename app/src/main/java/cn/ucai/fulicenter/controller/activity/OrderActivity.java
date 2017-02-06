package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.view.MFGT;

public class OrderActivity extends AppCompatActivity implements PaymentHandler {
    private static String URL = "http://218.244.151.190/demo/charge";
    int payPrice;
    @BindView(R.id.tv_count)
    TextView mtvCount;
    @BindView(R.id.etReceiverName)
    EditText metReceiverName;
    @BindView(R.id.etPhone)
    EditText metPhone;
    @BindView(R.id.spinArea)
    Spinner mspinArea;
    @BindView(R.id.etAddress)
    EditText metAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        payPrice = getIntent().getIntExtra(I.Cart.PAY_PRICE, 0);
        initPingPP();
        setView();
    }

    // 设置支付渠道及参数
    private void initPingPP() {
        // 设置要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "bfb", "jdpay_wap"});
        //提交数据的格式，默认格式为json
        //PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";
        //是否开启日志
        PingppLog.DEBUG = true;
    }

    private void setView() {
        mtvCount.setText("合计：￥" + payPrice);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick(R.id.btnBuy)
    public void checkOrder() {
        String receiveName = metReceiverName.getText().toString();
        if (TextUtils.isEmpty(receiveName)) {
            metReceiverName.setError("收货人姓名不能为空");
            metReceiverName.requestFocus();
            return;
        }
        String phone = metPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            metPhone.setError("手机号码不能为空");
            metPhone.requestFocus();
            return;
        }
        if (!phone.matches("[\\d]{11}")) {
            metPhone.setError("手机号码格式错误");
            metPhone.requestFocus();
            return;
        }
        String area = mspinArea.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(this, "收货地区不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = metAddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            metAddress.setError("街道地址不能为空");
            metAddress.requestFocus();
            return;
        }
        showPay();
    }

    // 发起支付
    private void showPay() {
        // 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        // 计算总金额（以分为单位）
        int amount = payPrice * 100;
        JSONArray billList = new JSONArray();
//        for (Good good : mList) {
//            amount += good.getPrice() * good.getCount() * 100;
//            billList.put(good.getName() + " x " + good.getCount());
//        }
        // 构建账单json对象
        JSONObject bill = new JSONObject();

        // 自定义的额外信息 选填
        JSONObject extras = new JSONObject();
        try {
            extras.put("extra1", "extra1");
            extras.put("extra2", "extra2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            bill.put("order_no", orderNo);
            bill.put("amount", amount);
            bill.put("extras", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //壹收款: 创建支付通道
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), null, URL, new PaymentHandler() {

            // 返回支付结果
            // @param data

            @Override
            public void handlePaymentResult(Intent data) {
            }
        });
    }

    // 显示结果
    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {
            /**
             * code：支付结果码  -2:服务端错误、 -1：失败、 0：取消、1：成功
             * error_msg：支付结果信息
             */
            int code = data.getExtras().getInt("code");
            String errorMsg = data.getExtras().getString("error_msg");
            Log.e("main", "code=" + code);
        }
    }
}
