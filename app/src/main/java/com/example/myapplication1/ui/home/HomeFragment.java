package com.example.myapplication1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.R;
import com.example.myapplication1.adapter.StateAdapter2;
import com.example.myapplication1.adapter.StateAdapterNowD;
import com.example.myapplication1.newPlan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HomeFragment extends Fragment implements View.OnClickListener{

TextView text;
int day1;
int month1;
int year1;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_main, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.list);
        ImageView im =(ImageView)  root.findViewById(R.id.imageView333);
        text = (TextView) root.findViewById(R.id.boolplan);
        CalendarView cw = (CalendarView) root.findViewById(R.id.calendarView3);
        Calendar a =new GregorianCalendar();
        day1 = a.get(Calendar.DATE);
        month1 =a.get(Calendar.MONTH);
        year1 = a.get(Calendar.YEAR);

        if(MainActivity2.ArrPlan.size()==0){
            Button b = (Button)root.findViewById(R.id.newplan);
            ViewGroup.LayoutParams params = b.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            b.setLayoutParams(params);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_navigation_addplan);
                }
            });
        }else {
            ArrayList<newPlan> states = new ArrayList<newPlan>();
            for (int i = 0; i < MainActivity2.ArrPlan.size(); i++) {
                int r = MainActivity2.ArrPlan.get(i).search_nom_plan(day1, month1, year1);
                states.add(new newPlan(MainActivity2.ArrPlan.get(i).getSubString(), r, MainActivity2.ArrPlan.get(i).gettodaylearned(), i));
            }
            if (states.size() == 0) {
                text.setText("Нет плана на данную дату");
                text.setTextSize(34);
                im.setVisibility(View.INVISIBLE);

            } else {
                text.setTextSize(0);
                im.setVisibility(View.VISIBLE);
            }

            StateAdapter2 adapter = new StateAdapter2(getContext(), states);
            recyclerView.setAdapter(adapter);


        }
        //обработка нажатия на календарь
        cw.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if(day1==dayOfMonth&&month1==month&&year==year1) {
                    ArrayList<newPlan> states1 = new ArrayList<newPlan>();
                    for (int i = 0; i < MainActivity2.ArrPlan.size(); i++) {
                        int r = MainActivity2.ArrPlan.get(i).search_nom_plan(dayOfMonth, month, year);
                            states1.add(new newPlan(MainActivity2.ArrPlan.get(i).getSubString(), r, MainActivity2.ArrPlan.get(i).gettodaylearned(), i));
                    }
                    im.setVisibility(View.VISIBLE);
                    if (states1.size() == 0) {
                        text.setText("Нет плана на данную дату");
                        im.setVisibility(View.INVISIBLE);
                        text.setTextSize(34);
                    } else {
                        text.setText("");
                        text.setTextSize(1);

                    }

                    StateAdapter2 adapter = new StateAdapter2(getActivity(), states1);
                    recyclerView.setAdapter(adapter);
                }else {
                    Date_simple d = new Date_simple(dayOfMonth,month,year);
                    Date_simple d1 = new Date_simple();

                    ArrayList<newPlan> states1 = new ArrayList<newPlan>();
                    for (int i = 0; i < MainActivity2.ArrPlan.size(); i++) {
                        int r = MainActivity2.ArrPlan.get(i).search_nom_plan(dayOfMonth, month, year);
                        if (r != -1) {
                            if(d.before(d1)) {
                                states1.add(new newPlan(MainActivity2.ArrPlan.get(i).getSubString(), -1, MainActivity2.ArrPlan.get(i).getPlan_to_day().get(r).getSize_of_quetionString(), i));
                            }else {
                                states1.add(new newPlan(MainActivity2.ArrPlan.get(i).getSubString(), r, MainActivity2.ArrPlan.get(i).getPlan_to_day().get(r).getSize_of_quetionString(), i));
                            }

                        }else{
                            if((MainActivity2.ArrPlan.get(i).getDay()==dayOfMonth)&&(MainActivity2.ArrPlan.get(i).getMonth()==month)&&(MainActivity2.ArrPlan.get(i).getYear()==year)) {
                                states1.add(new newPlan(MainActivity2.ArrPlan.get(i).getSubString(), r, "-10", i));
                            }
                        }
                    }
                    im.setVisibility(View.VISIBLE);
                    if (states1.size() == 0) {
                        im.setVisibility(View.INVISIBLE);
                        text.setText("Нет плана на данную дату");
                        text.setTextSize(34);
                    } else {
                        text.setText("");
                        text.setTextSize(1);
                    }

                    StateAdapterNowD adapter = new StateAdapterNowD(getActivity(), states1);
                    recyclerView.setAdapter(adapter);


                }
            }
        });


        return root;
    }

    @Override
    public void onClick(View v) {

    }
}