package com.example.system;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Select_Report extends AppCompatActivity
{

  Context context=this;
    Button Emergency,Maintenance,My_Maintenance,My_Emergency;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__report);

        Emergency =  findViewById(R.id.btn_view_emergency_reports);
        Maintenance = findViewById(R.id.btn_maintenace_reports);

        My_Emergency =  findViewById(R.id.btn_view_my_emergency_reports);
        My_Maintenance = findViewById(R.id.btn_view_my_maintenace_reports);
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.mipmap.report);
        actionBar.setTitle("Select Report");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        My_Emergency.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                   // startActivity(new Intent(context, My_Emergency_Reports_Retrieve.class));
                    Intent hello = new Intent(context, My_Emergency_Reports_Retrieve.class);
                    startActivity(hello);
                    overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
                }catch(Exception ex)
                {
                    System.out.println("error :"+ex.getMessage());

                }
            }
        });

        My_Maintenance.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {//My_Maintenance_Retrieve
                    startActivity(new Intent(context, My_Maintenance_Retrieve.class));
                    //startActivity(new Intent(context, My_Maintenance_Retrieve.class));
                    overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
                }catch(Exception ex)
                {

                }
            }
        });

        Emergency.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    startActivity(new Intent(context, Emergency_Reports_Retrieve.class));
                    overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
                    }catch(Exception ex)
                    {

                    }
                }
        });

        Maintenance.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    startActivity(new Intent(context, Maintenance_Reports_Retrieve.class));
                    overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
                }catch(Exception ex)
                {

                }
            }
        });


    }


    public void Buttons(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_view_emergency_reports:
                startActivity(new Intent(this, Emergency_Reports_Retrieve.class));
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
                break;
            case R.id.btn_maintenace_reports:

                startActivity(new Intent(this, Emergency_Reports_Retrieve.class));
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
                break;
            case R.id.btn_view_my_emergency_reports:

                startActivity(new Intent(this, Maintenance_Reports_Retrieve.class));
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
                break;
            case R.id.btn_view_my_maintenace_reports:

                startActivity(new Intent(this, Emergency_Reports_Retrieve.class));
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.back1,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.Back:
                startActivity(new Intent(this,Home_Activity.class));
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Toast(String msg)
    {
        View view = getLayoutInflater().inflate(R.layout.toast_uj, (ViewGroup) findViewById(R.id.toast_uj));
        TextView text = (TextView) view.findViewById(R.id.textToast);
        text.setText(msg);
        Toast toast = new Toast(Select_Report.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }
}
