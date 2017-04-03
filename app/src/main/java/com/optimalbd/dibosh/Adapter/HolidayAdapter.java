package com.optimalbd.dibosh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.optimalbd.dibosh.Model.Holiday;
import com.optimalbd.dibosh.R;

import java.util.ArrayList;

/**
 * Created by ripon on 2/8/2017.
 */

public class HolidayAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Holiday> holidayArrayList;
    private LayoutInflater layoutInflater;
    private String lang;
    private String mode;

    public HolidayAdapter(Context context, ArrayList<Holiday> holidayArrayList, String lang, String mode) {
        this.context = context;
        this.holidayArrayList = holidayArrayList;
        this.lang = lang;
        this.mode = mode;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return holidayArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return holidayArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (convertView == null) {
            if (mode.equals("1")) {
                view = layoutInflater.inflate(R.layout.holiday_item, null);
            } else {
                view = layoutInflater.inflate(R.layout.holiday_table_view, null);
            }
            viewHolder = new ViewHolder();

            viewHolder.idTV = (TextView) view.findViewById(R.id.idTV);
            viewHolder.dateTV = (TextView) view.findViewById(R.id.dateTV);
            viewHolder.dayTV = (TextView) view.findViewById(R.id.dayTV);
            viewHolder.nameTV = (TextView) view.findViewById(R.id.nameTV);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (lang.equals("en")) {
            viewHolder.idTV.setText(holidayArrayList.get(i).getId());
            viewHolder.dateTV.setText(holidayArrayList.get(i).getDateEn());
            viewHolder.dayTV.setText(holidayArrayList.get(i).getDayEn());
            viewHolder.nameTV.setText(holidayArrayList.get(i).getNameEn());

        } else {
            viewHolder.idTV.setText(holidayArrayList.get(i).getId());
            viewHolder.dateTV.setText(holidayArrayList.get(i).getDateBn());
            viewHolder.dayTV.setText(holidayArrayList.get(i).getDayBn());
            viewHolder.nameTV.setText(holidayArrayList.get(i).getNameBn());
        }

        return view;
    }

    public class ViewHolder {
        private TextView idTV;
        private TextView dateTV;
        private TextView dayTV;
        private TextView nameTV;

    }
}
