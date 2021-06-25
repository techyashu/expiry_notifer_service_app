package com.example.expirynotifier;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{
    public myadapter(@NonNull @NotNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull myadapter.myviewholder holder, int position, @NonNull @org.jetbrains.annotations.NotNull model model) {

        holder.Item.setText(model.getItem());
        holder.date.setText(model.getDate());

    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView Item,date;
        public myviewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            Item=(TextView)itemView.findViewById(R.id.nametext);
            date=(TextView)itemView.findViewById(R.id.coursetext);
        }
    }
}
