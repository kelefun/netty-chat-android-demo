package com.funstill.netty.chat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.funstill.netty.chat.model.chat.ChatDialog;
import com.funstill.netty.chat.utils.AppUtils;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

/*
 * Created by troy379 on 05.04.17.
 */
public abstract class DialogsActivity extends AppCompatActivity
        implements DialogsListAdapter.OnDialogClickListener<ChatDialog>,
        DialogsListAdapter.OnDialogLongClickListener<ChatDialog> {

    protected ImageLoader imageLoader;
    protected DialogsListAdapter<ChatDialog> dialogsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Glide.with(DialogsActivity.this).load(url).into(imageView);
            }
        };
    }

    @Override
    public void onDialogLongClick(ChatDialog dialog) {
        AppUtils.showToast(
                this,
                dialog.getDialogName(),
                false);
    }
}
