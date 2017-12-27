package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.funstill.netty.chat.R;
import com.funstill.netty.chat.adapter.FriendRecyclerAdapter;
import com.funstill.netty.chat.model.chat.ChatFriend;
import com.funstill.netty.chat.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liukaiyang on 2017/12/27.
 */

public class FriendActivity extends AppCompatActivity {
    public static void open(Context context) {
        context.startActivity(new Intent(context, FriendActivity.class));
    }
    private String[] text = new String[]{
            "面对大河我无限惭愧",
            "我年华虚度 空有一身疲倦",
            "和所有以梦为马的诗人一样",
            "岁月易逝 一滴不剩",
            "我不得不和烈士和小丑走在同一道路上",
            "万人都要将火熄灭",
            "我一人独将此火高高举起",
            "他什么都没有留下来",
            "随着海水的颠簸",
            "去往未知的地域",
            "我是一只船",
    };

    private List<ChatFriend> itemList;

    private RecyclerView list;
    private MyAdapter adapter;
    private ChatFriend itemD ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        initView();
        initData();
    }

    //初始化布局
    private void initView() {
        list = (RecyclerView) findViewById(R.id.friend_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setHasFixedSize(true);
    }

    private void initData() {
        itemList = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            itemD= new ChatFriend(text[i]);
            itemList.add(itemD);
        }
        adapter = new MyAdapter(this, itemList);
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new FriendRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(FriendActivity.this, "点击事件被触发,位置：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemLongClickListener(new FriendRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position) {
                Toast.makeText(FriendActivity.this,"长按事件被触发,位置："+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyAdapter extends FriendRecyclerAdapter<ChatFriend> {

        public MyAdapter(Context ctx, List<ChatFriend> l) {
            super(ctx, l);
        }

        @Override
        protected void add(int position) {
            Random random=new Random();
            int i = random.nextInt(3);
            ChatFriend item=new ChatFriend(text[i]);
            adapter.addData(position,item);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "添加好友").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.getItem(0).setIcon(R.drawable.ic_action_add);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //目前只有一个button,所以暂不判断
        //TODO 跳转添加好友界面
        AppUtils.showToast(this,"添加好友",false);
        return super.onOptionsItemSelected(item);
    }

}