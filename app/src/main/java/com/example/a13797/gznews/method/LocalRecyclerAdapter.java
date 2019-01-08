package com.example.a13797.gznews.method;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a13797.gznews.R;

public class LocalRecyclerAdapter extends RecyclerView.Adapter<LocalRecyclerAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View childView = inflater.inflate(R.layout.local_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(childView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalRecyclerAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        View localnewsView;
        TextView localnewsTitle;
        TextView localnewsContent,localnewsAuthor,localnewsDate;
        ImageView localnewsImg1,localnewsImg2,localnewsImg3;
        public ViewHolder(View view){
            super(view);
            localnewsView = itemView;
            localnewsTitle = (TextView) itemView.findViewById(R.id.local_news_title);
            localnewsContent = (TextView) itemView.findViewById(R.id.local_news_content);
            localnewsAuthor = (TextView)itemView.findViewById(R.id.local_news_author);
            localnewsDate = (TextView)itemView.findViewById(R.id.local_news_date);
            localnewsImg1 = (ImageView)itemView.findViewById(R.id.local_news_img1);
            localnewsImg2 = (ImageView)itemView.findViewById(R.id.local_news_img2);
            localnewsImg3 = (ImageView)itemView.findViewById(R.id.local_news_img3);




        }


    }
}
