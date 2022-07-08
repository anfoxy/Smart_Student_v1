package com.example.myapplication1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.R;
import com.example.myapplication1.newPlan;

import java.util.List;

public class StateAdapterNowD extends RecyclerView.Adapter<StateAdapterNowD.ViewHolder>{
  //  public interface OnStateClickListener{ void onStateClick(newPlan stateP, int position);}
    private final LayoutInflater inflater;
    private final List<newPlan> planToSubs;
  //  private final OnStateClickListener onClickListener;

    public StateAdapterNowD(Context context, List<newPlan> states ) {
   //    this.onClickListener = OnClickListener;
        this.planToSubs = states;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public StateAdapterNowD.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_itemnowd, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapterNowD.ViewHolder holder, int position) {
        newPlan state3 = planToSubs.get(position);

        holder.nameView.setText(state3.getName());
        if(state3.getKol()=="-10"){
            holder.planView.setText("Экзамен");
        }
        else
            if(state3.getInd()==-1){
                holder.planView.setText("Вопросов было изучено : "+state3.getKol());

            }else {
                holder.planView.setText("Вопросов надо изучить: " + state3.getKol());
            }
    }

    @Override
    public int getItemCount() {
        return planToSubs.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final TextView planView;
        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
            planView = (TextView) view.findViewById(R.id.plan);


        }
    }
}