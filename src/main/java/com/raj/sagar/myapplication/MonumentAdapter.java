package com.raj.sagar.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by SAGAR on 16-03-2018.
 */

public class MonumentAdapter extends RecyclerView.Adapter<MonumentAdapter.ViewHolder> {

    private ArrayList<String> titlesmonument;
    private Context context;
    private ArrayList<String> descriptionsmonument;

    private ArrayList<Integer> urlsToImagemonument;

    private int[] androidColors;

    public MonumentAdapter(int[] androidColors, ArrayList<String> titlesmonument, ArrayList<String> descriptionsmonument, ArrayList<Integer> urlsToImagemonument, Context context) {
        this.titlesmonument = titlesmonument;
        this.descriptionsmonument = descriptionsmonument;

        this.context = context;
        this.urlsToImagemonument = urlsToImagemonument;
        this.androidColors = androidColors;
    }

    @Override
    public MonumentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent, false);
        return new MonumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonumentAdapter.ViewHolder holder, final int position) {
        holder.titlemonument.setText(titlesmonument.get(position));
        holder.descriptionmonument.setText((descriptionsmonument.get(position)));


        int randomColor = androidColors[new Random().nextInt(androidColors.length)];
        holder.linearLayoutmonument.setBackgroundColor(randomColor);
        Picasso.with(context).load(urlsToImagemonument.get(position)).into(holder.imageViewmonument);
        holder.linearLayoutmonument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///context.startActivity(new Intent(context, Main2Activity.class).putExtra("url", urls.get(position)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                Toast.makeText(context, titlesmonument.get(position), Toast.LENGTH_LONG).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return titlesmonument.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemClickListener2 itemClickListener2;
        public TextView titlemonument, descriptionmonument;
        public ImageView imageViewmonument;
        public LinearLayout linearLayoutmonument;


        public ViewHolder(View itemView) {
            super(itemView);
            titlemonument = (TextView) itemView.findViewById(R.id.textView1);
            imageViewmonument = (ImageView) itemView.findViewById(R.id.imageviewmonument);
            linearLayoutmonument = (LinearLayout) itemView.findViewById(R.id.linearLayoutmonument);
            descriptionmonument = (TextView) itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener2 itemClickListener2){

            this.itemClickListener2 = itemClickListener2;
        }

        @Override
        public void onClick(View v) {
            itemClickListener2.onClick(v, getAdapterPosition());

        }
    }
}
