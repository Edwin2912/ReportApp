package com.example.system;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class Emergency_Report_list extends ArrayAdapter<Emergency> {

    private Activity context;
    List<Emergency> emergency;

    public Emergency_Report_list(Activity context, List<Emergency> emergency)
    {
        super(context, R.layout.custom_emergency_report_list, emergency);
        this.context = context;
        this.emergency = emergency;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.custom_emergency_report_list,parent,false);
        }
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_emergency_report_list, null, true);
        //View listViewItem = inflater.inflate(R.layout.custom_list, null, true);

        TextView txt_emergency_description = listViewItem.findViewById(R.id.txt_maintenance_description);
        TextView txt_emergency_report_date = listViewItem.findViewById(R.id.txt_maintenance_report_massage);
        TextView txt_emergency_status = listViewItem.findViewById(R.id.txt_ems_status);
        TextView videourl = listViewItem.findViewById(R.id.txt_ems_url);

        //emergency.get(position).getStatus();

        Emergency emergency_report = emergency.get(position);
        txt_emergency_description.setText(emergency_report.getDescription());
        txt_emergency_report_date.setText(emergency_report.getDate_reported());
        videourl.setText(emergency_report.getVideo());
        String status=emergency_report.getStatus();
        //txt_emergency_status.setTextColor(R.color.Red);
        if (status.equalsIgnoreCase("un-resolved"))
    {
        txt_emergency_status.setTextColor(R.color.Red);
        txt_emergency_status.setText(emergency_report.getStatus());

    }


        return listViewItem;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Emergency getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}


