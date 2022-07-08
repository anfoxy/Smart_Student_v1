package com.example.myapplication1.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.R;

public class NotificationsFragment extends Fragment implements View.OnClickListener{

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


        LinearLayout k1 =(LinearLayout) root.findViewById(R.id.k1);
        k1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_notifications2_to_o_prilo);
            }
        });
        LinearLayout k2 =(LinearLayout) root.findViewById(R.id.k2);
        k2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_notifications2_to_Setting);
            }
        });
        LinearLayout k3 =(LinearLayout) root.findViewById(R.id.k3);
        k3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_notifications2_to_Contact);
            }
        });



        return root;
    }


    @Override
    public void onClick(View v) {

    }
}