package com.example.diplomjava;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class DataItemsAdapter extends BaseAdapter {

    private List<DataItems> dataItems;
    private LayoutInflater inflater;

    public DataItemsAdapter(List<DataItems> dataItems, Context context) {
        this.dataItems = dataItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataItems != null ? dataItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return dataItems != null ? dataItems.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View currentView;
        if (convertView != null) {
            currentView = convertView;
        } else {
            currentView = inflater.inflate(R.layout.notes_list, parent, false);
        }

        final DataItems dataItems = (DataItems) getItem(position);
        Calendar calendar = Calendar.getInstance();

        if (dataItems != null) {

            TextView title = currentView.findViewById(R.id.title);
            TextView subTitle = currentView.findViewById(R.id.subtitle);
            TextView dateTime = currentView.findViewById(R.id.timeAndDate);

            title.setText(dataItems.getTitle_view());
            subTitle.setText(dataItems.getSubTitle_view());

            if (dataItems.getDateTime_view() != null ) {
                calendar.setTimeInMillis(Long.parseLong(dataItems.getDateTime_view()));
                dateTime.setText(DateFormat.format("dd/MM/yy HH:mm", calendar.getTimeInMillis()));
            }else {
                dateTime.setText(dataItems.getDateTime_view());
            }

            String checkRedLine = dataItems.getCheckBoxInInteger();

            if (checkRedLine.equals("0")) {
                dateTime.setVisibility(View.GONE);
            }else {
                dateTime.setVisibility(View.VISIBLE);
            }

        }
        return currentView;
    }
}
