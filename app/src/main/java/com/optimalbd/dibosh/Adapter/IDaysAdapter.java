package com.optimalbd.dibosh.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.optimalbd.dibosh.Model.IDays;
import com.optimalbd.dibosh.R;

import java.util.ArrayList;

import static java.util.Collections.addAll;

/**
 * Created by ripon on 12/19/2016.
 */

public class IDaysAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<IDays> iDaysArrayList;
    private LayoutInflater layoutInflater;
    private String lang;
    private int  mode;

    public IDaysAdapter(Context context, ArrayList<IDays> iDaysArrayList, String lang, int mode) {
        this.context = context;
        this.iDaysArrayList = iDaysArrayList;
        this.lang = lang;
        this.mode = mode;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return iDaysArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return iDaysArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {

            if (mode==2){
                view = layoutInflater.inflate(R.layout.day_table_view, null);
            }else {
                view = layoutInflater.inflate(R.layout.iday_item, null);
            }

            viewHolder = new ViewHolder();

            viewHolder.idTV = (TextView) view.findViewById(R.id.idTV);
            viewHolder.dateTV = (TextView) view.findViewById(R.id.dateTV);
            viewHolder.nameTV = (TextView) view.findViewById(R.id.nameTV);
//            viewHolder.detailsTV = (TextView) view.findViewById(R.id.detailsTV);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (lang.equals("en")){

            viewHolder.idTV.setText(iDaysArrayList.get(i).getId());
            viewHolder.dateTV.setText(iDaysArrayList.get(i).getDate_en());
            viewHolder.nameTV.setText(iDaysArrayList.get(i).getName_en());


        }else {
            viewHolder.idTV.setText(iDaysArrayList.get(i).getId());
            viewHolder.dateTV.setText(iDaysArrayList.get(i).getDate_bn());
            viewHolder.nameTV.setText(iDaysArrayList.get(i).getName_bn());
        }

        return view;
    }



    public class ViewHolder {
        private TextView idTV;
        private TextView dateTV;
        private TextView nameTV;
        private TextView detailsTV;
    }

    public void setFilter(ArrayList<IDays> iDayses){
        iDaysArrayList = new ArrayList<>();

        iDaysArrayList.clear();
        iDaysArrayList.addAll(iDayses);

        notifyDataSetChanged();
    }

}
