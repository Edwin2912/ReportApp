package com.example.system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.system.Emergency_Report.SEND_SMS_PERMISSION_REQUEST_CODE;


public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    Context context=this;
    private ActionBarDrawerToggle mToggle;
    private long backPressedTime;
    String User_name;
    TextView userEmail;
    Button button_uj_chat,blackboard,career24;
    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(com.example.system.R.layout.activity_home_);
        mDrawerLayout = (DrawerLayout) findViewById(com.example.system.R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, com.example.system.R.string.open, com.example.system.R.string.close);
        mAuth = FirebaseAuth.getInstance();
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        lvRss = (ListView) findViewById(R.id.lvRss);
        userEmail = findViewById(R.id.Current_user_email);
        button_uj_chat= findViewById(R.id.imageView6);
        blackboard= findViewById(R.id.btn_blackboard);
        career24= findViewById(R.id.btn_career24);


        titles = new ArrayList<String>();
        links = new ArrayList<String>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(com.example.system.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder(user.getUid().toString()+"a@gmail.com")
                .setMessageId(Integer.toString(0))
                .addData("my message","hello")
                .addData("my action","test 1").build());

        if (user != null)
        {
             User_name = user.getEmail();
            String User_email = user.getEmail();
            String User_studentNr = user.getUid();
            String User_PhoneNr = user.getPhoneNumber();

            // Uri photoUrl = user.getPhotoUrl();
            // Check if user's email is verified

            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Welcome : " + User_name);

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        button_uj_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.uj.ac.za/Pages/uj-chat.aspx"));
                startActivity(intent);
               overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
            }
        });
        career24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.careers24.com/"));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
            }
        });
        blackboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://uj.blackboard.com/ultra/stream"));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
            }
        });

    }


    public void runtimeEnableAutoInit() {
        // [START fcm_runtime_enable_auto_init]
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        // [END fcm_runtime_enable_auto_init]
    }

    public void deviceGroupUpstream() {
        // [START fcm_device_group_upstream]
        String to = "General"; // the notification key
        AtomicInteger msgId = new AtomicInteger();
        FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(to)
                .setMessageId(String.valueOf(msgId.get()))
                .addData("hello", "world")
                .build());
        // [END fcm_device_group_upstream]
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId()== R.id.nav_help_1)
        {
            Emergency_Contacts();
//            Intent help_1 = new Intent(context,Help.class);
//            startActivity(help_1);
//            overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
        }

        if (item.getItemId()==R.id.nav_report_Maintenance_1)
        {
            Intent report_Maintenance = new Intent(context,Maintenance_Report.class);
            startActivity(report_Maintenance);
            overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
        }



        if (item.getItemId()==R.id.nav_logout)
        {
            Intent logout = new Intent(context,Login_Activity.class);
            mAuth.signOut();
            Toast("Signed Out..Bye Bye");
            startActivity(logout);
            overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);

        }

        if (item.getItemId()==R.id.nav_Report_Status)
    {
        Intent Select_Report = new Intent(context, Select_Report.class);
        startActivity(Select_Report);
        overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);

    }


        if (item.getItemId()==R.id.nav_report__Emergency)
        {
            Intent Select_Report = new Intent(context, Emergency_Report.class);
            startActivity(Select_Report);
            overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
        }

