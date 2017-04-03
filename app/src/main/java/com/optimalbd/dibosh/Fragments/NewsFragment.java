package com.optimalbd.dibosh.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.optimalbd.dibosh.Adapter.NewsAdapter;
import com.optimalbd.dibosh.NewsApi;
import com.optimalbd.dibosh.NewsDetailsActivity;
import com.optimalbd.dibosh.NewsModel.Attachment;
import com.optimalbd.dibosh.NewsModel.NewsMain;
import com.optimalbd.dibosh.NewsModel.Post;
import com.optimalbd.dibosh.R;
import com.optimalbd.dibosh.Ulility.DiboshSharePreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    String baseUrl = "http://api.deshinewsbd.com/";
    NewsApi newsApi;
    ListView listView;

    int mCurrentPage = 2;
    boolean userScrolled = false;

    RelativeLayout loadMoreLayout;

    ArrayList<Post> postArrayList;
    ArrayList<Post> scrollPostArrayList;

    DiboshSharePreference sharePreference;
    String lang;
    String newsLang;

    ProgressBar loading_progressBar;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        listView = (ListView) view.findViewById(R.id.newsListView);
        loadMoreLayout = (RelativeLayout) view.findViewById(R.id.loadMoreLayout);
        loading_progressBar=(ProgressBar)view.findViewById(R.id.loading_progressBar);

        postArrayList = new ArrayList<>();
        scrollPostArrayList = new ArrayList<>();
        sharePreference = new DiboshSharePreference(getActivity());


        lang = sharePreference.getLanguage();
        if (lang.equals("en")) {
            newsLang = "2";
        } else {
            newsLang = "1";
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        newsApi = retrofit.create(NewsApi.class);
        String link = "api/get_category_posts/?id=" + newsLang;
        getData(link);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (userScrolled
                        && firstVisibleItem + visibleItemCount == totalItemCount) {

                    userScrolled = false;
                    int totalPage = sharePreference.getTotalPages();

                    if (mCurrentPage <= totalPage) {

                        String link = "http://api.deshinewsbd.com/api/get_category_posts/?id=" + newsLang + "&page=" + mCurrentPage;
                        getScrollData(link,totalItemCount);
                    }
                }

            }
        });

        return view;
    }

    private void getData(String link) {

        final Call<NewsMain> getAllNews = newsApi.getAllNews(link);

        getAllNews.enqueue(new Callback<NewsMain>() {
            @Override
            public void onResponse(Call<NewsMain> call, Response<NewsMain> response) {
                NewsMain newsMain = response.body();
                int pages = newsMain.getPages();

                sharePreference.saveTotalPage(pages);

                postArrayList = (ArrayList<Post>) newsMain.getPosts();
                NewsAdapter newsAdapter = new NewsAdapter(getActivity(), postArrayList);
                listView.setAdapter(newsAdapter);
                loading_progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NewsMain> call, Throwable t) {
                t.printStackTrace();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String thumbnail = "";
                int newsId = postArrayList.get(position).getId();
                String heading = postArrayList.get(position).getTitlePlain();
                String details = postArrayList.get(position).getContent();
                String time = postArrayList.get(position).getDate();

                List<Attachment> attachment = postArrayList.get(position).getAttachments();
                for (Attachment medi : attachment) {
                    thumbnail = medi.getImages().getFull().getUrl();

                }
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("heading", heading);
                intent.putExtra("details", details);
                intent.putExtra("time", time);
                intent.putExtra("img", thumbnail);
                startActivity(intent);
            }
        });
    }

    private void getScrollData(String link, final int totalItem) {

        loadMoreLayout.setVisibility(View.VISIBLE);

        final Call<NewsMain> getAllNews = newsApi.getAllNews(link);

        getAllNews.enqueue(new Callback<NewsMain>() {
            @Override
            public void onResponse(Call<NewsMain> call, Response<NewsMain> response) {
                NewsMain newsMain = response.body();

                mCurrentPage += 1;

                scrollPostArrayList = (ArrayList<Post>) newsMain.getPosts();
                postArrayList.addAll(scrollPostArrayList);

                loadMoreLayout.setVisibility(View.GONE);
                NewsAdapter newsAdapter = new NewsAdapter(getActivity(), postArrayList);
                listView.setAdapter(newsAdapter);
                listView.setSelection(totalItem-1);
            }

            @Override
            public void onFailure(Call<NewsMain> call, Throwable t) {
                t.printStackTrace();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String thumbnail = "";
                int newsId = postArrayList.get(position).getId();
                String heading = postArrayList.get(position).getTitlePlain();
                String details = postArrayList.get(position).getContent();
                String time = postArrayList.get(position).getDate();

                List<Attachment> attachment = postArrayList.get(position).getAttachments();
                for (Attachment medi : attachment) {
                    thumbnail = medi.getImages().getFull().getUrl();

                }
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("heading", heading);
                intent.putExtra("details", details);
                intent.putExtra("time", time);
                intent.putExtra("img", thumbnail);
                startActivity(intent);
            }
        });
    }


}
