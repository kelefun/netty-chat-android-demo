package com.funstill.netty.chat.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.funstill.netty.chat.R;
import com.funstill.netty.chat.config.ClientType;
import com.funstill.netty.chat.config.Const;
import com.funstill.netty.chat.model.enums.ProtoTypeEnum;
import com.funstill.netty.chat.model.enums.ResponseEnum;
import com.funstill.netty.chat.netty.NettyClientHandler;
import com.funstill.netty.chat.observer.ProtoMsgObserver;
import com.funstill.netty.chat.permission.MPermission;
import com.funstill.netty.chat.permission.annotation.OnMPermissionDenied;
import com.funstill.netty.chat.permission.annotation.OnMPermissionNeverAskAgain;
import com.funstill.netty.chat.protobuf.AuthMsg;
import com.funstill.netty.chat.protobuf.AuthResponseMsg;
import com.funstill.netty.chat.protobuf.ProtoMsg;
import com.funstill.netty.chat.utils.AppUtils;
import com.funstill.netty.chat.widget.ClearWriteEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.channel.Channel;


public class LoginActivity extends FragmentActivity {

    private static final String KICK_OUT = "KICK_OUT";
    private final int BASIC_PERMISSION_REQUEST_CODE = 110;
    private ProtoMsgObserver loginObserver = null;

    private ImageView mImg_Background;
    private ClearWriteEditText mPhoneEdit, mPasswordEdit;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(KICK_OUT, kickOut);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        requestBasicPermission();
        initView();
        initObserver();
    }

    //netty登录响应观察
    private void initObserver() {
        if (loginObserver == null) {
            loginObserver = new ProtoMsgObserver() {
                @Override
                public void handleProtoMsg(Channel channel, ProtoMsg.Content msg) {
                    if (msg.getProtoType() == ProtoTypeEnum.LOGIN_RES_MSG.getIndex()) {//登录响应信息
                        AuthResponseMsg.Content res;
                        try {
                            res = AuthResponseMsg.Content.parseFrom(msg.getContent());
                            if (res.getCode() == ResponseEnum.SUCCESS.getCode()) {
                                //如果登录成功保存登录信息
                                saveMyInfo(res.getExtra());
                                //跳转
                                DefaultMessagesActivity.senderId = res.getUserId();
                                DefaultDialogsActivity.open(LoginActivity.this);
                                finish();//结束此页面
                            } else {
                                AppUtils.showToast(LoginActivity.this, res.getMsg(), false);
                            }
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                            AppUtils.showToast(LoginActivity.this, "登录失败", false);
                        }
                    }
                }
            };
        }
        NettyClientHandler.msgObservable.addObserver(loginObserver);
    }

    private void saveMyInfo(String userJson) {
        JsonObject returnData = new JsonParser().parse(userJson).getAsJsonObject();
        editor.putString(Const.LOGIN_USERNAME, mPhoneEdit.getText().toString());
        editor.putString(Const.LOGIN_USER_ID, returnData.get("userId").toString());
        editor.putString(Const.LOGIN_AVATAR, returnData.get("avatar").toString());
        editor.putString(Const.LOGIN_NICKNAME, returnData.get("nickname").toString());
        editor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NettyClientHandler.msgObservable.deleteObserver(loginObserver);

    }

    private void initView() {
        mPhoneEdit = (ClearWriteEditText) findViewById(R.id.de_login_phone);
        mPasswordEdit = (ClearWriteEditText) findViewById(R.id.de_login_password);
        Button mConfirm = (Button) findViewById(R.id.de_login_sign);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneString = mPhoneEdit.getText().toString().trim();
                String passwordString = mPasswordEdit.getText().toString().trim();
                if (TextUtils.isEmpty(phoneString)) {
                    AppUtils.showToast(LoginActivity.this, "账号不能为空", false);
                    mPhoneEdit.setShakeAnimation();
                    return;
                }

                if (TextUtils.isEmpty(passwordString)) {
                    AppUtils.showToast(LoginActivity.this, "密码不能为空", false);
                    mPasswordEdit.setShakeAnimation();
                    return;
                }
                //发送登录消息
                AuthMsg.Content.Builder authBuilder = AuthMsg.Content.newBuilder();
                authBuilder.setUsername(mPhoneEdit.getText().toString());
                authBuilder.setPassword(mPasswordEdit.getText().toString());
                ProtoMsg.Content.Builder msgBuilder = ProtoMsg.Content.newBuilder();
                msgBuilder.setProtoType(ProtoTypeEnum.LOGIN_MSG.getIndex());
                msgBuilder.setContent(authBuilder.build().toByteString());
                if (NettyClientHandler.channel != null && NettyClientHandler.channel.isActive()) {
                    NettyClientHandler.channel.writeAndFlush(msgBuilder.build());
                } else {
                    AppUtils.showToast(LoginActivity.this, "与服务器连接异常", false);
                }

            }
        });
        mImg_Background = (ImageView) findViewById(R.id.de_img_backgroud);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.translate_anim);
                mImg_Background.startAnimation(animation);
            }
        }, 1000);
        mPhoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    AppUtils.onInactive(getApplicationContext(), mPhoneEdit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String oldPhone = sp.getString(Const.LOGIN_USERNAME, "");

        if (!TextUtils.isEmpty(oldPhone)) {
            mPhoneEdit.setText(oldPhone);
        }
    }

    /**
     * 基本权限管理
     */

    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private void requestBasicPermission() {
        MPermission.with(LoginActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

//    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
//    public void onBasicPermissionSuccess() {
//        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
//    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
    }

    private void onParseIntent() {
        if (getIntent().getBooleanExtra(KICK_OUT, false)) {
            //TODO 需要正确获取
            int type = 0;
            String client;
            switch (type) {
                case ClientType.Web:
                    client = "网页端";
                    break;
                case ClientType.Windows:
                case ClientType.MAC:
                    client = "电脑端";
                    break;
                case ClientType.REST:
                    client = "服务端";
                    break;
                default:
                    client = "移动端";
                    break;
            }
            //TODO 通知
//            EasyAlertDialogHelper.showOneButtonDiolag(LoginActivity.this, getString(R.string.kickout_notify),
//                    String.format(getString(R.string.kickout_content), client), getString(R.string.ok), true, null);
        }
    }

}
