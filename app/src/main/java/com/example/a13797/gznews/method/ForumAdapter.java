package com.example.a13797.gznews.method;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13797.gznews.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForumAdapter extends ArrayAdapter<Forum> {

    private int resourceId;

    public ForumAdapter(Context context, int textViewResourceId, List<Forum> objects){
        super(context,textViewResourceId,objects);

        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Forum forum = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        CircleImageView forum_Image = (CircleImageView) view.findViewById(R.id.forum_image);
        TextView forum_name = (TextView)view.findViewById(R.id.forum_name);
        TextView forum_content = (TextView)view.findViewById(R.id.forum_content);
        ImageView forum_image_content = (ImageView)view.findViewById(R.id.forum_image_content);
        TextView forum_fenxiang = (TextView)view.findViewById(R.id.forum_fenxiang);
        TextView forum_pinglun = (TextView)view.findViewById(R.id.forum_pinglun);
        TextView forum_zan = (TextView)view.findViewById(R.id.forum_zan);

        forum_Image.setImageResource(forum.getImage());
        forum_name.setText(forum.getForum_name());
        forum_content.setText(forum.getForum_content());
        forum_image_content.setImageResource(forum.getForum_image_content());
        forum_fenxiang.setText(forum.getForum_fenxiang());
        forum_pinglun.setText(forum.getForum_pinglun());
        forum_zan.setText(forum.getForum_zan());


        return view;
    }
}
