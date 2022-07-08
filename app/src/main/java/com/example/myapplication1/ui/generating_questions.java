package com.example.myapplication1.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToDay;
import com.example.myapplication1.Question;
import com.example.myapplication1.R;
import com.example.myapplication1.Study;

import java.util.ArrayList;


public class generating_questions extends Fragment {
    Button Otvet, DA,NET;
    TextView Ot, getVop,getOtv, nom;
    Dialog dialog;
    int n, i;
    int idPredmeta, idPlana,n2;
    Study n1;
    @SuppressLint("CutPasteId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_generating_questions, container, false);

        Bundle b = getArguments();

        int code = b.getInt("code");
        idPredmeta = b.getInt("idPredmeta");
        idPlana = b.getInt("Ind");

        Ot=(TextView)root.findViewById(R.id.otve) ;
        nom=(TextView)root.findViewById(R.id.Num) ;
        getVop=(TextView)root.findViewById(R.id.textVop) ;
        getOtv=(TextView)root.findViewById(R.id.otve) ;
        Otvet=(Button)root.findViewById(R.id.XZ) ;
        DA=(Button)root.findViewById(R.id.Da) ;
        NET=(Button)root.findViewById(R.id.No) ;
        i=1;
        nom.setText("Вопрос №"+ i);
        Ot.setVisibility(View.INVISIBLE);
        DA.setVisibility(View.INVISIBLE);
        NET.setVisibility(View.INVISIBLE);
        getOtv.setMovementMethod(new ScrollingMovementMethod());
        getOtv.setMovementMethod(new ScrollingMovementMethod());

        if(code == 2){
            passedPlanForTheDay();
        } else if(code==1){
            noPlanForTheDay();
        } else havePlanForTheDay();


        return root;
    }


