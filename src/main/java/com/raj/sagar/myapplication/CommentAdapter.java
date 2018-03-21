package com.raj.sagar.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAGAR on 18-03-2018.
 */

public class CommentAdapter extends ArrayAdapter<PostComment> {

    private ArrayList<PostComment> dataset;
    Context ctx;
    private static class ViewHolder {
        TextView username;
        TextView comment;

    }

    public CommentAdapter(@NonNull Context context,ArrayList<PostComment> data) {
        super(context, R.layout.comment_item, data);
        this.dataset = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PostComment postComment = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.comment_item, parent, false);
            viewHolder.username = (TextView) convertView.findViewById(R.id.comment_name);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.comment_body);
            result = convertView;
            convertView.setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.username.setText(postComment.getUsername());
        viewHolder.comment.setText(postComment.getComment());
        return convertView;

    }
}
