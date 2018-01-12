package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.funstill.netty.chat.model.User;
import com.funstill.netty.chat.model.chat.ChatDialog;
import com.funstill.netty.chat.model.chat.ChatMessage;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

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
        //打开聊天会话
        //查出好友id
        if (dialog.getId() == null) {
            throw new IllegalArgumentException("dialogId不能为空");
        }
        DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
        MessageDataDao messageDataDao = daoSession.getMessageDataDao();
        MessageData messageData = messageDataDao.queryBuilder()
                .where(MessageDataDao.Properties.DialogId.eq(dialog.getId())).limit(1).build().unique();
        DefaultMessagesActivity.open(this, Long.valueOf(dialog.getId()), messageData.getSenderId());
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(R.layout.item_dialog, super.imageLoader);
        // 查询本地历史会话
        DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
        DialogDataDao dialogDataDao = daoSession.getDialogDataDao();
        MessageDataDao messageDataDao = daoSession.getMessageDataDao();

        Query<DialogData> dialogDataQuery = dialogDataDao.queryBuilder().orderDesc(DialogDataDao.Properties.UpdateDate).build();
        List<DialogData> dialogDataList = dialogDataQuery.list();
        List<ChatDialog> dialogList = new ArrayList<>();
        for (DialogData dialogData : dialogDataList) {
            ChatDialog chatDialog = new ChatDialog();
            chatDialog.setId(dialogData.getId() + "");
            String[] userIds = dialogData.getUsers().split(",");
            if(userIds.length>1){//群聊
                //TODO 处理群聊头像,群名称
            }else {//私聊
                User friend=getUser(Long.valueOf(userIds[0]));
                chatDialog.setDialogPhoto(friend.getAvatar());
                chatDialog.setDialogName(friend.getName());
            }
            //2查询最近一条消息
            MessageData messageData = messageDataDao.queryBuilder()
                    .where(MessageDataDao.Properties.DialogId.eq(dialogData.getId()))
                    .orderDesc(MessageDataDao.Properties.CreateDate).limit(1)
                    .build().unique();
            ChatMessage chatMessage= new ChatMessage();
            chatMessage.setCreatedAt(messageData.getCreateDate());
            chatMessage.setText(messageData.getContent());
            chatDialog.setLastMessage(chatMessage);
            chatDialog.setUnreadCount(3);
            dialogList.add(chatDialog);

        }

        super.dialogsAdapter.setItems(dialogList);

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }

    private User getUser(Long userId) {
        DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
        UserDataDao userDataDao = daoSession.getUserDataDao();
        UserData userData = userDataDao.queryBuilder()
                .where(UserDataDao.Properties.UserId.eq(userId)).build().unique();
        if (userData == null) {
            userData = new UserData();
            userData.setNickname("没查到..bug");
            userData.setAvatar("http://i.imgur.com/pv1tBmT.png");
        }
        //返回查询结果
        User user = new User(userId + "", userData.getNickname(), userData.getAvatar(), true);
        return user;
    }
}
