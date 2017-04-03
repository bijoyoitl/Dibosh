package com.optimalbd.dibosh.Ulility;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by ripon on 12/5/2016.
 */

public class ListUtils {
    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = (ListAdapter) listView.getAdapter();
        if (adapter == null) {
            // pre-condition
            return;
        }

        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();


    }
}
