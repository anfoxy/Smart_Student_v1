package com.example.myapplication1.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.R;
import com.example.myapplication1.newPlan;

import java.util.List;

public class StateAdapter2 extends RecyclerView.Adapter<StateAdapter2.ViewHolder>{
  //  public interface OnStateClickListener{ void onStateClick(newPlan stateP, int position);}
    private final LayoutInflater inflater;
    private final List<newPlan> planToSubs;
    Dialog dialog;
    private  Context context;
  //  private final OnStateClickListener onClickListener;

    public StateAdapter2(Context context, List<newPlan> states ) {
   //    this.onClickListener = OnClickListener;
        this.planToSubs = states;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public StateAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter2.ViewHolder holder, int position) {

        newPlan state3 = planToSubs.get(position);
        if(state3.getInd()==-1){
            holder.planView.setText("Нет плана");
            holder.Otvet.setImageResource(R.drawable.ic_baseline_replay_24);

        }else {
            holder.planView.setText("Выучено " + state3.getKol() + " из " + MainActivity2.ArrPlan.get(state3.getInPredmeta()).getPlan_to_day().get(state3.getInd()).getSize_of_quetion());
            if (MainActivity2.ArrPlan.get(state3.getInPredmeta()).getPlan_to_day().get(state3.getInd()).getSize_of_quetion() <= MainActivity2.ArrPlan.get(state3.getInPredmeta()).getTodaylearned()) {
                holder.Otvet.setImageResource(R.drawable.ic_baseline_replay_24);
                holder.planView.setText("Выучено " + MainActivity2.ArrPlan.get(state3.getInPredmeta()).getPlan_to_day().get(state3.getInd()).getSize_of_quetion() + " из " + MainActivity2.ArrPlan.get(state3.getInPredmeta()).getPlan_to_day().get(state3.getInd()).getSize_of_quetion());
            }
        }
        holder.nameView.setText(state3.getName());
        holder.Otvet.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(state3.getInd()==-1){
                    //MainActivity2.num3=1;
                    bundle.putInt("code", 1);
                }else if(MainActivity2.ArrPlan.get(state3.getInPredmeta()).getPlan_to_day().get(state3.getInd()).getSize_of_quetion() <= MainActivity2.ArrPlan.get(state3.getInPredmeta()).getTodaylearned()){
                    //MainActivity2.num3=2;
                    bundle.putInt("code", 2);
                } else {
                    //MainActivity2.num3=0;
                    bundle.putInt("code", 0);
                }
               /* MainActivity2.num = state3.getInPredmeta();
                MainActivity2.num2 = state3.getInd();*/


                bundle.putInt("InPredmeta", state3.getInPredmeta());
                bundle.putInt("Ind", state3.getInd());
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_question,bundle);
            }
       });

    }


    @Override
    public int getItemCount() {
        return planToSubs.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final TextView planView;
        final ImageButton Otvet;
        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
            planView = (TextView) view.findViewById(R.id.plan);
            Otvet=(ImageButton) view.findViewById(R.id.otvet) ;


        }
    }
}