package com.synergym;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.reflect.TypeToken;
import com.synergym.adapter.NewsAdapter;
import com.synergym.model.ArticlesItem;
import com.synergym.model.NewsModel;
import com.synergym.network.ApiInterface;
import com.synergym.network.CacheManager;
import com.synergym.network.CacheUtils;
import com.synergym.network.MyApplication;
import com.synergym.utils.Const;
import com.synergym.utils.NetworkCheck;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends Activity {
    private NewsAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ArticlesItem> newslist;
    private CacheManager cacheManager;
    LinearLayout rootLayout;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication) getApplication()).getNetComponent().inject(this);

        newslist = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);

        View includedLayout = findViewById(R.id.toolbar);
        TextView toolbartxtTitle = (TextView) includedLayout.findViewById(R.id.toolbartxtTitle);
        ImageView backBtn = (ImageView)includedLayout. findViewById(R.id.ivBackButton);

        toolbartxtTitle.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.GONE);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new NewsAdapter(MainActivity.this, newslist);
        recyclerView.setAdapter(adapter);
        cacheManager = new CacheManager(this);

        initFromCache();
    }

    private void initFromCache() {
        Type type = new TypeToken<ArrayList<ArticlesItem>>() {
        }.getType();
        ArrayList<ArticlesItem> itemLatest = (ArrayList<ArticlesItem>) cacheManager.readJson(type, CacheUtils.LATEST);
        if (itemLatest != null) {
            adapter.replaceWith(itemLatest);
        } else {
            callArticalList();
        }
    }

    private void callArticalList() {

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<NewsModel> call = api.getNewsList("tesla", Const.currentDate(), "publishedAt", "20aed0afdc354d2da41359844c4d587d");

        Log.v("retrofit ", " success " + call.request().url().toString());

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                Log.v("retrofit ", " success ");
                Log.v("retrofit ", " success " + response.body().getArticles().toString());
                newslist.addAll(response.body().getArticles());
                adapter.notifyDataSetChanged();

                adapter.replaceWith((ArrayList<ArticlesItem>) response.body().getArticles());
                Type type = new TypeToken<ArrayList<ArticlesItem>>() {
                }.getType();
                cacheManager.writeJson(response.body().getArticles(), type, CacheUtils.LATEST);
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        getLatest();
        super.onResume();
    }

    private void getLatest() {

        if (NetworkCheck.isConnectionAvailable(this)) {
            ApiInterface api = retrofit.create(ApiInterface.class);
            Call<NewsModel> call = api.getNewsList("tesla", Const.currentDate(), "publishedAt", "20aed0afdc354d2da41359844c4d587d");
            Log.v("ManActivity", "url " + call.request().url().toString());
            call.enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(@NonNull Call<NewsModel> call, @NonNull Response<NewsModel> response) {
                    if (response.body() != null) {
                        //here is your json data from api, save this into json file.
                        ArrayList<ArticlesItem> items = (ArrayList<ArticlesItem>) response.body().getArticles();
                        if (items != null) {
                            adapter.replaceWith((ArrayList<ArticlesItem>) response.body().getArticles());
                            Type type = new TypeToken<ArrayList<ArticlesItem>>() {
                            }.getType();
                            cacheManager.writeJson(response.body().getArticles(), type, CacheUtils.LATEST);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {
                    //api failed to return data due to network problem or something else, display data from json file.
                    NetworkCheck.l("TAG", t);
                    Type type = new TypeToken<ArrayList<ArticlesItem>>() {
                    }.getType();
                    ArrayList<ArticlesItem> itemLatests = (ArrayList<ArticlesItem>) cacheManager.readJson(type, CacheUtils.LATEST);
                    if (itemLatests != null)
                        adapter.replaceWith(itemLatests);
                }
            });
        } else {
            //Internet not available, display data from json file.
            Type type = new TypeToken<ArrayList<ArticlesItem>>() {
            }.getType();
            ArrayList<ArticlesItem> itemLatests = (ArrayList<ArticlesItem>) cacheManager.readJson(type, CacheUtils.LATEST);
            if (itemLatests != null)
                adapter.replaceWith(itemLatests);
            Snackbar.make(rootLayout, "No internet connection", Snackbar.LENGTH_SHORT).setAction("retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.this.getLatest();
                }
            }).show();
        }
    }
}