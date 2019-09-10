package com.example.system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class Users  extends AppCompatActivity
{

    DatabaseReference databaseUsers;
    ListView listViewUsers;
    List<User> userlist;
    private ProgressDialog progressDialog;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        listViewUsers = (ListView) findViewById(R.id.Users_list);
        userlist = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        databaseUsers = FirebaseDatabase.getInstance().getReference("user");
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                User user = userlist.get(i);
                Intent intent = new Intent(Users.this, User_Details.class);
                intent.putExtra("Student_Nr",user.getStudentNr().toString());
                intent.putExtra("Email",user.getEmail().toString());
                intent.putExtra("Phone_Nr",user.getPhoneNr().toString());
                intent.putExtra("Role",user.getRole().toString());

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Users.....");
        progressDialog.show();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userlist.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    userlist.add(user);
                }
                UserList usersListAdapter = new UserList(Users.this, userlist);
                listViewUsers.setAdapter(usersListAdapter);
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
        Toast toast = new Toast(Users.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }




}
