package com.example.mytext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.InnerHolder> {

    List<Integer> list;
    Context context;
    public MyAdapter(List<Integer>list,Context context) {
        this.list=list;
        this.context=context;
    }

    public MyAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rv,parent,false);
        InnerHolder innerHolder =new InnerHolder(view);
        return innerHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyAdapter.InnerHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }


}
