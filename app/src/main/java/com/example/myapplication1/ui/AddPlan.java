package com.example.myapplication1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToSub;
import com.example.myapplication1.Question;
import com.example.myapplication1.R;
import com.example.myapplication1.adapter.StateAdapter3;
import com.example.myapplication1.Subject;
import com.example.myapplication1.code.addPlanCode;

import java.util.ArrayList;
import java.util.Calendar;


public class AddPlan extends Fragment implements View.OnClickListener  {
    private Button addQue;
    private Button addplan;
    private TextView mDisplayDate;
    private TextView textView;
    private StateAdapter3.OnStateClickListener stateClickListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int day2,month2,year2=-1;
    private Dialog dialog;
    private String date;
    private Date_simple d2;
    private ArrayList<Question> question ;
    private String name;
   // public static ArrayList<Question> question1 ;

 public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add, container, false);

        textView = (TextView) root.findViewById(R.id.Text1);
        mDisplayDate = (EditText) root.findViewById(R.id.editTextDate);
        final  RecyclerView recyclerView = root.findViewById(R.id.listVop);


     if (question == null) {
         question = new ArrayList<Question>();
     }
         //редактирование уже созданного вопроса
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
                        question.get(position).answer=textOtv.getText().toString();
                        question.get(position).question=textVop.getText().toString();
                        StateAdapter3 adapter = new StateAdapter3(getActivity(), question,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                //Кнопка отмены
                Button bt2 = (Button)dialog.findViewById(R.id.bdel);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        question.remove(position);
                        StateAdapter3 adapter = new StateAdapter3(getActivity(), question,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();//закрываем диалоговое окно
                    }
                });
                dialog.show();

            }
        };
        StateAdapter3 adapter = new StateAdapter3(getActivity(), question,stateClickListener);
        recyclerView.setAdapter(adapter);


       // нажатие на кнопку добавление вопроса
        addQue=(Button)root.findViewById(R.id.addque);
        addQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogque);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //dialog.setCancelable(false);//нельзя закрыть кнопкой назад
                //кнопка добавление вопроса
                Button bt = (Button)dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //добавляем вопрос в массив вопросов
                        TextView textOtv = (TextView) dialog.findViewById(R.id.textOTVET);
                        TextView textVop = (TextView) dialog.findViewById(R.id.textQUE);
                        Question vop= new Question(textVop.getText().toString(),textOtv.getText().toString());
                        question.add(vop);
                        StateAdapter3 adapter = new StateAdapter3(getActivity(), question,stateClickListener);

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

        // запись даты
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



        // кнопка добавления плана
        addplan=(Button)root.findViewById(R.id.AddPlan1);
        addplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2 = new Date_simple(day2,month2,year2);
                Date_simple d = new  Date_simple();
                if (day2 == -1 || month2 == -1 || year2 == -1) {
                    Toast toast = Toast.makeText(getContext(),
                            "Выберите дату", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (textView.getText().toString().trim().isEmpty()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Введите название", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (question.size() == 0) {
                    Toast toast = Toast.makeText(getContext(),
                            "Нет вопросов", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (d2.before(d)) {
                    Toast toast = Toast.makeText(getContext(),
                            "Неверная дата", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    int u = 0;
                    //проверка на одинаковое имя предметов
                    for (int i = 0; i < MainActivity2.ArrPlan.size(); i++) {
                        if (MainActivity2.ArrPlan.get(i).getSub().getName_of_sub().equals(textView.getText().toString())) {
                            u = 1;
                        }
                    }
                    //создание предмета
                    if (u == 0) {
                        name = textView.getText().toString();

                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        bundle.putInt("day", day2);
                        bundle.putInt("month", month2);
                        bundle.putInt("year", year2);
                        bundle.putSerializable("list",  question);
                        Navigation.findNavController(view).navigate(R.id.action_navigation_addplan_to_navigation_plantoday,bundle);
                    } else {
                        Toast toast = Toast.makeText(getContext(), "предмет с данным названием уже существует", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        return root;
    }




    @Override
    public void onClick(View v) {

    }
}