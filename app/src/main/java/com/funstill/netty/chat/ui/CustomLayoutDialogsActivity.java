package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.funstill.netty.chat.R;
import com.funstill.netty.chat.fixtures.DialogsFixtures;
import com.funstill.netty.chat.model.Dialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

public class CustomLayoutDialogsActivity extends DialogsActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomLayoutDialogsActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_dialogs);

        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        initAdapter();
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        DefaultMessagesActivity.open(this);
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, super.imageLoader);
        super.dialogsAdapter.setItems(DialogsFixtures.getDialogs());

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }
}
