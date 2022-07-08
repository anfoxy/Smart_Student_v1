package com.example.myapplication1;
import java.util.Calendar;
import java.util.GregorianCalendar;
//ди
public class PlanToDay {

   private    Date_simple date;
   private int size_of_quetion;
    public PlanToDay(int day, int mon, int year, int size_of_quet){
        date= new Date_simple(day, mon, year);
        size_of_quetion=size_of_quet;
    }
    PlanToDay(Date_simple date_, int size_of_quet){
        date=  date_;
        size_of_quetion=size_of_quet;
    }

    public Date_simple getDate() {
        return date;
    }

    public void setDate(Date_simple date) {
        this.date = date;
    }

    public void setSize_of_quetion(int size_of_quetion) {
        this.size_of_quetion = size_of_quetion;
    }

    void change_size_of_quetion(int a){
        size_of_quetion=a;
    }

    public int getSize_of_quetion() {
        return size_of_quetion;
    }

    public String getSize_of_quetionString() {
        return String.valueOf(size_of_quetion);
    }
}