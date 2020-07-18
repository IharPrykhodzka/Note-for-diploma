package com.example.diplomjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        final View currentView;
        if (convertView != null) {
            currentView = convertView;
        } else {
            currentView = inflater.inflate(R.layout.notes_list, parent, false);
        }

        final DataItems model = (DataItems) getItem(position);

        if (model != null) {

            TextView title = currentView.findViewById(R.id.title);
            TextView subTitle = currentView.findViewById(R.id.subtitle);
            TextView dateTime = currentView.findViewById(R.id.timeAndDate);

            title.setText(model.getTitle_view());
            subTitle.setText(model.getSubTitle_view());
            dateTime.setText(model.getDateTime_view());
            String checkRedLine = model.getCheckBoxInInteger();

            if (checkRedLine.equals("0")) {
                dateTime.setVisibility(View.GONE);
            }else {
                dateTime.setVisibility(View.VISIBLE);
            }


        }
        return currentView;
    }
}