    private void setVisible(){
        Ot.setVisibility(View.VISIBLE);
        Otvet.setVisibility(View.INVISIBLE);
        DA.setVisibility(View.VISIBLE);
        NET.setVisibility(View.VISIBLE);
    }
    private void setInvisible(){
       Ot.setVisibility(View.INVISIBLE);
                    Otvet.setVisibility(View.VISIBLE);
                    DA.setVisibility(View.INVISIBLE);
                    NET.setVisibility(View.INVISIBLE);
    }
    private void nextQuestion() {
        n = n1.STUDY();
        getVop.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getQuestion());
        getOtv.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getAnswer());
        nom.setText("Вопрос №" + i);
    }


    private void havePlanForTheDay(){
        n1 = new Study(MainActivity2.ArrPlan.get(idPredmeta).getSub(),
                MainActivity2.ArrPlan.get(idPredmeta).getPlan_to_day().get(idPlana).getSize_of_quetion(),
                MainActivity2.ArrPlan.get(idPredmeta).getTodaylearned());

        n = n1.STUDY();

        getVop.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getQuestion());
        getOtv.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getAnswer());

        Otvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible();
            }
        });

        //да
        DA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                setInvisible();

                Question q = new Question(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n));
                MainActivity2.ArrPlan.get(idPredmeta).after_study(n, 1);

                MainActivity2.myDBManager.update_QWTABLE(MainActivity2.ArrPlan.get(idPredmeta).getSub().getName_of_sub(), q, MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n));
                MainActivity2.myDBManager.update_todaylearning(MainActivity2.ArrPlan.get(idPredmeta).getSub().getName_of_sub(), MainActivity2.ArrPlan.get(idPredmeta).getTodaylearned());

                if (MainActivity2.ArrPlan.get(idPredmeta).getPlan_to_day().get(idPlana).getSize_of_quetion() == MainActivity2.ArrPlan.get(idPredmeta).getTodaylearned()) {
                    dialog=new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialogque4);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView t = (TextView) dialog.findViewById(R.id.textv1);
                    t.setText("Поздравляем! Вы прошли план на сегодня! \nТеперь вы можете повторить все выученные вопросы.");
                    TextView t1 = (TextView) dialog.findViewById(R.id.textv);
                    t1.setText("Вы прошли план на сегодня!");
                    //кнопка добавление даты
                    Button bt = (Button)dialog.findViewById(R.id.bSet);
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                    Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_navigation_home);
                } else {
                    nextQuestion();
                }
            }
        });
        //нет
        NET.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                i++;
                setInvisible();

                Question q = new Question(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n));
                MainActivity2.ArrPlan.get(idPredmeta).after_study(n, 0);

                MainActivity2.myDBManager.update_QWTABLE(MainActivity2.ArrPlan.get(idPredmeta).getSub().getName_of_sub(), q, MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n));
                MainActivity2.myDBManager.update_todaylearning(MainActivity2.ArrPlan.get(idPredmeta).getSub().getName_of_sub(), MainActivity2.ArrPlan.get(idPredmeta).getTodaylearned());

                if (MainActivity2.ArrPlan.get(idPredmeta).getPlan_to_day().get(idPlana).getSize_of_quetion() == MainActivity2.ArrPlan.get(idPredmeta).getTodaylearned()) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_navigation_home);
                } else {
                    nextQuestion();
                }

            }
        });
    }

    private void noPlanForTheDay(){
        dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogque4);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button bt = (Button)dialog.findViewById(R.id.bSet);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();

        n2=0;
        getVop.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n2).getQuestion());
        getOtv.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n2).getAnswer());

        Otvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible();
            }
        });
        DA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                n2++;
                if(n2>=MainActivity2.ArrPlan.get(idPredmeta).getQue().size())n2=0;
                setInvisible();

                getVop.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n2).getQuestion());
                getOtv.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n2).getAnswer());
                nom.setText("Вопрос №"+ i);

            }
        });
        //нет
        NET.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                i++;
                n2++;
                if(n2==MainActivity2.ArrPlan.get(idPredmeta).getQue().size()){n2=0;}
                setInvisible();

                getVop.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n2).getQuestion());
                getOtv.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n2).getAnswer());
                nom.setText("Вопрос №"+ i);


            }
        });
    }

    private void passedPlanForTheDay(){
        dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogque4);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView t = (TextView) dialog.findViewById(R.id.textv1);
        t.setText("Вы можете повторить вопросы, которые уже изучили. \nРезультат будет записан в общий план!");
        TextView t1 = (TextView) dialog.findViewById(R.id.textv);
        t1.setText("Вы уже прошли план на сегодня!");
        //кнопка добавление даты
        Button bt = (Button)dialog.findViewById(R.id.bSet);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        n2=0;
        Study n1 = new Study(MainActivity2.ArrPlan.get(idPredmeta).getSub(),2);
        n = n1.STUDY_ALL();

        getVop.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getQuestion());
        getOtv.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getAnswer());

        Otvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible();
            }
        });
        DA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;

                setInvisible();

                Question q = new Question(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n));
                MainActivity2.ArrPlan.get(idPredmeta).after_study1(n, 1);

                MainActivity2.myDBManager.update_dateQW(MainActivity2.ArrPlan.get(idPredmeta).getSub().getName_of_sub(), q, MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getdate());

                n = n1.STUDY_ALL();
                if (n == -1) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_navigation_home);
                } else {
                    getVop.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getQuestion());
                    getOtv.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getAnswer());
                    nom.setText("Вопрос №" + i);
                }
            }
        });
        //нет
        NET.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                i++;

                setInvisible();

                Question q = new Question(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n));
                MainActivity2.ArrPlan.get(idPredmeta).after_study1(n, 0);

                ArrayList<PlanToDay> p = new  ArrayList<PlanToDay>( MainActivity2.ArrPlan.get(idPredmeta).getPlan_to_day());
                MainActivity2.myDBManager.update_QWTABLE(MainActivity2.ArrPlan.get(idPredmeta).getSub().getName_of_sub(), q, MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n));
                MainActivity2.myDBManager.update_todaylearning(MainActivity2.ArrPlan.get(idPredmeta).getSub().getName_of_sub(), MainActivity2.ArrPlan.get(idPredmeta).getTodaylearned());
                MainActivity2.myDBManager.update_Allplantoday(MainActivity2.ArrPlan.get(idPredmeta).getSub().getName_of_sub(),p,MainActivity2.ArrPlan.get(idPredmeta).getPlan_to_day());

                n = n1.STUDY_ALL();
                if (n == -1) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_navigation_home);
                } else {
                    getVop.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getQuestion());
                    getOtv.setText(MainActivity2.ArrPlan.get(idPredmeta).getQue().get(n).getAnswer());
                    nom.setText("Вопрос №" + i);
                }

            }
        });
    }
}