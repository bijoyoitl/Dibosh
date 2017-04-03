package com.optimalbd.dibosh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.optimalbd.dibosh.Model.Quotes;
import com.optimalbd.dibosh.R;

import java.util.ArrayList;

/**
 * Created by ripon on 1/7/2017.
 */

public class QuotesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Quotes> quotesArrayList;
    private LayoutInflater layoutInflater;

    public QuotesAdapter(Context context, ArrayList<Quotes> quotesArrayList) {

        this.context = context;
        this.quotesArrayList = quotesArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return quotesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return quotesArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        View rowView = view;

        if (view == null) {
            rowView = layoutInflater.inflate(R.layout.quotes_item, null);
            viewHolder = new ViewHolder();
            viewHolder.msgTextView = (TextView) rowView.findViewById(R.id.msgTextView);
            viewHolder.sendImageView =(ImageView)rowView.findViewById(R.id.sendImageView);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        viewHolder.msgTextView.setText(quotesArrayList.get(i).getMsg());

        viewHolder.sendImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody;
                shareBody = viewHolder.msgTextView.getText().toString();
                String heading;
                heading = "Special Days";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, heading);
                context.startActivity(Intent.createChooser(shareIntent, "Share via " + heading));
            }
        });

        return rowView;
    }

    public class ViewHolder {
        private TextView msgTextView;
        private ImageView sendImageView;
    }
}
