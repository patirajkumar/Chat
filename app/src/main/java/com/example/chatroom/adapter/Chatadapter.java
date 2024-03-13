package com.example.chatroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatroom.Message;
import com.example.chatroom.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Chatadapter extends RecyclerView.Adapter {

    ArrayList<Message> message;
    Context context;
    int Sender_view = 1;
    int Recieve_view = 2;

    public Chatadapter(ArrayList<Message> message, Context context) {
        this.message = message;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == Sender_view) {
            View v = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(context).inflate(R.layout.sample_receive, parent, false);
            return new ReceiverViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(message.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())) {
            return Sender_view;
        }
        else {
            return  Recieve_view;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message msg = message.get(position);
        if(holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder)holder).sndmsg.setText(msg.getMsg());
            SimpleDateFormat sd = new SimpleDateFormat("MMM dd HH:mm");
            String ts = sd.format(msg.getTimestamp());
            ((SenderViewHolder)holder).sndtime.setText(ts);
        }
        else {
            ((ReceiverViewHolder)holder).recmsg.setText(msg.getMsg());
            SimpleDateFormat sd = new SimpleDateFormat("MMM dd HH:mm");
            String ts = sd.format(msg.getTimestamp());
            ((ReceiverViewHolder)holder).rectime.setText(ts);
        }
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView recmsg, rectime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            recmsg = itemView.findViewById(R.id.receive);
            rectime = itemView.findViewById(R.id.rec_time);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView sndmsg, sndtime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sndmsg = itemView.findViewById(R.id.sender);
            sndtime = itemView.findViewById(R.id.send_time);
        }
    }

}
