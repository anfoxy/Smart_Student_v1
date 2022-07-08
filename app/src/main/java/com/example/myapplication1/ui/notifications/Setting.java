package com.example.myapplication1.ui.notifications;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.R;

public class Setting extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.setting, container, false);
        EditText t = (EditText) root.findViewById(R.id.editTextDate);
        if(  MainActivity2.NotiMinuts< 10){
            t.setText(""+MainActivity2.NotiHours+":0"+MainActivity2.NotiMinuts);
        }else {
            t.setText(""+MainActivity2.NotiHours+":"+MainActivity2.NotiMinuts);
        }
        t.setInputType(InputType.TYPE_NULL);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_time);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TimePicker time = (TimePicker) dialog.findViewById(R.id.t1);
                time.setIs24HourView(true);
                //кнопка добавление даты
                Button bt = (Button)dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity2.NotiHours=time.getCurrentHour();
                        MainActivity2.NotiMinuts=time.getCurrentMinute();

                        if(  MainActivity2.NotiMinuts< 10){
                            t.setText(""+MainActivity2.NotiHours+":0"+MainActivity2.NotiMinuts);
                        }else {
                            t.setText(""+MainActivity2.NotiHours+":"+MainActivity2.NotiMinuts);
                        }

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Switch s =(Switch) root.findViewById(R.id.switchWidget);
        if(MainActivity2.myDBManager.getNoti()==1){
            s.setChecked(true);
        }else {
            s.setChecked(false);
        }
        Button b = (Button) root.findViewById(R.id.AddPlan1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s.isChecked()){
                    MainActivity2.myDBManager.update_timeNOTI(1);
                }else {
                    MainActivity2.myDBManager.update_timeNOTI(0);
                }
                MainActivity2.myDBManager.update_time(MainActivity2.NotiHours,  MainActivity2.NotiMinuts);
                Navigation.findNavController(view).navigate(R.id.action_Setting_to_navigation_notifications);

            }
        });

        return root;
    }

}