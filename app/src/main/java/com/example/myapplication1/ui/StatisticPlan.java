package com.example.myapplication1.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToSub;
import com.example.myapplication1.R;


public class StatisticPlan extends Fragment implements View.OnClickListener{

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_statistic_plan, container, false);
        Button redactorplana=(Button)root.findViewById(R.id.button1);
        TextView kolvop=(TextView)root.findViewById(R.id.kolvop);
        TextView kolzap=(TextView)root.findViewById(R.id.kolzap);
        TextView dateob=(TextView)root.findViewById(R.id.dateob);
        TextView dateex=(TextView)root.findViewById(R.id.dateex);
        TextView koldex=(TextView)root.findViewById(R.id.kold);

        Bundle b = getArguments();
        int pos = b.getInt("position");

        kolvop.setText("Количество вопросов:  "+MainActivity2.ArrPlan.get(pos).getSub().get_size_all_quest());
        kolzap.setText("Количество выученных вопросов:  "+MainActivity2.ArrPlan.get(pos).getSub().get_size_know());
        koldex.setText("Количество дней до экзамена:  "+MainActivity2.ArrPlan.get(pos).size_today_day_of_exams());
        dateex.setText("Дата экзамена:  "+MainActivity2.ArrPlan.get(pos).getDate());
        dateob.setText("Количество невыученных вопросов:  "+MainActivity2.ArrPlan.get(pos).getSub().get_size_no_know());


        redactorplana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlanToSub pl = new PlanToSub(MainActivity2.ArrPlan.get(pos));
                Bundle bundle = new Bundle();
                bundle.putInt("position", pos);
                bundle.putSerializable("pl",  pl);
                Navigation.findNavController(view).navigate(R.id.action_navigation_statistic_to_navigation_redactorplana,bundle);
            }
        });
        StringBuilder str = new StringBuilder();
        for(int i =0; i< MainActivity2.ArrPlan.get(pos).getPlan_to_day().size();i++){
            str.append("").append(MainActivity2.ArrPlan.get(pos).getPlan_to_day().get(i).getDate().getDate()).append("\n");
        }
        Log.v("TAG_PLAN", str.toString());
        return root;
    }
    @Override
    public void onClick(View v) {

    }
}