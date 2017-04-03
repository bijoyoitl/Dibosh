package com.optimalbd.dibosh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.optimalbd.dibosh.Model.PersonalEvent;
import com.optimalbd.dibosh.R;

import java.util.ArrayList;

/**
 * Created by ripon on 12/21/2016.
 */

public class EventAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PersonalEvent> personalEventArrayList;
    private LayoutInflater layoutInflater;
    String lang;

    public EventAdapter(Context context, ArrayList<PersonalEvent> personalEventArrayList, String lang) {
        this.context = context;
        this.personalEventArrayList = personalEventArrayList;
        this.lang = lang;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return personalEventArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return personalEventArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View viewRow, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        View view = viewRow;
        if (viewRow == null) {

            view = layoutInflater.inflate(R.layout.event_item, null);
            viewHolder = new ViewHolder();

            viewHolder.idTV = (TextView) view.findViewById(R.id.idTV);
            viewHolder.dateTV = (TextView) view.findViewById(R.id.dateTV);
            viewHolder.nameTV = (TextView) view.findViewById(R.id.nameTV);
            viewHolder.titleTV = (TextView) view.findViewById(R.id.titleTV);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.idTV.setText(personalEventArrayList.get(i).getId());
        viewHolder.dateTV.setText(personalEventArrayList.get(i).getMonthDate());
        viewHolder.nameTV.setText(personalEventArrayList.get(i).getName());
        if (lang.equals("en")){
            viewHolder.titleTV.setText(personalEventArrayList.get(i).getName() + "'s " + personalEventArrayList.get(i).getType());
        }else {
            viewHolder.titleTV.setText(personalEventArrayList.get(i).getName() + " এর " + personalEventArrayList.get(i).getType());
        }

        return view;
    }

    public class ViewHolder {
        private TextView idTV;
        private TextView titleTV;
        private TextView dateTV;
        private TextView nameTV;
    }

    public void setFilter(ArrayList<PersonalEvent> personalEvents){
        personalEventArrayList = new ArrayList<>();
        personalEventArrayList.clear();
        personalEventArrayList.addAll(personalEvents);
        notifyDataSetChanged();
    }


}
