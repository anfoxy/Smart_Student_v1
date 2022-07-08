package com.example.myapplication1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToSub;
import com.example.myapplication1.Question;
import com.example.myapplication1.R;
import com.example.myapplication1.Subject;
import com.example.myapplication1.adapter.StateAdapterPlanToDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditingPlan extends Fragment implements View.OnClickListener {
    private ArrayList<Date_simple> c;
    private int k;
    private StateAdapterPlanToDay adapter;
    private boolean p [];
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editing_plan, container, false);
        //принимаем данные с прошлой страницы
        Bundle b = getArguments();
        PlanToSub pl = (PlanToSub) b.getSerializable("pl");
        int pos = b.getInt("position");
        int n = pl.size_today_day_of_exams();

        final  RecyclerView recyclerView = root.findViewById(R.id.list1);
        ArrayList<String> date =new ArrayList<String>();

 /*       Calendar a =new GregorianCalendar();
        int day = a.get(Calendar.DATE);
        int month =a.get(Calendar.MONTH);
        int year = a.get(Calendar.YEAR);
   */

        Calendar cal = new GregorianCalendar();
        c = new ArrayList<Date_simple>();
        p= new boolean[n];
        for(int i=0; i<n; i++){
            c.add(new Date_simple(cal.get(Calendar.DATE), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));
            date.add(""+ cal.get(Calendar.DATE)+" "+getMonth(cal.get(Calendar.MONTH))+" "+ cal.get(Calendar.YEAR));
            cal.add(Calendar.DATE, 1);
            for(int j=0; j<pl.getPlan_to_day().size(); j++){
                if(c.get(i).checkDate(pl.getPlan_to_day().get(j).getDate())){
                p[i]=true;
                }
            }
        }


       // MainActivity2.p= new boolean[c.size()];

        adapter = new StateAdapterPlanToDay(getActivity(),date ,0, c, p);
        recyclerView.setAdapter(adapter);

        Button allplan = (Button) root.findViewById(R.id.all1);

        k=0;
        allplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(k==0) {
                    k=1;
                    adapter = new StateAdapterPlanToDay(getActivity(), date, 1, c);
                    recyclerView.setAdapter(adapter);
                }else {
                    k=0;
                    StateAdapterPlanToDay adapter = new StateAdapterPlanToDay(getActivity(), date, 0, c);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        Button addPlan = (Button) root.findViewById(R.id.AddPlan);
        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = adapter.getPlanArray();
                pl.deletePlanToDay();
                for (int i=0;i<c.size();i++){
                    if(p[i]){
                        pl.add_date_to_study(c.get(i).day,c.get(i).month,c.get(i).year);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("pl",  pl);
                bundle.putInt("position", pos);
                Navigation.findNavController(view).navigate(R.id.navigation_editing_plan_to_action_navigation_redactorplana,bundle);
                }
        });
        return root;
    }
    private String getMonth(int m){
    switch (m) {
        case 0:
            return "Января";
        case 1:
            return "Февраля";
        case 2:
            return "Марта";
        case 3:
            return "Апреля";
        case 4:
            return "Мая";
        case 5:
            return "Июня";
        case 6:
            return "Июля";
        case 7:
            return "Августа";
        case 8:
            return "Сентября";
        case 9:
            return "Октября";
        case 10:
            return "Ноября";
        case 11:
            return "Декабря";
        default:
            return "ERROR";
    }
}

    @Override
    public void onClick(View v) {

    }
}