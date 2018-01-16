package com.funstill.netty.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.funstill.generator.greendao.dao.DaoSession;
import com.funstill.generator.greendao.dao.DialogDataDao;
import com.funstill.generator.greendao.dao.MessageDataDao;
import com.funstill.generator.greendao.entity.DialogData;
import com.funstill.generator.greendao.entity.MessageData;
import com.funstill.netty.chat.R;
import com.funstill.netty.chat.adapter.FriendRecyclerAdapter;
import com.funstill.netty.chat.api.FriendApi;
import com.funstill.netty.chat.config.ServerConst;
import com.funstill.netty.chat.model.chat.ChatFriend;
import com.funstill.netty.chat.model.enums.DialogTypeEnum;
import com.funstill.netty.chat.utils.AppUtils;
import com.funstill.netty.chat.utils.RetrofitUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liukaiyang on 2017/12/27.
 */

public class FriendActivity extends AppCompatActivity {
    public static void open(Context context) {
        context.startActivity(new Intent(context, FriendActivity.class));
    }


    private List<ChatFriend> itemList;
    private RecyclerView list;
    private MyAdapter adapter;

    private Map<String, Object> parameters = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        initView();
        loadData();
    }

    //初始化布局
    private void initView() {
        list = (RecyclerView) findViewById(R.id.friend_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        setTitle("好友列表");
    }

    private void loadData() {
        parameters.clear();
        parameters.put("userId", DefaultMessagesActivity.senderId);
        headers.put("Accept", "application/json");
        FriendApi friendApi = RetrofitUtil.retrofit(ServerConst.WEB_URL).create(FriendApi.class);
        Call<List<ChatFriend>> call = friendApi.getFriendList(parameters);
        call.enqueue(new Callback<List<ChatFriend>>() {
            @Override
            public void onResponse(Call<List<ChatFriend>> call, Response<List<ChatFriend>> response) {
                itemList = response.body();
                initData();
            }

            @Override
            public void onFailure(Call<List<ChatFriend>> call, Throwable t) {

            }
        });


    }


    private void initData() {
        adapter = new MyAdapter(this, itemList);
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new FriendRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
//                Toast.makeText(FriendActivity.this,
//                        "点击事件被触发,位置：" + position+itemList.get(position).getNickname(), Toast.LENGTH_SHORT).show();
                //查询出会话id
                Long friendId = itemList.get(position).getFriendUserId();
                DaoSession daoSession = ((NettyApplication) getApplication()).getDaoSession();
                MessageDataDao messageDataDao = daoSession.getMessageDataDao();
                QueryBuilder<MessageData> queryBuilder = messageDataDao.queryBuilder()
                        .where(MessageDataDao.Properties.SenderId.eq(friendId));
                queryBuilder.join(DialogData.class, DialogDataDao.Properties.Id)
                        .where(DialogDataDao.Properties.DialogType.eq(DialogTypeEnum.PRIVATE_DIALOG.getIndex()));
                MessageData messageData = queryBuilder.limit(1).build().unique();
                DefaultMessagesActivity.open(FriendActivity.this,
                        messageData==null?null:messageData.getDialogId(), friendId);
                finish();
            }
        });

        adapter.setOnItemLongClickListener(new FriendRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position) {
                Toast.makeText(FriendActivity.this, "长按事件被触发,位置：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyAdapter extends FriendRecyclerAdapter<ChatFriend> {

        public MyAdapter(Context ctx, List<ChatFriend> l) {
            super(ctx, l);
        }

        @Override
        protected void add(int position) {
            //TODO 下面是测试代码
            Random random = new Random();
            int i = random.nextInt(3);
            ChatFriend item = new ChatFriend();
            adapter.addData(position, item);
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
        AppUtils.showToast(this, "添加好友", false);
        return super.onOptionsItemSelected(item);
    }

}