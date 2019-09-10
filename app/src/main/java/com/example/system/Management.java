package com.example.system;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;



public class Management extends Fragment {

    ListView Users_List_View;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState)
    {

       // Users_List_View = (ListView) fin;

        return inflater.inflate(R.layout.fragment_management,container,false);
    }
}
