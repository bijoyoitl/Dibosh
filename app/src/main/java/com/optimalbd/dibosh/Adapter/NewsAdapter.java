package com.optimalbd.dibosh.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.optimalbd.dibosh.NewsModel.Attachment;
import com.optimalbd.dibosh.NewsModel.Images;
import com.optimalbd.dibosh.NewsModel.Medium;
import com.optimalbd.dibosh.NewsModel.NewsMain;
import com.optimalbd.dibosh.NewsModel.Post;
import com.optimalbd.dibosh.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ripon on 3/30/2017.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Post> newsMainArrayList;
    private LayoutInflater layoutInflater;

    public NewsAdapter(Context context, ArrayList<Post> newsMainArrayList) {
        this.context = context;
        this.newsMainArrayList = newsMainArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newsMainArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsMainArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.news_item, null);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) view.findViewById(R.id.thumbnailImageView);
            viewHolder.headingTextView = (TextView) view.findViewById(R.id.headingTextView);
            viewHolder.excerptTextView = (TextView) view.findViewById(R.id.excerptTextView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        List<Attachment> attachments = newsMainArrayList.get(position).getAttachments();


        if (attachments.size() == 1) {
            String thumbnail = "";

            for (Attachment medi : attachments) {
                thumbnail = medi.getImages().getLarge().getUrl();

                if (!thumbnail.equals("")) {
                    Picasso.with(context).load(thumbnail).placeholder(R.drawable.news).error(R.drawable.news).into(viewHolder.imageView);
                }

            }
        } else {
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.news);
            viewHolder.imageView.setImageBitmap(bm);
        }

        if (Build.VERSION.SDK_INT >= 24) {
            viewHolder.headingTextView.setText(Html.fromHtml(newsMainArrayList.get(position).getTitlePlain(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.headingTextView.setText(Html.fromHtml(newsMainArrayList.get(position).getTitlePlain()));
        }

        if (Build.VERSION.SDK_INT >= 24) {
            viewHolder.excerptTextView.setText(Html.fromHtml(newsMainArrayList.get(position).getExcerpt(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.excerptTextView.setText(Html.fromHtml(newsMainArrayList.get(position).getExcerpt()));
        }

        return view;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView headingTextView;
        private TextView excerptTextView;
    }

}
