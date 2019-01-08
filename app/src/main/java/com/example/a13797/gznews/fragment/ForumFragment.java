package com.example.a13797.gznews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a13797.gznews.R;
import com.example.a13797.gznews.activity.ActivitySendForum;
import com.example.a13797.gznews.method.Forum;
import com.example.a13797.gznews.method.ForumAdapter;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends Fragment {

    private List<Forum> forumList = new ArrayList<>();
    private ImageView sendForum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forum_layout_frag,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initForum();
        ForumAdapter forumAdapter = new ForumAdapter(getActivity(),R.layout.forum_item,forumList);
        ListView listView = (ListView)getActivity().findViewById(R.id.forum_listView);
        listView.setAdapter(forumAdapter);
        initview();
        setEvents();
    }
    private void initview(){
        sendForum= (ImageView)getActivity().findViewById(R.id.forum_layout_send);
    }
    private void setEvents(){
        sendForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ActivitySendForum.class);
                startActivity(intent);
            }
        });
    }



    private void initForum(){
        for (int i=0;i<10;i++){
            Forum forum = new Forum(R.drawable.head,"Take a Break","这是内容",R.drawable.back,"30万","49万","90万");
            forumList.add(forum);
        }
    }



 }
