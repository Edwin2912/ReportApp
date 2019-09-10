package com.example.system;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Sello extends AppCompatActivity {


    DatabaseReference database;
    ListView sello;
    List<Maintenance> userlist;
    ArrayList<Maintenance>list;
    FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
    ArrayAdapter<Maintenance> adaptert;
    Maintenance emergency_report ;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sello);


        sello = findViewById(R.id.sello);
        firebaseDatabase= FirebaseDatabase.getInstance();
        database=firebaseDatabase.getReference("Maintenance Reports");

        userlist = new ArrayList<>();
        progressDialog = new ProgressDialog(this);


        sello.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Maintenance maintenance_report = userlist.get(i);
                Intent in = new Intent(Sello.this, Detailed_Maintenance_Report.class);
                in.putExtra("Description",maintenance_report.getDescription());
                in.putExtra("Message",maintenance_report.getMessage());
                in.putExtra("Reporter",maintenance_report.getReporter());
                in.putExtra("Status",maintenance_report.getStatus());
                in.putExtra("ImageUrl",maintenance_report.getimageUrl());
                in.putExtra("Back","Back To My Reports Maintenance Reports");



                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);

            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        try
        {
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Loading Reports..");
            progressDialog.show();

            user = FirebaseAuth.getInstance().getCurrentUser();
            String email=user.getEmail().toString();
            Query query=firebaseDatabase.getReference("Maintenance Reports").orderByChild("reporter").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    userlist.clear();
                    if (dataSnapshot.exists())
                    {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                        {
                            Maintenance user = userSnapshot.getValue(Maintenance.class);
                            userlist.add(user);
                        }
                        Maintenance_Report_list usersListAdapter = new Maintenance_Report_list(Sello.this, userlist);
                        sello.setAdapter(usersListAdapter);
                        progressDialog.dismiss();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast("!!! Data is not found !!!");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch(Exception ex)
        {

            Toast("Something went wrong");
        }

    }

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
        Toast toast = new Toast(Sello.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }
}
