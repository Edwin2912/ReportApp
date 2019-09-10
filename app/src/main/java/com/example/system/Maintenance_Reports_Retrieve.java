package com.example.system;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Maintenance_Reports_Retrieve extends AppCompatActivity
{

    DatabaseReference database;
    ListView Maintenance_reports_list_list_view;
    List<Maintenance> userlist;
    ArrayList<Maintenance>list;
    FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
    ArrayAdapter<Maintenance>adaptert;
    Maintenance emergency_report ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_reports_retrieve);

        Maintenance_reports_list_list_view = findViewById(R.id.Emergency_Reports_List);
        firebaseDatabase=FirebaseDatabase.getInstance();
        database=firebaseDatabase.getReference("Maintenance Reports");

        userlist = new ArrayList<>();
        progressDialog = new ProgressDialog(this);


        Maintenance_reports_list_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Maintenance maintenance_report = userlist.get(i);
                Intent in = new Intent(Maintenance_Reports_Retrieve.this, Detailed_Maintenance_Report.class);
                in.putExtra("Description",maintenance_report.getDescription());
                in.putExtra("Message",maintenance_report.getMessage());
                in.putExtra("Reporter",maintenance_report.getReporter());
                in.putExtra("Status",maintenance_report.getStatus());
                in.putExtra("ImageUrl",maintenance_report.getimageUrl());


                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Reports.....");
        progressDialog.show();


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userlist.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    Maintenance user = userSnapshot.getValue(Maintenance.class);
                    userlist.add(user);
                }
                Maintenance_Report_list usersListAdapter = new Maintenance_Report_list(Maintenance_Reports_Retrieve.this, userlist);
                Maintenance_reports_list_list_view.setAdapter(usersListAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

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
                startActivity(new Intent(this,Select_Report.class));
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
        Toast toast = new Toast(Maintenance_Reports_Retrieve.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }

}
