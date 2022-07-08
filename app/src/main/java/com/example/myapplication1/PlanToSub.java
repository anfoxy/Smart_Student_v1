package com.example.myapplication1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
//ди
public class PlanToSub implements Serializable {
    private   Subject sub; //предмет
    private   ArrayList<PlanToDay> plan_to_day; //планы на все дни
    private   int todaylearned; //сколько выучили именно сегодня
    private Date_simple today;
    private Date_simple date_of_exams; //Дата, когда будет экзамен
    public PlanToSub(Subject sub_, int day, int mon, int year){
        sub=sub_;
        date_of_exams= new Date_simple(day, mon, year);
        plan_to_day=new ArrayList<PlanToDay>();
        todaylearned=0;
        today=new Date_simple();
      // do_date_toDay_dateOfE();

    }

    public PlanToSub(PlanToSub plan) {
        this.todaylearned =  plan.todaylearned;
        this.sub = new Subject(plan.sub);
        this.plan_to_day =  new ArrayList<PlanToDay>(plan.plan_to_day);
        this.today =   new Date_simple(plan.today);
        this.date_of_exams =  new Date_simple(plan.date_of_exams);
    }

    public  PlanToSub(Subject sub_, Date_simple today1, int todaylearned1, Date_simple date_of_exams1, ArrayList<PlanToDay> plan_to_day1){
        sub=sub_;
        today=today1;
        todaylearned=todaylearned1;
        date_of_exams=date_of_exams1;
        plan_to_day=plan_to_day1;
    }

    public ArrayList<PlanToDay> getPlan_to_day() {
        return plan_to_day;
    }

    public int getTodaylearned() {
        return todaylearned;
    }

    public Date_simple getToday() {
        return today;
    }
    public boolean deletePlanToDay() {
        plan_to_day=new ArrayList<PlanToDay>();
        return true;
    }

    public void setPlan_to_day(ArrayList<PlanToDay> plan_to_day) {
        this.plan_to_day = plan_to_day;
    }