//        if (item.getItemId()==R.id.nav_Blackboard)
//        {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("https://uj.blackboard.com/ultra/stream"));
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
//
//        }

        if (item.getItemId()==R.id.nav_Users)
        {
            Intent users = new Intent(context, Users.class);
            startActivity(users);
            overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
        }

        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed()
    {
       // super.onBackPressed();
        if (backPressedTime +1000 >System.currentTimeMillis())
        {
            super.onBackPressed();
            showDialog();
            //return;
        }
        else
        {
            showDialog();
        }
        backPressedTime=System.currentTimeMillis();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        if ( item.getItemId()== com.example.system.R.id.nav_profile_picture)
        {
            Intent help_1 = new Intent(this,Profile.class);
            startActivity(help_1);
            overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
        }
        return super.onOptionsItemSelected(item);
    }
    public void Toast (String msg)
    {
        View view=getLayoutInflater().inflate(R.layout.toast_uj,(ViewGroup) findViewById(R.id.toast_uj));
        TextView text=(TextView)view.findViewById(R.id.textToast);
        text.setText(msg);
        Toast toast = new Toast(Home_Activity.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        toast.show();
    }


    void showDialog()
    {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alret_close_app, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();

        Button acceptButton = view.findViewById(R.id.Button_Yes);
        Button cancelButton = view.findViewById(R.id.Button_No);

        acceptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Log.e(TAG, "onClick: cancel button" );
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    void Emergency_Contacts()
    {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.emergency_contacts_layout, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light).setView(view).create();
        Button close=view.findViewById(R.id.btn_close_dialog);

        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                alertDialog.dismiss();
            }
        });

        TextView PsyCaD = view.findViewById(R.id.textView43);
        TextView Flying_Squad = view.findViewById(R.id.textView44);
        TextView APB_Health = view.findViewById(R.id.textView30);
        TextView APK_Health = view.findViewById(R.id.textView31);
        TextView DFC_Health = view.findViewById(R.id.textView32);
        TextView SWC_Health = view.findViewById(R.id.textView33);
        TextView APB_Protection = view.findViewById(R.id.textView29);
        TextView APK_Protection = view.findViewById(R.id.textView34);
        TextView DFC_Protection = view.findViewById(R.id.textView35);
        TextView SWC_Protection = view.findViewById(R.id.textView38);
        TextView Netcare = view.findViewById(R.id.textView37);
        TextView Milpark = view.findViewById(R.id.textView39);
        TextView Garden = view.findViewById(R.id.textView42);
        TextView Helen = view.findViewById(R.id.textView41);
        TextView Melden = view.findViewById(R.id.textView51);

        PsyCaD.setText(R.string.PsyCaD_crisis_Tell);
        Flying_Squad.setText(R.string.Flying_squad_crisis_Tell);
        APB_Health.setText(R.string.APB_health_Tell);
        APK_Health.setText(R.string.APK_health_Tell);
        DFC_Health.setText(R.string.DFC_health_Tell);
        SWC_Health.setText(R.string.SWC_health_Tell);
        APB_Protection.setText(R.string.APB_protection_Tell);
        APK_Protection.setText(R.string.APK_protection_Tell);
        DFC_Protection.setText(R.string.DFC_protection_Tell);
        SWC_Protection.setText(R.string.SWC_protection_Tell);
        Netcare.setText(R.string.NetcareHospital_Tell);
        Milpark.setText(R.string.Milpark_Hospital_Tell);
        Garden.setText(R.string.Garden_Hospital_Tell);
        Helen.setText(R.string.Helen_Joseph_Hospital_Tell);
        Melden.setText(R.string.Meldene_Medicross_Hospital__Tell);


        String PsyCaD_tel = PsyCaD.getText().toString();
        String flying_squad = Flying_Squad.getText().toString();
        String apb_Health = APB_Health.getText().toString();
        String apk_Health = APK_Health.getText().toString();
        String dfc_Health = DFC_Health.getText().toString();
        String swc_Health = SWC_Health.getText().toString();
        String apb_Protection = APB_Protection.getText().toString();
        String apk_Protection = APK_Protection.getText().toString();
        String dfc_Protection = DFC_Protection.getText().toString();
        String swc_Protection = SWC_Protection.getText().toString();
        String netcare = Netcare.getText().toString();
        String milpark = Milpark.getText().toString();
        String garden = Garden.getText().toString();
        String helen = Helen.getText().toString();
        String melden = Melden.getText().toString();


        PsyCaD.setText(Html.fromHtml("PsyCaD : <a href="+PsyCaD_tel+">"+PsyCaD_tel+"</a>"));
        PsyCaD.setMovementMethod(LinkMovementMethod.getInstance());


        Flying_Squad.setText(Html.fromHtml("Flying Squad : <a href="+flying_squad+">"+flying_squad+"</a>"));
        Flying_Squad.setMovementMethod(LinkMovementMethod.getInstance());

        APB_Health.setText(Html.fromHtml("APB health : <a href="+apb_Health+">"+apb_Health+"</a>"));
        APB_Health.setMovementMethod(LinkMovementMethod.getInstance());

        APK_Health.setText(Html.fromHtml("APK health : <a href="+apk_Health+">"+apk_Health+"</a>"));
        APK_Health.setMovementMethod(LinkMovementMethod.getInstance());

        DFC_Health.setText(Html.fromHtml("DFC health : <a href="+dfc_Health+">"+dfc_Health+"</a>"));
        DFC_Health.setMovementMethod(LinkMovementMethod.getInstance());

        SWC_Health.setText(Html.fromHtml("SWC health : <a href="+swc_Health+">"+swc_Health+"</a>"));
        SWC_Health.setMovementMethod(LinkMovementMethod.getInstance());

        APB_Protection.setText(Html.fromHtml("APB protection : <a href="+apb_Protection+">"+apb_Protection+"</a>"));
        APB_Protection.setMovementMethod(LinkMovementMethod.getInstance());

        APK_Protection.setText(Html.fromHtml("APK protection : <a href="+apk_Protection+">"+apk_Protection+"</a>"));
        APK_Protection.setMovementMethod(LinkMovementMethod.getInstance());

        DFC_Protection.setText(Html.fromHtml("DFC protection : <a href="+dfc_Protection+">"+dfc_Protection+"</a>"));
        DFC_Protection.setMovementMethod(LinkMovementMethod.getInstance());

        SWC_Protection.setText(Html.fromHtml("SWC protection : <a href="+swc_Protection+">"+swc_Protection+"</a>"));
        SWC_Protection.setMovementMethod(LinkMovementMethod.getInstance());

        Netcare.setText(Html.fromHtml("Netcare Hospital : <a href="+netcare+">"+netcare+"</a>"));
        Netcare.setMovementMethod(LinkMovementMethod.getInstance());

        Milpark.setText(Html.fromHtml("Milpark Hospital : <a href="+milpark+">"+milpark+"</a>"));
        Milpark.setMovementMethod(LinkMovementMethod.getInstance());

        Garden.setText(Html.fromHtml("Garden Hospital : <a href="+garden+">"+garden+"</a>"));
        Garden.setMovementMethod(LinkMovementMethod.getInstance());

        Helen.setText(Html.fromHtml("Helen Joseph Hospital : <a href="+helen+">"+helen+"</a>"));
        Helen.setMovementMethod(LinkMovementMethod.getInstance());

        Melden.setText(Html.fromHtml("Meldene Medicross Hospital : <a href="+melden+">"+melden+"</a>"));
        Melden.setMovementMethod(LinkMovementMethod.getInstance());

        alertDialog.show();
    }

    //////////////////////////////////////////////





}
