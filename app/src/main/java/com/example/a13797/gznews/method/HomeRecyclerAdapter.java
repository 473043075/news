package com.example.a13797.gznews.method;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13797.gznews.R;
import com.example.a13797.gznews.activity.ActivityNewsplay;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.home_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(childView);
        viewHolder.newsView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(),ActivityNewsplay.class);
                parent.getContext().startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {




    }

    @Override
    public int getItemCount() {
        return 15;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        TextView newsTitle;
        TextView newsContent,newsAuthor,newsDate;
        ImageView newsImg1,newsImg2,newsImg3;

        public ViewHolder(View itemView) {
            super(itemView);
            newsView = itemView;
            newsTitle = (TextView) itemView.findViewById(R.id.home_news_title);
            newsContent = (TextView) itemView.findViewById(R.id.home_news_content);
            newsAuthor = (TextView)itemView.findViewById(R.id.home_news_author);
            newsDate = (TextView)itemView.findViewById(R.id.home_news_date);
            newsImg1 = (ImageView)itemView.findViewById(R.id.home_news_img1);
            newsImg2 = (ImageView)itemView.findViewById(R.id.home_news_img2);
            newsImg3 = (ImageView)itemView.findViewById(R.id.home_news_img3);



        }
    }
}

