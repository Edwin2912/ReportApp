package com.example.system;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Help extends AppCompatActivity
{

    Button h;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.uj_logo_);
        actionBar.setTitle("Help");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//        h=(findViewById(R.id.button_frag_1));
//
//        h.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://www.uj.ac.za/newandevents/_layouts/15/listfeed.aspx?List={A1F3810B-9D03-40D5-B818-9425DC169450}&Source=https://www.uj.ac.za/newandevents/Pages/Forms/AllItems.aspx"));
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
//            }
//        });












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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Toast (String msg)
    {
        View view=getLayoutInflater().inflate(R.layout.toast_uj,(ViewGroup) findViewById(R.id.toast_uj));
        TextView text=(TextView)view.findViewById(R.id.textToast);
        text.setText(msg);
        Toast toast = new Toast(Help.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        toast.show();
    }


}
