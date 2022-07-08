package com.example.myapplication1;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

//ди
public class Question implements Serializable {
  public   String question; //Вопрос
  public   String answer; //Ответ
    private Date_simple last_date; //Дата, когда попадался этот вопрос последний раз //1.1.1-значит еще не было
    private int size_of_view; //сколько раз попадался этот вопрос
    private boolean know; //Вопрос выучен- 1, не выучен- 0

    public Question() {
        question = "";
        answer = "";
        last_date = new Date_simple(1, 1, 1);
        size_of_view = 0;
        know = false;
    }
    public Question(String quest, String ansver)
    {
        question = quest;
        answer = ansver;
        last_date = new Date_simple(1, 1, 1);
        size_of_view = 0;
        know = false;
    }
public Question(Question q){
    question = new String(q.question);
    answer = new String(q.answer);
    last_date = new Date_simple(q.last_date.day,q.last_date.month,q.last_date.year);
    size_of_view = new Integer(q.size_of_view);
    know = new Boolean(q.know);

}
    public Question(String quest, String ansver,Date_simple d,int size,boolean k)
    {
        question = quest;
        answer = ansver;
        last_date = d;
        size_of_view = size;
        know = k;
    }
    void base_click(int click_on_res)
    //____________________________________________
    // если правильно-1, если нет- 0, 2- если неизвестно
    {
        last_date.day=new Date_simple().day;
        last_date.month=new Date_simple().month;
        last_date.year=new Date_simple().year;
        //должен установить текущее время

        if(click_on_res==0) {
            know = false;
            size_of_view++;
        }
        else if ((click_on_res == 1)&& know == false) {
            know = true;
            size_of_view++;
        }
        else if (click_on_res == 2) {
            know = false;
            size_of_view++;
        }
    }
    boolean Getknow_answer() {
        return know;
    }
    int get_size_of_view(){
        return size_of_view;
    }

    Date_simple GetDate() {
        return last_date;
    }
    void formaintestNOTOUCH(int sov, boolean k, Date_simple ld){
        size_of_view=sov;
        know=k;
        last_date=ld;
    }
    public int getday(){
        return  last_date.day;
    }
    public int getmonth(){
        return  last_date.month;
    }
    public int getyear(){
        return  last_date.year;
    }
    public String getSize_of_view(){
        return  String.valueOf(size_of_view);
    }
    public String getknow(){
        if (know){
            return  String.valueOf(1);
        }else{
            return  String.valueOf(0);
        }

    }
public Date_simple getdate(){
        return last_date;
}
    public int getSize_of_view1(){
        return  size_of_view;
    }
    public int getknow1(){
        if (know){
            return  1;
        }else{
            return  0;
        }

    }
    public String getQuestion() {
        return question;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public String getAnswer() {
        return answer;
    }
}