package com.example.myapplication1;
import java.util.ArrayList;
//ди
public class Study {
    Subject sub; //предмет передаем ТОЛЬКО из PLAN_TO_SUB
    int nom, flag;
    int plan_learned; //сколько нужно выучить сегодня
    int now_learned; //сколько выучено уже
    ArrayList<Integer> qw;
    ArrayList<Integer> qw_repeat;
    Date_simple max_date;
    int max_see;
    public Study(Subject s, int plan_learned_today, int now_learn){
        sub=s;
        max_date= new Date_simple(sub.o_oldest_day().day, sub.o_oldest_day().month, sub.o_oldest_day().year);
        max_date.day++;
        max_see=sub.max_size_of_view()+1;
        flag=0;
        plan_learned=plan_learned_today;
        now_learned=now_learn;
        qw=new ArrayList<Integer>();
        qw_repeat=new ArrayList<Integer>();
        nom=-1;
        set_start_question();
        set_repeat();
    }
    public Study(Subject s, int a){
        sub=s;
        max_date= new Date_simple(sub.o_oldest_day().day, sub.o_oldest_day().month, sub.o_oldest_day().year);
        max_date.day++;
        max_see=sub.max_size_of_view()+1;
        flag=0;
        plan_learned=s.get_size_no_know();
        now_learned=0;
        qw=new ArrayList<Integer>();
        qw_repeat=new ArrayList<Integer>();
        nom=-1;
        set_repeat_1();
    }
    //-------Формирование_стартового_запаса_вопросов(учить)-------
    public int STUDY_ALL(){
        nom++;
        if(nom>qw_repeat.size()-1) return -1;
        return nom;
    }

    void set_repeat_1(){
        qw_repeat.clear();
        for(int j=0; j<sub.get_size_all_quest(); j++) {
            if (sub.get_Class_Question(j).Getknow_answer())  qw_repeat.add(j);
        }
        if(qw_repeat.size()==0)qw_repeat.add(0);
    }
    void set_most_difficult_n(){
        if(plan_learned-now_learned>0){
            int d=plan_learned-now_learned;
            if(d<6) d=6;
            qw.addAll(sub.O_base_q(d));
        }
    }
    void set_start_question(){
        set_most_difficult_n();
        if(plan_learned-now_learned-qw.size()>0)
            if(qw.size()<plan_learned-now_learned) qw.addAll(sub.O_get_array_new_q(plan_learned-now_learned-qw.size()));

    }
    void set_new_start_question(){
        qw.addAll(sub.O_base_q(sub.get_size_no_know()));
        qw.addAll(sub.O_get_array_new_q(6));
    }

    //-------Формирование_стартового_запаса_вопросов(повторение)-------
    void dell_clone(){
        for(int i1=0; i1<qw_repeat.size()-1; i1++){
            for(int i2=i1+1; i2<qw_repeat.size(); i2++){
                if(sub.get_Class_Question(qw_repeat.get(i1)).GetDate()==new Date_simple()) qw_repeat.remove(i1);
                else if(qw_repeat.get(i1)==qw_repeat.get(i2)) {
                    qw_repeat.remove(i2);
                    break;
                }

            }
        }
    }

    void set_repeat(){
        ArrayList<Integer> arr1=new ArrayList<Integer>();
        ArrayList<Integer> arr2=new ArrayList<Integer>();
        arr1=sub.O_array_oldest(max_date);
        arr2=sub.O_difficult_q(max_see);
        if(arr1.size()>0){
            qw_repeat.addAll(arr1);
            max_date= sub.get_Class_Question(arr1.get(0)).GetDate();
        }
        if(arr2.size()>0){
            qw_repeat.addAll(arr2);
            max_see= sub.get_Class_Question(arr2.get(0)).get_size_of_view();
        }
        dell_clone();//так как вопросы могут повториться
    }
    //----------Поиск-элемента-----------------------------

    //-----Проверка вопроса из  (выучен ли он qw)---
    private void delete_kn_quest(){
        if(nom>-1&&qw.size()<nom){
            if(sub.get_Class_Question(qw.get(nom)).Getknow_answer()) {
                qw.remove(nom);
                now_learned++;
            }
        }
    }

    //-----Обучение---------
    public int STUDY(){
        delete_kn_quest();
        int res=-1;
        if(qw_repeat.size()==0) set_repeat();
        if(flag>2&&qw_repeat.size()>0&&qw_repeat.get(0)!=res) {
//возвращаем вопрос из повторения
            res=qw_repeat.get(0);
            qw_repeat.remove(0);
            flag=0;
        } else{

            if(qw.size()==0){
                set_new_start_question();
                nom=0;
            }
            else {
                if(nom+1<qw.size()) nom++; else nom=0;
                res=qw.get(nom);
                flag++;

            }
        }
//если вдруг чел все выучил
        if(res==-1){
            if(qw_repeat.size()==0) set_repeat();
            if(flag==2&&qw_repeat.size()>0) {
                res = qw_repeat.get(0);
                qw_repeat.remove(0);
                flag = 0;
            }
        }
//если вдруг чел все выучил и все сегодня повторил
        if(res==-1){
            if(nom+1<sub.getQuestion().size()) nom++; else nom=0;
        }
        if(res==-1) res=0;
        return res;
    }
}