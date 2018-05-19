package com.easy.fly.flyeasy.fragments;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.adapters.HotelAdapter;
import com.easy.fly.flyeasy.adapters.NewsArticleAdapter;
import com.easy.fly.flyeasy.adapters.NewsSourceAdapter;
import com.easy.fly.flyeasy.common.NewsParametersConstants;
import com.easy.fly.flyeasy.common.Response;
import com.easy.fly.flyeasy.db.models.Article;
import com.easy.fly.flyeasy.db.models.CombineModel;
import com.easy.fly.flyeasy.db.models.News;
import com.easy.fly.flyeasy.db.models.Source;
import com.easy.fly.flyeasy.di.Injectable;
import com.easy.fly.flyeasy.utils.UtilConfigKey;
import com.easy.fly.flyeasy.viewmodel.HotelViewModel;
import com.easy.fly.flyeasy.viewmodel.NewsViewModel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailsFragment extends Fragment implements Injectable {

    @BindView(R.id.news_horizontal_recyclerView)
    RecyclerView newsHorizontalRecyclerView;

    @BindView(R.id.news_vertical_recyclerView)
    RecyclerView newsVerticalRecyclerView;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    private News news;

    private NewsArticleAdapter newsArticleAdapter;

    private NewsSourceAdapter newsSourceAdapter;

    private NewsViewModel viewModel;

    private String category;

    private String countryCode;

    private View viewSource;

    private List<Source> newsSources;

    private String apiKey;

    private boolean isSourceSelected;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    public NewsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_news_details, container, false);
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(NewsViewModel.class);
        ButterKnife.bind(this,inflate);

        initKey();

        RecyclerView.LayoutManager layoutManagerHorizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        newsHorizontalRecyclerView.setLayoutManager(layoutManagerHorizontal);
        newsHorizontalRecyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManagerVertical = new LinearLayoutManager(getContext());
        newsVerticalRecyclerView.setLayoutManager(layoutManagerVertical);
        newsVerticalRecyclerView.setItemAnimator(new DefaultItemAnimator());

        loadNews(viewModel);

        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                loadNews(viewModel);
            }
        });

        newsHorizontalRecyclerView.addOnItemTouchListener(new NewsSourceAdapter.RecyclerTouchListener(getContext(), newsHorizontalRecyclerView, new NewsSourceAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(viewSource!=null){
                    viewSource.setBackgroundColor(Color.WHITE);
                }
                viewSource = view;
                view.setBackgroundColor(Color.rgb(40, 163, 251));
                swipeRefreshLayout.setRefreshing(true);
                viewModel.allNewsBySource(newsSources.get(position).getId(),apiKey);
                isSourceSelected = true;

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        newsVerticalRecyclerView.addOnItemTouchListener(new NewsArticleAdapter.RecyclerTouchListener(getContext(), newsVerticalRecyclerView, new NewsArticleAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("URL",news.getArticles().get(position).getUrl());

                ReadArticleFragment readArticleFragment = new ReadArticleFragment();
                readArticleFragment.setArguments(bundle);
                //start fragment
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                int containerId = R.id.container;
                fragmentManager.beginTransaction()
                        .replace(containerId,readArticleFragment)
                        .commitAllowingStateLoss();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        viewModel.response().observe(this,response -> processResponse(response));

        return inflate;
    }

    private void loadNews(NewsViewModel viewModel) {

        if (NewsParametersConstants.ALL.equals(category)) {
            viewModel.allNewsByCountry(countryCode, apiKey);
        } else {
            viewModel.allNewsByCountryAndCategory(countryCode, category, apiKey);
        }

    }

    private void processResponse(Response response) {

        switch (response.status) {
            case LOADING:
                break;

            case SUCCESS:
                news = (News) response.data;
                if(isSourceSelected){
                    isSourceSelected = false;
                    loadArticles(news.getArticles());
                }else{
                    newsSources = news.getArticles().stream().map(article -> article.getSource()).collect(Collectors.toList());
                    loadSource(newsSources);
                    loadArticles(news.getArticles());
                }

                swipeRefreshLayout.setRefreshing(false);

                break;

            case ERROR:
                break;
        }
    }

    public void initKey(){
        category = (String)getArguments().get("CATEGORY");
        countryCode = (String)getArguments().get("COUNTRY");
        try {
            apiKey = UtilConfigKey.getProperty("apiKeyNews",getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadArticles(List<Article> articles){
        newsArticleAdapter = new NewsArticleAdapter(articles,getContext());
        newsVerticalRecyclerView.setAdapter(newsArticleAdapter);
    }

    private void loadSource(List<Source> newsSources){
        newsSourceAdapter = new NewsSourceAdapter(newsSources,getContext());
        newsHorizontalRecyclerView.setAdapter(newsSourceAdapter);
    }

}