    //При открытии программы это делать всегда.
    private void do_date_toDay_dateOfE(){
        int a=size_today_day_of_exams();
        Calendar cal=new GregorianCalendar();
        for(int i=0; i<a; i++){
            add_date_to_study(cal.get(Calendar.DATE), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
            cal.add(Calendar.DATE, 1);
        }
        do_plan();
    }
    public  void open_prog(){
        Date_simple av=new Date_simple();
        if(today.before(av)) { //тут проверяется наступил ли новый день
            int a=search_nom_plan(today.day, today.month, today.year);
            if (a!=-1){
                plan_to_day.get(a).setSize_of_quetion(todaylearned);
                MainActivity2.myDBManager.update_today_ost_que(sub.getName_of_sub(),plan_to_day.get(a).getDate(),todaylearned);
            }
            todaylearned=0;
            MainActivity2.myDBManager.update_todaylearning(sub.getName_of_sub(),0);
            today=av;
            MainActivity2.myDBManager.update_tDATE(sub.getName_of_sub(),av);
            ArrayList<PlanToDay> p = new  ArrayList<PlanToDay>(plan_to_day);
            do_plan();
            MainActivity2.myDBManager.update_Allplantoday(sub.getName_of_sub(),p,plan_to_day);
        }
    }
    public void after_study1(int nom_q, int res){
        sub.get_Class_Question(nom_q).base_click(res);
    }
    public void after_study(int nom_q, int res){
        if(res==1&&sub.get_Class_Question(nom_q).Getknow_answer()==false) todaylearned++;
        if(res==0&&sub.get_Class_Question(nom_q).Getknow_answer()==true) todaylearned--;
        sub.get_Class_Question(nom_q).base_click(res);
    }
    public  int get_size_q_today(){
        int res=search_nom_plan(today.day, today.month, today.year);
        if(res!=-1) res=plan_to_day.get(res).getSize_of_quetion();
        else res=0;
        return res;
    }
    private void sort_list(){
        Date_simple a=new Date_simple(0, 0, 0);
        int d=0;
        for(int i=1; i<plan_to_day.size(); i++) {
            if(plan_to_day.get(i-1).getDate().before(plan_to_day.get(i).getDate())) continue;
            for(int j=i; j>=1; j--){
                if(plan_to_day.get(j).getDate().before(plan_to_day.get(j-1).getDate())) {
                    plan_to_day.add(j-1,plan_to_day.get(j));
                    plan_to_day.remove(j+1);
                } else continue;
            }
        }
    }
    public  int search_nom_plan(int dat, int mon, int yy){
        Date_simple a=new Date_simple(dat, mon, yy);
        for(int i=0; i<plan_to_day.size(); i++){
            if(a.equally(plan_to_day.get(i).getDate())) return i;
        }
        return -1;
    }
    public  void delete_date_to_study(int day, int mon, int year){
        int a=search_nom_plan(day, mon, year);
        if(a!=-1) {
            plan_to_day.remove(a);
            do_plan();
        }
    }
    public  void add_date_to_study(int day, int mon, int year){
        //проверить, не было ли этой даты ранее
        int d=0;
        Date_simple da=new Date_simple(day, mon, year);
        for(int i=0; i<plan_to_day.size(); i++) {
            if (plan_to_day.get(i).getDate().equally(da)) {
                d++;
                break;
            }
        }
        //2- добавить дату
        if(d==0){
            plan_to_day.add(new  PlanToDay(da, 0));
            sort_list();
            do_plan();
        }
    }





    //количество дней до экзамена
    public int size_today_day_of_exams(){
        Calendar a= new GregorianCalendar();
        Calendar b= new GregorianCalendar(date_of_exams.year, date_of_exams.month, date_of_exams.day);
        int res=0;
        while(a.before(b)){
            a.add(Calendar.DATE, 1);
            res++;
        } //пока a!=b прибавлять день
        return res;
    }

    public  int size_today_day_of_exams_plan(){
        Date_simple a= new Date_simple();
        int res=0;
        for(int i=0; i<plan_to_day.size();i++){
            if((plan_to_day.get(i).getDate().before(date_of_exams) &&(a.before(plan_to_day.get(i).getDate())||a.equally(plan_to_day.get(i).getDate()))))
                res++;
        }
        return res;
    }
    private void do_plan(){
        int size_of_fall_qwestion=sub.get_size_no_know(); //количество не выученных вопросов
        int size_of_days=size_today_day_of_exams_plan(); //количество дней до экзамена
        int nom_today=search_nom_plan(new Date_simple().day, new Date_simple().month, new Date_simple().year);
        if (nom_today==-1){
            Calendar cal=new GregorianCalendar();
            cal.add(Calendar.DATE, 1);
            nom_today=search_nom_plan(cal.get(Calendar.DATE), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
            while(nom_today==-1){
                cal.add(Calendar.DATE, 1);
                nom_today=search_nom_plan(cal.get(Calendar.DATE), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
                if(cal.get(Calendar.DATE)== date_of_exams.day && cal.get(Calendar.MONTH)== date_of_exams.month && cal.get(Calendar.YEAR)== date_of_exams.year) break;
            }
        }
        if(nom_today!=-1){
            int average; //среднее арифметическое
            if(size_of_days==0) average=0;
            else average=size_of_fall_qwestion/size_of_days;
            if(average*size_of_days==size_of_fall_qwestion) //если чудом все поделилось нацело
                for(int i=nom_today; i<plan_to_day.size(); i++){
                    plan_to_day.get(i).change_size_of_quetion(average);
                }
            else{ //если не поделилось на цело
                int s=size_of_fall_qwestion-average*size_of_days; //считаем остаток
                for(int i=nom_today; i<plan_to_day.size(); i++){
                    if(s!=0) { //добавляем этот остаток, пока он не иссякнет
                        plan_to_day.get(i).change_size_of_quetion(average+1);
                        s--;
                    } else plan_to_day.get(i).change_size_of_quetion(average);
                }
            }
        }

    } //план создается для ненаступивших дат и количества невыученных вопросов.

    public void change_date_of_exams( int day, int mon, int year){
        //проверки на дурака нет, вводи все сразу правильно
        date_of_exams.day=day;
        date_of_exams.month=mon;
        date_of_exams.year=year;
        do_plan();
    }
    public Date_simple getDate_of_exams(){
        return date_of_exams;
    }
    public String getDate(){
        return date_of_exams.getDate();
    }
    public String getSubString() {
        return sub.getName_of_sub();
    }
    public String gettodaylearned() {
        return String.valueOf(todaylearned);
    }

    public ArrayList<Question> getQue(){
        return sub.getQuestion();
    }

    public int getDay() {
        return date_of_exams.day;
    }

    public int getMonth() {
        return date_of_exams.month;
    }

    public int getYear() {
        return date_of_exams.year;
    }
    public Subject getSub(){return sub;}


}