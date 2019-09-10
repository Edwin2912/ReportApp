package com.example.system;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlay extends AppCompatActivity {

    private VideoView myVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        ActionBar actionBar=getSupportActionBar();
         actionBar.setIcon(R.mipmap.uj_logo_);
        actionBar.setTitle("Up load Video");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        myVideo= findViewById(R.id.videoView);
        Uri  videoUri=  Uri.parse(getIntent().getExtras().getString("videoUri"));
        myVideo.setVideoURI(videoUri);
        myVideo.start();

        myVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Uri  videoUri=  Uri.parse(getIntent().getExtras().getString("videoUri"));
                myVideo.setVideoURI(videoUri);
                myVideo.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.back,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_back:
                Intent intent = new Intent(this, Home_Activity.class);
                //intent.putExtra("key",2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Play_Video(View v)
    {
        myVideo= findViewById(R.id.videoView);
        Uri  videoUri=  Uri.parse(getIntent().getExtras().getString("videoUri"));
        myVideo.setVideoURI(videoUri);
        myVideo.start();

    }

    public void Toast (String msg)
    {
        View view=getLayoutInflater().inflate(R.layout.toast_uj,(ViewGroup) findViewById(R.id.toast_uj));
        TextView text=(TextView)view.findViewById(R.id.textToast);
        text.setText(msg);
        Toast toast = new Toast(VideoPlay.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        toast.show();
    }
}
