package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.funstill.netty.chat.R;
import com.funstill.netty.chat.model.chat.ChatDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;

public class CustomLayoutDialogsActivity extends DialogsActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomLayoutDialogsActivity.class));
    }

    private DialogsList dialogsList;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_dialogs);
        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
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
        DefaultMessagesActivity.open(this);
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, super.imageLoader);
        //TODO 查询历史会话
        super.dialogsAdapter.setItems(new ArrayList<ChatDialog>());

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }
}
