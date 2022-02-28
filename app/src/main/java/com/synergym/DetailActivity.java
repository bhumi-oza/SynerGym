package com.synergym;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.synergym.model.ArticlesItem;
import com.synergym.utils.Const;

import java.util.ArrayList;

import okhttp3.internal.Util;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artical_detail_activity);
        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        TextView txtSrc = (TextView) findViewById(R.id.txtSrc);
        TextView txtDesc = (TextView) findViewById(R.id.txtDesc);
        TextView txtDate = (TextView) findViewById(R.id.txtDate);

        View includedLayout = findViewById(R.id.toolbar);
        TextView toolbartxtTitle = (TextView) includedLayout.findViewById(R.id.toolbartxtTitle);
        ImageView backBtn = (ImageView) includedLayout.findViewById(R.id.ivBackButton);

        toolbartxtTitle.setVisibility(View.GONE);
        backBtn.setVisibility(View.VISIBLE);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.finish();
            }
        });
        ArrayList<ArticlesItem> articlesItems = (ArrayList<ArticlesItem>) getIntent().getSerializableExtra("LIST");
        int pos = getIntent().getIntExtra("POS", 0);

        Log.v("log", "list : ->  " + articlesItems.toString());

        txtTitle.setText(articlesItems.get(pos).getTitle() + "");
        // txtTitle.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BOLD));


        txtSrc.setText(articlesItems.get(pos).getAuthor().toString() );
        //  txtSrc.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BOLD));


        txtDesc.setText(articlesItems.get(pos).getDescription().toString().toString());
        //  txtSrc.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BOLD));

        txtDate.setText(Const.changeDateFormate(articlesItems.get(pos).getPublishedAt().toString())+"");
        //  txtSrc.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BOLD));


        if (articlesItems.get(pos).getUrlToImage() != null) {
            Picasso.with(this)
                    .load(articlesItems.get(pos).getUrlToImage())
                    .noFade()
                    .into(ivImage);
        }


    }
}
