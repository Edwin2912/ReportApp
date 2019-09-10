package com.example.system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;

public class Detailed_Emergency_Report extends AppCompatActivity
{


    TextView description,reporter,reporeted_date,status;
    VideoView videoView;
    Uri uri;
    String Back_To_My_Reports;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_detailed__emergency__report);

        videoView = findViewById(R.id.videoView_detailed_emergency);
        description=findViewById(R.id.txt_ems_description);
        reporter= findViewById(R.id.txt_ems_reporter);
        reporeted_date= findViewById(R.id.Txt_ems_report_date_reported);
        status= findViewById(R.id.txt_ems_status);

        Intent i = this.getIntent();
        String Description=i.getExtras().getString("Description");
        String Reporter=i.getExtras().getString("Reporter");
        String Date_Reported=i.getExtras().getString("Date Reported");
        String Status=i.getExtras().getString("Status");
        String VideoUrl=i.getExtras().getString("Video");
        Back_To_My_Reports=i.getExtras().getString("Back");
        uri = Uri.parse(VideoUrl);

        description.setText(Description);
        reporeted_date.setText(Date_Reported);
        reporter.setText(Reporter);
        status.setText(Status);

        progressDialog = new ProgressDialog(this);

        videoView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    videoView.setVideoURI(uri);
                    videoView.requestFocus();
                    videoView.start();
                }
                catch(Exception ex)
                {
                    Toast toast = Toast.makeText(Detailed_Emergency_Report.this, "No recorded video", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

       // Glide.with(getApplicationContext()).load(VideoUrl).;
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Report Details.....");
        progressDialog.show();
        progressDialog.dismiss();

        try
        {

            progressDialog.setTitle("Please wait");
            progressDialog.show();
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            progressDialog.setMessage("Loading Video.....");
            videoView.start();
        }
        catch(Exception ex)
        {
            Toast toast = Toast.makeText(Detailed_Emergency_Report.this, "No recorded video", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
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
                if (Back_To_My_Reports.equalsIgnoreCase("Back To My Reports Emergency Reports"))
                {
                    startActivity(new Intent(this,My_Emergency_Reports_Retrieve.class));
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

//    public void download(View view) {
//
//        try
//        {
//            final File localFile = File.createTempFile("userIntro", "3gp");
//
//            videoRef.getFile(localFile).addOnSuccessListener
//                    (
//                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
//                    {
//                        @Override
//                        public void onSuccess
//                                (
//                                FileDownloadTask.TaskSnapshot taskSnapshot)
//                        {
//
//                            Toast.makeText(Detailed_Emergency_Report.this, "Download complete", Toast.LENGTH_LONG).show();
//
//                            final VideoView videoView = (VideoView) findViewById(R.id.videoView);
//                            videoView.setVideoURI(Uri.fromFile(localFile));
//                            videoView.start();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener()
//            {
//                @Override
//                public void onFailure(@NonNull Exception e)
//                {
//                    Toast.makeText(Detailed_Emergency_Report.this, "Download failed: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
//        } catch (Exception e)
//        {
//            Toast.makeText(Detailed_Emergency_Report.this, "Failed to create temp file: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
}
