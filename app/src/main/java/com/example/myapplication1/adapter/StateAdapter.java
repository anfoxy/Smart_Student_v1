package com.example.myapplication1.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.PlanToSub;
import com.example.myapplication1.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<PlanToSub> planToSubs;

    public StateAdapter(Context context, List<PlanToSub> states) {
        this.planToSubs = states;
        this.inflater = LayoutInflater.from(context);
    }
    @NotNull
    public StateAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        PlanToSub state3 = planToSubs.get(position);
        holder.nameView.setText(state3.getSubString());
        holder.k1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
              //  MainActivity2.num= position;
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                Navigation.findNavController(v).navigate(R.id.action_navigation_dashboard_to_navigation_statistic,bundle);

            }
        });
    }
    @Override
    public int getItemCount() {
        return planToSubs.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final LinearLayout k1;
        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
            k1 = (LinearLayout) view.findViewById(R.id.k1);

        }
    }
}