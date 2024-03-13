package com.example.chatroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatroom.Chatdetail;
import com.example.chatroom.R;
import com.example.chatroom.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class useradapter extends RecyclerView.Adapter<useradapter.ViewHolder>{

    ArrayList<Users> list;
    Context context;

    public useradapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = list.get(position);
        Picasso.get().load(users.getProfpic()).placeholder(R.drawable.ic_avatar_user_svgrepo_com).into(holder.img);
        holder.usrname.setText(users.getUsername());
        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + users.getUid())
                .orderByChild("timestamp").limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()) {
                            for(DataSnapshot snap: snapshot.getChildren()) {
                                holder.lstmsg.setText(snap.child("msg").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, Chatdetail.class);
            in.putExtra("userid", users.getUid());
            in.putExtra("profpic", users.getProfpic());
            in.putExtra("username", users.getUsername());
            in.putExtra("phone" , users.getPhone());
            context.startActivity(in);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView usrname, lstmsg;
        public ViewHolder(@NonNull View item) {
            super(item);
            img= item.findViewById(R.id.profileImage);
            usrname = item.findViewById(R.id.user);
            lstmsg = item.findViewById(R.id.lst);
        }
    }
}
