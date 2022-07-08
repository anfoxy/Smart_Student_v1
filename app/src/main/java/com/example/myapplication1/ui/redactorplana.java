package com.example.myapplication1.ui;

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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToDay;
import com.example.myapplication1.PlanToSub;
import com.example.myapplication1.Question;
import com.example.myapplication1.R;
import com.example.myapplication1.adapter.StateAdapter3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class redactorplana extends Fragment {
    private StateAdapter3.OnStateClickListener stateClickListener;
    private EditText mDisplayDate;
    private EditText name;
    private String date;
    private Date_simple d2;
    private ArrayList<Question> q;
    private int day2,month2,year2;
    private Dialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add2, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.listVop);
        Bundle b = getArguments();
        int pos = b.getInt("position");
        PlanToSub pl = (PlanToSub) b.getSerializable("pl");


        day2=pl.getDay();
        month2 = pl.getMonth();
        year2 =pl.getYear();
        q = pl.getQue();


        //нажатие на вопрос
        stateClickListener = new StateAdapter3.OnStateClickListener() {
            @Override
            public void onStateClick(Question state, int position) {
                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogque1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView textOtv = (TextView) dialog.findViewById(R.id.textOTVET);
                TextView textVop = (TextView) dialog.findViewById(R.id.textQUE);
                textOtv.setText(state.getAnswer());
                textVop.setText(state.getQuestion());

                //dialog.setCancelable(false);//нельзя закрыть кнопкой назад
                //кнопка добавление вопроса
                Button bt = (Button)dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //добавляем вопрос в массив вопросов
                        q.get(position).question=textVop.getText().toString();
                        q.get(position).answer=textOtv.getText().toString();

                        StateAdapter3 adapter = new StateAdapter3(getActivity(),q,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                //Кнопка удаления
                Button bt2 = (Button)dialog.findViewById(R.id.bdel);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        q.remove(position);
                        StateAdapter3 adapter = new StateAdapter3(getActivity(),q,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        };

        //Записываем имя
        name = (EditText) root.findViewById(R.id.Text1);
        name.setText(pl.getSubString());

        //записываем дату
        mDisplayDate = (EditText) root.findViewById(R.id.editTextDate);
        d2 = new Date_simple(day2, month2, year2);
        mDisplayDate.setText(pl.getDate());

        mDisplayDate.setInputType(InputType.TYPE_NULL);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogque3);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CalendarView cw = (CalendarView) dialog.findViewById(R.id.calendarView);

                cw.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        day2 = dayOfMonth;
                        month2 = month;
                        year2 = year;
                        d2 = new Date_simple(day2,month2,year2);
                        month = month + 1;
                        date = dayOfMonth + "/" + month + "/" + year;

                    }
                });
                //кнопка добавление даты
                Button bt = (Button)dialog.findViewById(R.id.set);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDisplayDate.setText(date);
                        dialog.dismiss();

                    }
                });
                //Кнопка отмены
                Button bt2 = (Button)dialog.findViewById(R.id.ot);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();//закрываем диалоговое окно
                    }
                });
                dialog.show();
            }
        });

        //выводим лист
        StateAdapter3 adapter = new StateAdapter3(getActivity(), q,stateClickListener);
        recyclerView.setAdapter(adapter);

        //редактировать план
        Button b1 = (Button)root.findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date_simple d = new  Date_simple();
                if (d2.before(d)) {
                    Toast toast = Toast.makeText(getContext(),
                            "Неверная дата", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pl",  pl);
                    bundle.putInt("position", pos);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_redactorplana_to_navigation_editing_plan,bundle);
                }
            }

        });




        //сохранить
        TextView addpl=(TextView)root.findViewById(R.id.save);
        addpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date_simple d = new  Date_simple();
                if (name.getText().toString().trim().isEmpty()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Введите название", Toast.LENGTH_SHORT);
                    toast.show();
                } else if(q.size()==0){
                    Toast toast = Toast.makeText(getContext(),
                            "Нет вопросов", Toast.LENGTH_SHORT);
                    toast.show();
                }else if (d2.before(d)) {
                        Toast toast = Toast.makeText(getContext(), "Неверная дата", Toast.LENGTH_SHORT);
                        toast.show();
                }  else {
                    int u = 0;
                    /* for (int i = 0; i < MainActivity2.num; i++) {
                        if (    MainActivity2.ArrPlan.get(i).sub.name_of_sub.equals(name.getText().toString())) u = 1;
                    }*/
                    for (int i = MainActivity2.ArrPlan.size()+1; i < MainActivity2.ArrPlan.size(); i++) {
                        if ( MainActivity2.ArrPlan.get(i).getSub().getName_of_sub().equals(name.getText().toString())) u = 1;
                    }if (u == 0) {
                        //заменяем вопросы
                        MainActivity2.ArrPlan.get(pos).getSub().setQuestion(new ArrayList<>(q));
                        MainActivity2.myDBManager.delete_QUE(MainActivity2.ArrPlan.get(pos).getSub().getName_of_sub());
                        for(int i=0;i<q.size();i++){
                            MainActivity2.myDBManager.insert_TABLE_QUE(MainActivity2.ArrPlan.get(pos).getSub().getName_of_sub(), q.get(i));
                        }
                        //изменение имени
                        MainActivity2.myDBManager.update_sub(name.getText().toString(), MainActivity2.ArrPlan.get(pos).getSub().getName_of_sub());
                        MainActivity2.ArrPlan.get(pos).getSub().setName_of_sub(name.getText().toString());

                        //измение даты экзамена
                        MainActivity2.ArrPlan.get(pos).change_date_of_exams(day2, month2, year2);
                        MainActivity2.myDBManager.update_date_ex(MainActivity2.ArrPlan.get(pos).getSub().getName_of_sub(), MainActivity2.ArrPlan.get(pos).getDate_of_exams());

                        //изменение плана
                        MainActivity2.ArrPlan.get(pos).deletePlanToDay();
                        MainActivity2.ArrPlan.get(pos).setPlan_to_day(pl.getPlan_to_day());
                        Calendar cal = new GregorianCalendar();
                        for(int i=0; i<pl.size_today_day_of_exams(); i++){
                            MainActivity2.myDBManager.delete_plantoday(MainActivity2.ArrPlan.get(pos).getSub().getName_of_sub(),new Date_simple(cal.get(Calendar.DATE), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));
                            cal.add(Calendar.DATE, 1);
                        }

                        MainActivity2.myDBManager.insert_TABLE_TODAY(MainActivity2.ArrPlan.get(pos));
                        Navigation.findNavController(view).navigate(R.id.action_navigation_redactorplana_to_navigation_dashboard);
                    }else {
                        Toast toast = Toast.makeText(getContext(), "предмет с данным названием уже существует", Toast.LENGTH_SHORT);
                        toast.show(); }
                }
            }
        });

        ImageButton del=(ImageButton)root.findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.del);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button bt = (Button)dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //Кнопка удаления
                Button bt2 = (Button)dialog.findViewById(R.id.bdel);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity2.myDBManager.delsub(MainActivity2.ArrPlan.get(pos).getSub().getName_of_sub());
                        MainActivity2.ArrPlan.remove(pos);
                        Navigation.findNavController(view).navigate(R.id.action_navigation_redactorplana_to_navigation_dashboard);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        // нажатие на кнопку добавление вопроса
        Button addQue = (Button) root.findViewById(R.id.b);
        addQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogque);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);//нельзя закрыть кнопкой назад
                //кнопка добавление вопроса
                Button bt = (Button)dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //добавляем вопрос в массив вопросов
                        TextView textOtv = (TextView) dialog.findViewById(R.id.textOTVET);
                        TextView textVop = (TextView) dialog.findViewById(R.id.textQUE);
                        Question vop= new Question(textVop.getText().toString(),textOtv.getText().toString());
                        q.add(vop);
                        StateAdapter3 adapter = new StateAdapter3(getActivity(),q,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                //Кнопка отмены
                Button bt2 = (Button)dialog.findViewById(R.id.bOtmena);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();//закрываем диалоговое окно
                    }
                });
                dialog.show();
            }
        });

        return root;
    }
}