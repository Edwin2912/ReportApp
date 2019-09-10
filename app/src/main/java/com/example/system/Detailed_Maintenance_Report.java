package com.example.system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Detailed_Maintenance_Report extends AppCompatActivity
{


    private ImageView mImageView;
    TextView description,message,reporter,status;
    private ProgressDialog progressDialog;
    String Back_To_My_Reports;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_detailed__maintenance__report__details);

        mImageView = findViewById(R.id.Maintence_Report);
        description=findViewById(R.id.Txt_Desc);
        message= findViewById(R.id.Txt_message);
        reporter= findViewById(R.id.Txt_Rep);
        status= findViewById(R.id.Txt_Stat);

        Intent i = this.getIntent();
        String Description=i.getExtras().getString("Description");
        String Message=i.getExtras().getString("Message");
        String Reporter=i.getExtras().getString("Reporter");
        String Status=i.getExtras().getString("Status");
        String ImageUrl=i.getExtras().getString("ImageUrl");
        Back_To_My_Reports=i.getExtras().getString("Back");




        description.setText(Description);
        message.setText(Message);
        reporter.setText(Reporter);
        status.setText(Status);

        progressDialog = new ProgressDialog(this);

        Glide.with(getApplicationContext()).load(ImageUrl).into(mImageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.back1,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.Back:

                if(Back_To_My_Reports.equalsIgnoreCase("Back To My Reports Maintenance Reports"))
                {
                    startActivity(new Intent(this,Sello.class));
                    overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                }
                else
                {
                    startActivity(new Intent(this,Emergency_Reports_Retrieve.class));
                    overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart()
    {
        super.onStart();

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Report Details.....");
        progressDialog.show();
        progressDialog.dismiss();
    }


}
