package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.funstill.generator.greendao.dao.DaoSession;
import com.funstill.generator.greendao.dao.DialogDataDao;
import com.funstill.generator.greendao.dao.MessageDataDao;
import com.funstill.generator.greendao.dao.UserDataDao;
import com.funstill.generator.greendao.entity.DialogData;
import com.funstill.generator.greendao.entity.MessageData;
import com.funstill.generator.greendao.entity.UserData;
import com.funstill.netty.chat.R;
import com.funstill.netty.chat.api.FriendApi;
import com.funstill.netty.chat.api.UserApi;
import com.funstill.netty.chat.config.ServerConfig;
import com.funstill.netty.chat.model.User;
import com.funstill.netty.chat.model.chat.ChatDialog;
import com.funstill.netty.chat.model.chat.ChatFriend;
import com.funstill.netty.chat.model.chat.ChatMessage;
import com.funstill.netty.chat.model.chat.ChatUser;
import com.funstill.netty.chat.utils.RetrofitUtil;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import org.greenrobot.greendao.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

public class DefaultDialogsActivity extends BaseDialogsActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, DefaultDialogsActivity.class));
    }

    private DialogsList dialogsList;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_dialogs);
        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        setTitle("会话列表");
        initAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.dialog_actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_friend:
                FriendActivity.open(this);
                break;
        }
        return true;
    }

    @Override
    public void onDialogClick(ChatDialog dialog) {
        //打开聊天会话 //TODO dialog id==null空指针异常
        DefaultMessagesActivity.open(this,Long.valueOf(dialog.getId()),null);
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, super.imageLoader);
        // 查询本地历史会话
        DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
        DialogDataDao dialogDataDao = daoSession.getDialogDataDao();
        UserDataDao userDataDao = daoSession.getUserDataDao();
        MessageDataDao messageDataDao = daoSession.getMessageDataDao();

        Query<DialogData> dialogDataQuery = dialogDataDao.queryBuilder().orderDesc(DialogDataDao.Properties.UpdateDate).build();
        List<DialogData> dialogDataList = dialogDataQuery.list();
        List<ChatDialog> dialogList = new ArrayList<>();
        for (DialogData dialog : dialogDataList) {
            //1查询参与会话的用户
            String[] userIds = dialog.getUsers().split(",");
            ArrayList<User> users = new ArrayList<>(userIds.length);
            for (String userId : userIds) {
                User user = getUser(Long.valueOf(userId));
                users.add(user);
            }
            ChatDialog chatDialog = new ChatDialog();
            chatDialog.setUsers(users);//用户头像
            //2查询最近一条消息
            MessageData messageData = messageDataDao.queryBuilder()
                    .where(MessageDataDao.Properties.DialogId.eq(dialog.getId()))
                    .orderDesc(MessageDataDao.Properties.UpdateDate).limit(1)
                    .build().unique();
            if(messageData!=null){
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setCreatedAt(messageData.getCreateDate());
                chatMessage.setText(messageData.getContent());
                chatMessage.setUser(getUser(messageData.getSenderId()));//TODO 改ui换成名字
                chatDialog.setLastMessage(chatMessage);
            }else{
                //TODO 下面为测试用代码
                Log.e("","异常,不应该出现这种情况");
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setCreatedAt(new Date());
                chatMessage.setText("异常消息");
                chatMessage.setUser(getUser(messageData.getSenderId()));
                chatDialog.setLastMessage(chatMessage);
            }
            dialogList.add(chatDialog);

        }

        super.dialogsAdapter.setItems(dialogList);

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }

    private User getUser(Long userId) {
        //先从本地数据库查
        DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
        UserDataDao userDataDao = daoSession.getUserDataDao();
        UserData userData = userDataDao.queryBuilder()
                .where(UserDataDao.Properties.UserId.eq(userId)).build().unique();
        //查不到再从服务器查
        if (userData == null) {
            userData = new UserData();
            UserApi userApi = RetrofitUtil.retrofit(ServerConfig.WEB_URL).create(UserApi.class);
            FriendApi friendApi = RetrofitUtil.retrofit(ServerConfig.WEB_URL).create(FriendApi.class);
            Call<ChatUser> call = userApi.getUser(userId);
            Call<ChatFriend> callFriend = friendApi.getFriendDetail(Long.valueOf(DefaultMessagesActivity.senderId), userId);
            try {
                ChatUser chatUser = call.execute().body();
                ChatFriend chatFriend = callFriend.execute().body();
                userData.setAvatar(chatUser.getAvatar());
                if (chatFriend != null) {
                    userData.setNickname(chatFriend.getNickname());
                }
                userData.setUserId(chatUser.getUserId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //将查询结果保存到本机
            userDataDao.insert(userData);
        }
        //返回查询结果
        User user = new User(userId + "", userData.getNickname(), userData.getAvatar(), true);
        return user;
    }
}
