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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Emergency_Reports_Retrieve extends AppCompatActivity
{

    DatabaseReference database;
    ListView emergency_reports_list_list_view;
    List<Emergency> userlist;
    ArrayList<Emergency>list;
    FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
    ArrayAdapter<Emergency>adaptert;
    Emergency emergency_report ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_reports_retrieve);

        emergency_reports_list_list_view = findViewById(R.id.Emergency_Reports_List);
        firebaseDatabase=FirebaseDatabase.getInstance();
        database=firebaseDatabase.getReference("Emergency Reports");

        userlist = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.mipmap.report);
        actionBar.setTitle("Emergency Reports");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        emergency_reports_list_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Emergency emergency_report = userlist.get(i);
                Intent in = new Intent(Emergency_Reports_Retrieve.this, Detailed_Emergency_Report.class);
                in.putExtra("Description",emergency_report.getDescription());
                in.putExtra("Date Reported",emergency_report.getDate_reported());
                in.putExtra("Reporter",emergency_report.getReporter());
                in.putExtra("Status",emergency_report.getStatus());
                in.putExtra("Video",emergency_report.getVideo());
                in.putExtra("Back","Back To Emergency Reports");

                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Reports....");
        progressDialog.show();


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userlist.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    Emergency user = userSnapshot.getValue(Emergency.class);
                    userlist.add(user);
                }
                Emergency_Report_list usersListAdapter = new Emergency_Report_list(Emergency_Reports_Retrieve.this, userlist);
                emergency_reports_list_list_view.setAdapter(usersListAdapter);
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
    @Override
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
        Toast toast = new Toast(Emergency_Reports_Retrieve.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }

}
