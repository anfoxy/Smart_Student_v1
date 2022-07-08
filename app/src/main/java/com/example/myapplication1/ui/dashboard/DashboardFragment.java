package com.example.myapplication1.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.R;
import com.example.myapplication1.adapter.StateAdapter;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    Button AddPlan;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity__v_plan, container, false);
        final  RecyclerView recyclerView = root.findViewById(R.id.list1);
        TextView text =root.findViewById(R.id.boolplan);
        if(MainActivity2.ArrPlan.size()==0){
            text.setText("Нет доступных предметов");
            text.setTextSize(34);
        } else {
            text.setText("");
            text.setTextSize(1);
        }

        StateAdapter adapter = new StateAdapter(getActivity(), MainActivity2.ArrPlan);
        recyclerView.setAdapter(adapter);
        AddPlan=(Button)root.findViewById(R.id.AddPlan);
        AddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_dashboard_to_navigation_addplan);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {

    }
}