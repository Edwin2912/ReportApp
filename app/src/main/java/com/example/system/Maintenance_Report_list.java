package com.example.system;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class Maintenance_Report_list extends ArrayAdapter<Maintenance>
{

    private Activity context;
    List<Maintenance> report;

    public Maintenance_Report_list(Activity context, List<Maintenance> report)
    {
        super(context, R.layout.custom_maintenance_report_list, report);
        this.context = context;
        this.report = report;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {



        if (convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.custom_maintenance_report_list,parent,false);
        }
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_maintenance_report_list, null, true);

        TextView txt_emergency_description = listViewItem.findViewById(R.id.txt_maintenance_description);
        TextView txt_emergency_report_message = listViewItem.findViewById(R.id.txt_maintenance_report_massage);
        TextView txt_emergency_report_date = listViewItem.findViewById(R.id.txt_maintenance_report_);
        TextView txt_emergency_report_status = listViewItem.findViewById(R.id.txt_maintenance_status);
        ImageView maintenanceImage = listViewItem.findViewById(R.id.logo);

        //txtUserRole

        Maintenance maintenance_report = report.get(position);
        txt_emergency_description.setText(maintenance_report.getDescription());
        txt_emergency_report_date.setText(maintenance_report.getReporter());
        txt_emergency_report_message.setText(maintenance_report.getMessage());
        txt_emergency_report_status.setText(maintenance_report.getStatus());
        String ImageUrl=maintenance_report.getimageUrl();
        Glide.with(listViewItem.getContext()).load(ImageUrl).into(maintenanceImage);
        String status=maintenance_report.getStatus();

        if (status.equalsIgnoreCase("un-resolved"))
        {
            txt_emergency_report_status.setHighlightColor(R.color.Red);

        }else
        {
            txt_emergency_report_status.setTextColor(R.color.Red);
        }


        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent i = new Intent(context, User_Details.class);
                i.putExtra("Description",maintenance_report.getDescription());
                i.putExtra("Message",maintenance_report.getMessage());
                i.putExtra("Reporter",maintenance_report.getReporter());
                i.putExtra("Status",maintenance_report.getStatus());
                i.putExtra("ImageUrl",maintenance_report.getimageUrl());

                //context.startActivity(i);
               // context.overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
            }
        });

        return listViewItem;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Maintenance getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}


