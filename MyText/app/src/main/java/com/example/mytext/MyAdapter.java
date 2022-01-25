package com.example.mytext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.InnerHolder> {

        private ArrayList<GoodsEntity> data;


        public MyAdapter(ArrayList<GoodsEntity> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,
                    parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull InnerHolder holder, int position){
            holder.tv_content.setText(data.get(position).getContent());
            holder.tv_title.setText(data.get(position).getContent());
            holder.tv_username.setText(data.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class InnerHolder extends RecyclerView.ViewHolder {

            TextView tv_title;
            TextView tv_content;
            TextView tv_username;

            public InnerHolder(@NonNull View itemView) {
                super(itemView);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                tv_username = (TextView) itemView.findViewById(R.id.tv_username);

            }
        }


        public void refresh(ArrayList<GoodsEntity> addList) {
            //增加数据
            int position = data.size();
            data.addAll(position, addList);
            notifyDataSetChanged();
        }

    }
