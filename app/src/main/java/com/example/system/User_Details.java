package com.example.system;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class User_Details extends AppCompatActivity {

    TextView student_nr,email,phone_nr,role;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__details);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.uj_logo_);
        actionBar.setTitle("User Details");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        student_nr = findViewById(R.id.Txt_User_Student_Nr);
        email = findViewById(R.id.Txt_Descff);
        phone_nr = findViewById(R.id.Txt_Stu_Phone_Nr);
        role = findViewById(R.id.Txt_Role);

        Intent i = this.getIntent();
        String stu_num=i.getExtras().getString("Student_Nr");
        String stu_email=i.getExtras().getString("Email");
        String stu_phone_num=i.getExtras().getString("Phone_Nr");
        String stu_role=i.getExtras().getString("Role");
        student_nr.setText(stu_num);
        email.setText(stu_email);
        phone_nr.setText(stu_phone_num);
        role.setText(stu_role);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.update,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.Back_ed:
                Intent intent = new Intent(this, Users.class);
                //intent.putExtra("key",2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;
            case R.id.Update_Details:
                Intent update_user = new Intent(this, Users.class);
                //intent.putExtra("key",2);
               // startActivity(update_user);
               // overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;

            case R.id.Delete_Details:
                Intent delete_user = new Intent(this, Users.class);
                //intent.putExtra("key",2);
               // startActivity(delete_user);
                //overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
