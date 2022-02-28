package com.synergym.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.squareup.picasso.Picasso;
import com.synergym.DetailActivity;
import com.synergym.R;
import com.synergym.model.ArticlesItem;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomViewHolder> {

    private Context context;
    private List<ArticlesItem> lists = new ArrayList<>();

    public NewsAdapter(Context context, List<ArticlesItem> lists) {
        this.context = context;
        this.lists = lists;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_row, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {

        Log.v("adapter", "artical size: " + lists.size());
        ArticlesItem list = lists.get(i);

        if (lists.get(i).getTitle() != null) {
            customViewHolder.txtTitle.setText(lists.get(i).getTitle().toString());
            customViewHolder.txtTitle.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BOLD));
        }

        if (lists.get(i).getAuthor() != null && lists.get(i).getPublishedAt() != null) {
           /* DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(lists.get(i).getPublishedAt() + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);*/

            customViewHolder.txtSrc.setText(lists.get(i).getAuthor().toString() + "  " + lists.get(i).getPublishedAt());
            customViewHolder.txtSrc.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BOLD));
        }

        if (lists.get(i).getUrlToImage() != null) {
            Picasso.with(context)
                    .load(list.getUrlToImage())
                    .noFade()
                    .into(customViewHolder.ivImage);
        }

        customViewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("LIST", (Serializable) lists);
                intent.putExtra("POS", i);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void replaceWith(ArrayList<ArticlesItem> itemLatests) {
        this.lists = itemLatests;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public CustomViewHolder(View itemView) {
            super(itemView);
        }

        ImageView ivImage = itemView.findViewById(R.id.ivImage);
        TextView txtTitle = itemView.findViewById(R.id.txtTitle);
        TextView txtSrc = itemView.findViewById(R.id.txtSrc);
        CardView cardView = itemView.findViewById(R.id.cardView);
        FrameLayout frameLayout = itemView.findViewById(R.id.frameLayout);
    }
}