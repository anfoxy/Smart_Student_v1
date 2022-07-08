package com.example.myapplication1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Question;
import com.example.myapplication1.R;

import java.util.List;

public class StateAdapter3 extends RecyclerView.Adapter<StateAdapter3.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Question> questions;
    private Context context;
    private final OnStateClickListener onClickListener;
    public interface OnStateClickListener{
        void onStateClick(Question questions, int position);
    }

    public StateAdapter3(Context context, List<Question> states, OnStateClickListener onClickListener) {
        this.questions = states;
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public StateAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter3.ViewHolder holder, int position) {
        Question state3 = questions.get(position);
        if(state3.question.length()-1>19) {
            holder.nameView.setText(state3.question.substring(0,20)+" ...");
        } else
            holder.nameView.setText(state3.question);

        holder.k1.setOnClickListener(new OnClickListener(){
            public void onClick(View v)
            {
                onClickListener.onStateClick(state3, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return questions.size();
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