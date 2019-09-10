package com.example.system;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserList extends ArrayAdapter<User>
{
    private Activity context;
    List<User> users;

    public UserList(Activity context, List<User> tracks)
    {
        super(context, R.layout.custom_list, tracks);
        this.context = context;
        this.users = tracks;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.custom_list,parent,false);
        }
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_list, null, true);

        TextView textStudentNr =  listViewItem.findViewById(R.id._description);
        TextView textEmail = listViewItem.findViewById(R.id.txt_userEmail);
        TextView textPhoneNr = listViewItem.findViewById(R.id.txt_user_phoneNr);
        TextView textUserRole =listViewItem.findViewById(R.id.txt_user_Role);
        //txtUserRole

        User user = users.get(position);
        User user2 = (User)this.getItem(position);

        textStudentNr.setText(user.getStudentNr());
        textEmail.setText(String.valueOf(user.getEmail()));
        textPhoneNr.setText(String.valueOf(user.getPhoneNr()));
        textUserRole.setText(String.valueOf(user.getRole()));


        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent i = new Intent(context, User_Details.class);
                i.putExtra("Student_Nr",user.getStudentNr().toString());
                context.startActivity(i);
                context.overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
            }
        });
        return listViewItem;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}

