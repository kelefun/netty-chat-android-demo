package com.funstill.netty.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.funstill.netty.chat.R;
import com.funstill.netty.chat.model.chat.ChatFriend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liukaiyang on 2017/12/27.
 */

public abstract class FriendRecyclerAdapter<T> extends RecyclerView.Adapter<FriendRecyclerAdapter.ViewHolder> {

    protected ArrayList<T> mObjects;
    private Context ctx;


    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public FriendRecyclerAdapter(Context ctx, List<T> l) {
        this.ctx = ctx;
        mObjects = new ArrayList<T>();
        if (l != null)
            mObjects.addAll(l);
    }


    @Override
    public FriendRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置布局文件
        View view = View.inflate(ctx, R.layout.item_friend_cardview, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FriendRecyclerAdapter.ViewHolder holder, int position) {
        //设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.OnItemClick(holder.itemView, pos);
                }
            });
        }

        //设置长按事件
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemLongClickListener.OnItemLongClick(v, pos);
                    return true;
                }
            });
        }

        ChatFriend t = (ChatFriend) mObjects.get(position);
        holder.desc.setText(t.getSelfDesc());
        holder.nickname.setText(t.getNickname());
    }

    //添加
    protected abstract void add(int position);


    @Override
    public int getItemCount() {
        return mObjects.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nickname, desc;
        ImageView avatarImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = (TextView) itemView.findViewById(R.id.friend_nickname);
            desc = (TextView) itemView.findViewById(R.id.friend_desc);
            avatarImageView = (ImageView) itemView.findViewById(R.id.friend_avatar);
        }
    }

    /**
     * 添加数据
     *
     * @param position
     * @param item
     */
    public void addData(int position, T item) {
        mObjects.add(item);
        notifyItemInserted(position);
    }

    /**
     * 移除数据
     *
     * @param position
     */
    public void removeData(int position) {
        mObjects.remove(position);
        notifyItemRemoved(position);
    }
}