package com.example.system;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Emergency_Message  extends AppCompatActivity implements LocationListener
{
    public  final static  int SELECT_CONTACT=111;
    final int PICK_CONTACT = 1;
    TextView Contact_Name,Contact_Number;
    RadioButton spinner_EMS_all_contacts;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat,txtLong;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency__message);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.uj_logo_);
        actionBar.setTitle("Emergency Message");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Contact_Name=(TextView)findViewById(R.id.textView80);
        Contact_Number=(TextView)findViewById(R.id.textView81);
        spinner_EMS_all_contacts=(RadioButton)findViewById(R.id.spinner_EMS_all_contacts);


        if (checkPermission(android.Manifest.permission.READ_CONTACTS))
        {
        }
        else
        {
            ActivityCompat.requestPermissions(Emergency_Message.this,new String[]{Manifest.permission.READ_CONTACTS},SELECT_CONTACT);
        }

        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
        {
            txtLat = (TextView) findViewById(R.id.textView83);
            txtLong = (TextView) findViewById(R.id.textView84);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        else
        {
            ActivityCompat.requestPermissions(Emergency_Message.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},SELECT_CONTACT);
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        txtLat = (TextView) findViewById(R.id.textView83);
        txtLong = (TextView) findViewById(R.id.textView84);
        txtLat.setText("Latitude:" + location.getLatitude() );
        txtLong.setText( "Longitude:" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Log.d("Latitude","status");
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
            case R.id.Back:
                startActivity(new Intent(this,Login_Activity.class));
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPermission(String permission)
    {
        int check= ContextCompat.checkSelfPermission(this,permission);
        return check== PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case SELECT_CONTACT:
                if (grantResults.length>0 && (grantResults[0]==PackageManager.PERMISSION_GRANTED))
                {
                }
                break;
        }
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void btnSend(View v)//sending via email
    {

        if (spinner_EMS_all_contacts.isChecked())
        {
            String[] to = new String[]{"selloedwin70@gmail.com","smabe@dvt.co.za"};
            String subuject=("aweee ma se kind its going down");
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(email.EXTRA_EMAIL,to);
            email.putExtra(email.EXTRA_SUBJECT,subuject);
            email.putExtra(email.EXTRA_TEXT,"aweee ma se kind");
            email.setType("message/rfc822");
            startActivity(email.createChooser(email,"Send Email"));
        }
        else
        {

        }


/*
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(intent.EXTRA_EMAIL,new String [] {"edwin70@gmail.com"+" "+"mmamam@gmail.com"});
        startActivity(intent.createChooser(intent,"Send Email"));
        */
    }
    public void btnSelectContact(View v)//sending via email
    {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent,1);
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode)
        {
            case (PICK_CONTACT):

                if (checkPermission(Manifest.permission.READ_CONTACTS))
                {
                    if (resultCode == Activity.RESULT_OK)
                    {
                        Uri contactData = data.getData();
                        Cursor c = managedQuery(contactData, null, null, null, null);
                        if (c.moveToFirst())
                        {
                            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                            if (hasPhone.equalsIgnoreCase("1"))
                            {
                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                phones.moveToFirst();
                                String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                String cName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                // Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
                               // Toast("Numbers selected are " +cNumber );
                                Contact_Number.setText(cNumber);
                                Contact_Name.setText(cName);
                            }
                        }
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(Emergency_Message.this,new String[]{Manifest.permission.READ_CONTACTS},SELECT_CONTACT);
                    // Toast("Some fucked up shit just happened  "  );
                }
        }//end of switch statement
    }//end of onActivityResult

//    public void Toast (String msg)
//    {
//        View view=getLayoutInflater().inflate(R.layout.toast,(ViewGroup) findViewById(R.id.linlay));
//        TextView text=(TextView)view.findViewById(R.id.textToast);
//        text.setText(msg);
//        Toast toast = new Toast(Emergency_Message.this);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(view);
//        toast.setGravity(Gravity.BOTTOM| Gravity.FILL_HORIZONTAL,0,0);
//        toast.show();
//    }

}
