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
        DefaultMessagesActivity.open(this,Long.valueOf(dialog.getId()));
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
                UserData userData = userDataDao.queryBuilder().where(UserDataDao.Properties.UserId.eq(userId)).build().unique();
                User user = new User(userData.getUserId().toString(), userData.getAvatar(), userData.getNickname(), true);
                users.add(user);
            }
            ChatDialog chatDialog = new ChatDialog();
            chatDialog.setUsers(users);
            //2查询最近一条消息
            MessageData messageData = messageDataDao.queryBuilder()
                    .where(MessageDataDao.Properties.DialogId.eq(dialog.getId()))
                    .orderDesc(MessageDataDao.Properties.UpdateDate).limit(1)
                    .build().unique();
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setCreatedAt(messageData.getCreateDate());
            chatMessage.setText(messageData.getContent());
            chatDialog.setLastMessage(chatMessage);
            dialogList.add(chatDialog);

        }

        super.dialogsAdapter.setItems(dialogList);

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }
}