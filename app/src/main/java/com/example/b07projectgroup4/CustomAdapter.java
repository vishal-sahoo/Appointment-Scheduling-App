package com.example.b07projectgroup4;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Timeslot> {
    int res = 0;
    int textRes = 0;

    public CustomAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        res = resource;
        textRes = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position,convertView,parent);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        }
        Timeslot item = getItem(position);
        if (item.getIs_available() == "true") {
            ((TextView) v).setTextColor(Color.BLACK);
        } else if (item.getIs_available() == "false"){
            ((TextView) v).setTextColor(Color.LTGRAY);
        }
        TextView textView = (TextView) convertView.findViewById(textRes);
        textView.setText(item.getTime());
        return v;
    }
}
