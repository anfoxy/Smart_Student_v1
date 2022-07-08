package com.example.myapplication1;
import java.util.Calendar;
import java.util.GregorianCalendar;
import  java.util.Date;
//ди
public class Date_simple {
    public int day;
    public int month;
    public int year;
    public Date_simple(int dd, int mm, int yy) {
        day = dd;
        month = mm;
        year = yy;
    }
    public Date_simple(Date_simple date_simple) {
        this.day = date_simple.day;
        this.month =  date_simple.month;
        this.year =  date_simple.year;
    }
    public  Date_simple () {
        Calendar a =new GregorianCalendar();
        Date date=new Date();
        day = a.get(Calendar.DATE);
        month =a.get(Calendar.MONTH);
        year = a.get(Calendar.YEAR);
    }
    public boolean checkDate(Date_simple c){
        return this.day==c.day&&this.month==c.month&&this.year==c.year;
    }

    //_______________________________________
    public boolean before(Date_simple b){
        if(b.year>year) return true;
        if(b.month>month)return true;
        if(b.day>day) return true;
        return false;
    }
    boolean equally(Date_simple  b){
        if(day!=b.day) return false;
        if(month!=b.month) return false;
        if (year!=b.year) return false;
        return true;
    }

   public String getDate(){
        return "" + day + "/" + (month+1) + "/"+year;
    }

}