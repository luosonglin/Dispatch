package com.songlin.dispatch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.songlin.dispatch.R;
import com.songlin.dispatch.model.chat.ChatDetail;

import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Luo Songlin on 5/27/16.
 *
 */
public class ChatAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private List<ChatDetail> chatDetailList = new ArrayList<>();

    public ChatAdapter(Context mContext, List<ChatDetail> chatDetailList) {
        this.mContext = mContext;
        this.chatDetailList = chatDetailList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_detail, parent, false);
        ChatDetailViewHolder chatDetailViewHolder = new ChatDetailViewHolder(view);
        return chatDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatDetailViewHolder chatDetailViewHolder = (ChatDetailViewHolder) holder;
        Picasso.with(mContext).load(chatDetailList.get(position).getAvatar())  //+ "?imageMogr/v2/thumbnail/100x100"
                .error(R.drawable.avatar_default)
                .into(chatDetailViewHolder.mItemChatLogo);
        chatDetailViewHolder.mItemChatName.setText(chatDetailList.get(position).getAuthor());
        chatDetailViewHolder.mItemChatTime.setText(chatDetailList.get(position).getCreatedStr());
        chatDetailViewHolder.mItemChatContentTv.setText(chatDetailList.get(position).getMessage());
        if (chatDetailList.get(position).getPicture() != null) {
            Picasso.with(mContext).load(chatDetailList.get(position).getPicture())
                    .error(R.drawable.ic_launcher)
                    .into(chatDetailViewHolder.mItemChatContentIv);
        } else {
            chatDetailViewHolder.mItemChatContentIv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatDetailList != null ? chatDetailList.size() : 0;
    }

    public void refreshChatDetail(List<ChatDetail> chatDetailItems) {
        this.chatDetailList = chatDetailItems;
        notifyDataSetChanged();
    }

    public void addChatDetail(List<ChatDetail> chatDetailItems) {
        this.chatDetailList.addAll(chatDetailItems);
        notifyDataSetChanged();
    }

    public class ChatDetailViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_chat_logo)
        ImageView mItemChatLogo;
        @Bind(R.id.item_chat_name)
        TextView mItemChatName;
        @Bind(R.id.item_chat_content_tv)
        TextView mItemChatContentTv;
        @Bind(R.id.item_chat_content_iv)
        ImageView mItemChatContentIv;
        @Bind(R.id.item_chat_time)
        TextView mItemChatTime;

        public ChatDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
