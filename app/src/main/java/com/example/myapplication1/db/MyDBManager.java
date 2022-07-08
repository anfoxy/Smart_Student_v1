package com.example.myapplication1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.PlanToDay;
import com.example.myapplication1.PlanToSub;
import com.example.myapplication1.Question;
import com.example.myapplication1.Subject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MyDBManager {
    private Context context;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;
    public MyDBManager(Context context) {
        this.context = context;
        myDBHelper = new MyDBHelper(context);
    }
    public void openDB() {
        db = myDBHelper.getWritableDatabase();
    }
    public  void get(PlanToSub pl){
        insert_TABLE_SUBJ(pl);
        for(int i=0;i<pl.getSub().getQuestion().size();i++){
            insert_TABLE_QUE(pl.getSub().getName_of_sub(),pl.getSub().getQuestion().get(i));
        }
        insert_TABLE_TODAY(pl);
    }
    public Cursor getYourTableContents() {
        openDB();
        Cursor data = db.rawQuery("SELECT * FROM " + "name_sub", null);
        return data;
    }
    public ArrayList<PlanToSub> set() {

        ArrayList<PlanToSub> pl = new ArrayList<>();
        //Будем использовать данные из таблицы SUBJECT - последнее ID. ID нам нужен для того, чтобы мы смогли знать точное кол-во предметов для цикла for.
        String queryID_sub = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", " + MyConstants.DAY + ", " + MyConstants.MONTH + ", " + MyConstants.YEAR + ", " + MyConstants.TODAYLEARNED + ", " + MyConstants.TDAY + ", " + MyConstants.TMONTH + ", " + MyConstants.TYEAR + " FROM " + MyConstants.TABLE_NAME_SUBJECTS;
        //Вывод из таблицы все ID
        Cursor yourCursor = getYourTableContents();
        String query_today = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", " + MyConstants.TODAY + ", " + MyConstants.TOMONTH + ", " + MyConstants.TOYEAR + ", " + MyConstants.TODAY_OST_QUE + " FROM " + MyConstants.TABLE_NAME_TODAY;
        int ff = 0;

        while (yourCursor.moveToNext()) {
            ff += 1;
        }

        if(ff > 0) {
            //Вывод из таблицы все ID
            Cursor cursor = db.rawQuery(queryID_sub, null);
            Cursor cursor3 = db.rawQuery(query_today, null);
            //Чтобы сохранить последний ID(это будет число предметов), нужно переместить курсор в конец.
            cursor.moveToFirst();
            cursor3.moveToFirst();
            int kol_sub=0;
            int kol_plan=0;
            int kol_que=0;
            //сохраняем ID в переменную.
            do { kol_sub++; }
            while (cursor.moveToNext());
            do { kol_plan++; }
            while (cursor3.moveToNext());

           // kol_sub = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));

           // kol_plan = cursor3.getInt(cursor3.getColumnIndex(MyConstants._ID));
            //Тоже самое только для количества вопросов и ответов.
            String queryID_que = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", "
                    + MyConstants.TITLE + ", " + MyConstants.SUBTITLE + ", " + MyConstants.LASTDAY + ", " + MyConstants.LASTMONTH + ", " + MyConstants.LASTYEAR + ", " + MyConstants.SIZE_OF_VIEW + ", " + MyConstants.KNOW + " FROM " + MyConstants.TABLE_NAME_QUESTIONS;
            Cursor cursor2 = db.rawQuery(queryID_que, null);
            cursor2.moveToFirst();
           // kol_que = cursor2.getInt(cursor2.getColumnIndex(MyConstants._ID));
            do { kol_que++; }
            while (cursor2.moveToNext());
            cursor2.moveToFirst();
            //Создаём String переменную масива
            cursor.moveToFirst();
            ArrayList<String> question_table_subject = new ArrayList<String>();
            for(int i = 0; i < kol_sub; i++) {
                question_table_subject.add(cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT)));
                cursor.moveToNext();
            }
            cursor2.moveToFirst();
            // нужно из базы данных загрузить предметы, даты экзамена, вопросы, ответы
            cursor.moveToFirst();
            // Запишем для каждого предмета вопросы.
            Subject pl2;

            for (int i=0; i<kol_sub; i++) {
                ArrayList<Question> q3 = new ArrayList<>();
                // Записываются те вопросы, где название предмета в таблицах совпадают.
                for (int k=0; k<kol_que; k++) {
                    cursor2.moveToPosition(k);
                    if (question_table_subject.get(i).equals(cursor2.getString(cursor2.getColumnIndex(MyConstants.SUBJECT)))) {
                        String question = cursor2.getString(cursor2.getColumnIndex(MyConstants.TITLE));
                        String subquestion = cursor2.getString(cursor2.getColumnIndex(MyConstants.SUBTITLE));

                        int lday = cursor2.getInt(cursor2.getColumnIndex(MyConstants.LASTDAY));
                        int lmonth = cursor2.getInt(cursor2.getColumnIndex(MyConstants.LASTMONTH));
                        int lyear = cursor2.getInt(cursor2.getColumnIndex(MyConstants.LASTYEAR));
                        int sv = cursor2.getInt(cursor2.getColumnIndex(MyConstants.SIZE_OF_VIEW));
                        int kn = cursor2.getInt(cursor2.getColumnIndex(MyConstants.KNOW));
                        boolean kn1;
                        if(kn==1){
                            kn1 =true;
                        } else {
                            kn1 =false;
                        }
                        Date_simple d = new Date_simple(lday,lmonth,lyear);
                        Question q1 = new Question(question, subquestion,d,sv,kn1);
                        q3.add(q1);
                    }
                }
                cursor.moveToPosition(i);
                pl2 = new Subject(cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT)), q3);
                int day = cursor.getInt(cursor.getColumnIndex(MyConstants.DAY));
                int month = cursor.getInt(cursor.getColumnIndex(MyConstants.MONTH));
                int year = cursor.getInt(cursor.getColumnIndex(MyConstants.YEAR));

                PlanToSub pl5;

                int todaylearn = cursor.getInt(cursor.getColumnIndex(MyConstants.TODAYLEARNED));

                Date_simple dates = new Date_simple(day, month, year);

                ArrayList<PlanToDay> arrtoday = new ArrayList<>();
                cursor3.moveToPosition(0);
                int tday = cursor.getInt(cursor.getColumnIndex(MyConstants.TDAY));
                int tmonth = cursor.getInt(cursor.getColumnIndex(MyConstants.TMONTH));
                int tyear = cursor.getInt(cursor.getColumnIndex(MyConstants.TYEAR));
                Date_simple todayss = new Date_simple(tday, tmonth, tyear);

                for(int x = 0; x < kol_plan; x++ ) {

                    cursor3.moveToPosition(x);
                    if (question_table_subject.get(i).equals(cursor3.getString(cursor3.getColumnIndex(MyConstants.SUBJECT)))) {
                        int today = cursor3.getInt(cursor3.getColumnIndex(MyConstants.TODAY));
                        int tomonth = cursor3.getInt(cursor3.getColumnIndex(MyConstants.TOMONTH));
                        int toyear = cursor3.getInt(cursor3.getColumnIndex(MyConstants.TOYEAR));
                        int sizetod = cursor3.getInt(cursor3.getColumnIndex(MyConstants.TODAY_OST_QUE));

                        PlanToDay tod = new PlanToDay(today, tomonth, toyear, sizetod);
                        arrtoday.add(tod);
                    }
                }
                pl5 = new PlanToSub(pl2, todayss, todaylearn, dates, arrtoday);
                pl.add(pl5);
            }

            cursor.close();
            cursor2.close();
            cursor3.close();
        }
        return pl;
    }
    public void update_tDATE(String name,Date_simple d){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.TDAY, d.day);
        cv.put(MyConstants.TMONTH, d.month);
        cv.put(MyConstants.TYEAR, d.year);
        db.update(MyConstants.TABLE_NAME_SUBJECTS, cv, MyConstants.SUBJECT + "= ?",new String[] {name});
    }
    public void update_sub(String s, String lastname) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.SUBJECT, s);
        db.update(MyConstants.TABLE_NAME_SUBJECTS, cv, MyConstants.SUBJECT + "= ?",new String[] {lastname});
        db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants.SUBJECT + "= ?", new String[] {lastname});
        db.update(MyConstants.TABLE_NAME_TODAY, cv, MyConstants.SUBJECT + "= ?", new String[] {lastname});
    }
    public void delete_QUE(String ind) {
        db.delete(MyConstants.TABLE_NAME_QUESTIONS, MyConstants.SUBJECT + " = ?", new String[]{ind});
    }
    public  void update_dateQW(String name,Question q, Date_simple d2){
        String queryID_que = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", "
                + MyConstants.TITLE + ", " + MyConstants.SUBTITLE + ", " + MyConstants.LASTDAY + ", " + MyConstants.LASTMONTH + ", " + MyConstants.LASTYEAR + ", " + MyConstants.SIZE_OF_VIEW + ", " + MyConstants.KNOW + " FROM " + MyConstants.TABLE_NAME_QUESTIONS;
        Cursor cursor = db.rawQuery(queryID_que, null);
        int i = 1;
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.LASTDAY, d2.day);
        cv.put(MyConstants.LASTMONTH, d2.month);
        cv.put(MyConstants.LASTYEAR, d2.year);

        cursor.moveToFirst();
        do {
            String n = cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT));
            int today = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTDAY));
            int tomonth = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTMONTH));
            int toyear = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTYEAR));
            int tsize = cursor.getInt(cursor.getColumnIndex(MyConstants.SIZE_OF_VIEW));
            int tknow = cursor.getInt(cursor.getColumnIndex(MyConstants.KNOW));
            String qw = cursor.getString(cursor.getColumnIndex(MyConstants.TITLE));
            String aw = cursor.getString(cursor.getColumnIndex(MyConstants.SUBTITLE));

            if (q.getday() == today && q.getmonth() == tomonth && q.getyear() == toyear && name.equals(n)&& q.answer.equals(aw)&&q.question.equals(qw)&& tsize==q.getSize_of_view1()&&tknow==q.getknow1()) {
                int ind = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
                db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants._ID + "= ?", new String[]{String.valueOf(ind)});
                //...DBHelper.KEY_ID + "=" + id, null);
            }
            i++;
        } while (cursor.moveToNext());
        cursor.close();
    }
    public  void update_size_of_view(String name,Question q, int size){
        String queryID_que = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", "
                + MyConstants.TITLE + ", " + MyConstants.SUBTITLE + ", " + MyConstants.LASTDAY + ", " + MyConstants.LASTMONTH + ", " + MyConstants.LASTYEAR + ", " + MyConstants.SIZE_OF_VIEW + ", " + MyConstants.KNOW + " FROM " + MyConstants.TABLE_NAME_QUESTIONS;
        Cursor cursor = db.rawQuery(queryID_que, null);
        int i = 1;
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.SIZE_OF_VIEW, size);


        cursor.moveToFirst();
        do {
            String n = cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT));
            int today = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTDAY));
            int tomonth = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTMONTH));
            int toyear = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTYEAR));
            int tsize = cursor.getInt(cursor.getColumnIndex(MyConstants.SIZE_OF_VIEW));
            int tknow = cursor.getInt(cursor.getColumnIndex(MyConstants.KNOW));
            String qw = cursor.getString(cursor.getColumnIndex(MyConstants.TITLE));
            String aw = cursor.getString(cursor.getColumnIndex(MyConstants.SUBTITLE));

            if (q.getday() == today && q.getmonth() == tomonth && q.getyear() == toyear && name.equals(n)&& q.answer.equals(aw)&&q.question.equals(qw)&& tsize==q.getSize_of_view1()&&tknow==q.getknow1()) {
                int ind = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
                db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants._ID + "= ?", new String[]{String.valueOf(ind)});
                //...DBHelper.KEY_ID + "=" + id, null);
            }
            i++;
        } while (cursor.moveToNext());
        cursor.close();
    }
    public  void updateKnow(String name,Question q,int b){
        String queryID_que = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", "
                + MyConstants.TITLE + ", " + MyConstants.SUBTITLE + ", " + MyConstants.LASTDAY + ", " + MyConstants.LASTMONTH + ", " + MyConstants.LASTYEAR + ", " + MyConstants.SIZE_OF_VIEW + ", " + MyConstants.KNOW + " FROM " + MyConstants.TABLE_NAME_QUESTIONS;
        Cursor cursor = db.rawQuery(queryID_que, null);
        int i = 1;
        ContentValues cv = new ContentValues();

            cv.put(MyConstants.KNOW, String.valueOf(b));

        cursor.moveToFirst();
        do {
            String n = cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT));
            int today = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTDAY));
            int tomonth = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTMONTH));
            int toyear = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTYEAR));
            int tsize = cursor.getInt(cursor.getColumnIndex(MyConstants.SIZE_OF_VIEW));
            int tknow = cursor.getInt(cursor.getColumnIndex(MyConstants.KNOW));
            String qw = cursor.getString(cursor.getColumnIndex(MyConstants.TITLE));
            String aw = cursor.getString(cursor.getColumnIndex(MyConstants.SUBTITLE));

            if (q.getday() == today && q.getmonth() == tomonth && q.getyear() == toyear && name.equals(n)&& q.answer.equals(aw)&&q.question.equals(qw)&& tsize==q.getSize_of_view1()&&tknow==q.getknow1()) {
                int ind = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
                db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants._ID + "= ?", new String[]{String.valueOf(ind)});
                //...DBHelper.KEY_ID + "=" + id, null);
            }
            i++;
        } while (cursor.moveToNext());
        cursor.close();
    }
    public void update_QWTABLE(String name,Question q,Question q1){
    String queryID_que = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", "
            + MyConstants.TITLE + ", " + MyConstants.SUBTITLE + ", " + MyConstants.LASTDAY + ", " + MyConstants.LASTMONTH + ", " + MyConstants.LASTYEAR + ", " + MyConstants.SIZE_OF_VIEW + ", " + MyConstants.KNOW + " FROM " + MyConstants.TABLE_NAME_QUESTIONS;
    Cursor cursor = db.rawQuery(queryID_que, null);
    int i = 1;
    ContentValues cv = new ContentValues();
    cv.put(MyConstants.SIZE_OF_VIEW, q1.getSize_of_view());
    cv.put(MyConstants.KNOW, q1.getknow());
    cv.put(MyConstants.LASTDAY, q1.getday());
    cv.put(MyConstants.LASTMONTH, q1.getmonth());
    cv.put(MyConstants.LASTYEAR,q1.getyear());

    cursor.moveToFirst();
    do {
        String n = cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT));
        int today = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTDAY));
        int tomonth = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTMONTH));
        int toyear = cursor.getInt(cursor.getColumnIndex(MyConstants.LASTYEAR));
        int tsize = cursor.getInt(cursor.getColumnIndex(MyConstants.SIZE_OF_VIEW));
        int tknow = cursor.getInt(cursor.getColumnIndex(MyConstants.KNOW));
        String qw = cursor.getString(cursor.getColumnIndex(MyConstants.TITLE));
        String aw = cursor.getString(cursor.getColumnIndex(MyConstants.SUBTITLE));

        if (q.getday() == today && q.getmonth() == tomonth && q.getyear() == toyear && name.equals(n)&& q.answer.equals(aw)&&q.question.equals(qw)&& tsize==q.getSize_of_view1()&&tknow==q.getknow1()) {
            int ind = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
            db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants._ID + "= ?", new String[]{String.valueOf(ind)});
            //...DBHelper.KEY_ID + "=" + id, null);
        }
        i++;
    } while (cursor.moveToNext());
    cursor.close();
}
    public void delsub(String ind){
        db.delete(MyConstants.TABLE_NAME_SUBJECTS, MyConstants.SUBJECT + " = ?", new String[]{ind});
        db.delete(MyConstants.TABLE_NAME_QUESTIONS, MyConstants.SUBJECT + " = ?", new String[]{ind});
        db.delete(MyConstants.TABLE_NAME_TODAY, MyConstants.SUBJECT + " = ?", new String[]{ind});
}
    public void delete_plantoday(String name, Date_simple d1) {
        // удалить только те предметы у которых дата равна d1.
        String query_today = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", " + MyConstants.TODAY + ", " + MyConstants.TOMONTH + ", " + MyConstants.TOYEAR + " FROM " + MyConstants.TABLE_NAME_TODAY;
        Cursor cursor = db.rawQuery(query_today, null);
        int i = 1;
        cursor.moveToFirst();
        do {
            String n = cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT));
            int today = cursor.getInt(cursor.getColumnIndex(MyConstants.TODAY));
            int tomonth = cursor.getInt(cursor.getColumnIndex(MyConstants.TOMONTH));
            int toyear = cursor.getInt(cursor.getColumnIndex(MyConstants.TOYEAR));
            if (d1.day == today && d1.month == tomonth && d1.year == toyear && name.equals(n)) {
                int ind = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
                db.delete(MyConstants.TABLE_NAME_TODAY, MyConstants._ID + " = ?", new String[]{String.valueOf(ind)});
                //...DBHelper.KEY_ID + "=" + id, null);
            }
            i++;
        } while (cursor.moveToNext());
        cursor.close();
    }
    public void update_Allplantoday(String name ,ArrayList<PlanToDay> s,ArrayList<PlanToDay> s1) {
        for(int i=0; i<s.size();i++){
            update_plantoday(name,s.get(i), s1.get(i));
        }
    }
    public void update_plantoday(String name,PlanToDay p, PlanToDay p1) {

        ContentValues cv = new ContentValues();
        cv.put(MyConstants.TODAY, p.getDate().day);
        cv.put(MyConstants.TOMONTH, p.getDate().month);
        cv.put(MyConstants.TOYEAR, p.getDate().year);
        cv.put(MyConstants.TODAY_OST_QUE, p.getSize_of_quetion());

        String query_today = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", " + MyConstants.TODAY + ", " + MyConstants.TOMONTH + ", " + MyConstants.TOYEAR + ", " + MyConstants.TODAY_OST_QUE + " FROM " + MyConstants.TABLE_NAME_TODAY;
        Cursor cursor = db.rawQuery(query_today, null);
        cursor.moveToFirst();
        int i = 1;
        do {
            String n = cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT));
            int today = cursor.getInt(cursor.getColumnIndex(MyConstants.TODAY));
            int tomonth = cursor.getInt(cursor.getColumnIndex(MyConstants.TOMONTH));
            int toyear = cursor.getInt(cursor.getColumnIndex(MyConstants.TOYEAR));
            if (p1.getDate().day == today && p1.getDate().month == tomonth && p1.getDate().year == toyear && name.equals(n)) {
                int ind = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
                db.update(MyConstants.TABLE_NAME_TODAY, cv, MyConstants._ID + "= ?", new String[]{String.valueOf(ind)});
                //...DBHelper.KEY_ID + "=" + id, null);
            }
            i++;
        } while (cursor.moveToNext());
        cursor.close();
    }
    public void update_today_ost_que(String name, Date_simple d1, int a) {
        //  db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants.SUBJECT + "= ?", new String[]{String.valueOf(d1.day)});
        // d1.day
        //  db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants.SUBJECT + "= ?", new String[]{String.valueOf(a)});
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.TODAY_OST_QUE, a);
        String query_today = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", " + MyConstants.TODAY + ", " + MyConstants.TOMONTH + ", " + MyConstants.TOYEAR + ", " + MyConstants.TODAY_OST_QUE + " FROM " + MyConstants.TABLE_NAME_TODAY;
        Cursor cursor = db.rawQuery(query_today, null);
        cursor.moveToFirst();
        int i = 1;
        do {
            String n = cursor.getString(cursor.getColumnIndex(MyConstants.SUBJECT));
            int today = cursor.getInt(cursor.getColumnIndex(MyConstants.TODAY));
            int tomonth = cursor.getInt(cursor.getColumnIndex(MyConstants.TOMONTH));
            int toyear = cursor.getInt(cursor.getColumnIndex(MyConstants.TOYEAR));
            int today_ost_q = cursor.getInt(cursor.getColumnIndex(MyConstants.TODAY_OST_QUE));
            if (d1.day == today && d1.month == tomonth && d1.year == toyear && name.equals(n)) {
                int ind = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
                db.update(MyConstants.TABLE_NAME_TODAY, cv, MyConstants._ID + "= ?", new String[]{String.valueOf(ind)});
                //...DBHelper.KEY_ID + "=" + id, null);
            }
            i++;
        } while (cursor.moveToNext());
        cursor.close();
    }
    public void update_date_ex(String na, Date_simple d1) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.DAY, d1.day);
        cv.put(MyConstants.MONTH, d1.month);
        cv.put(MyConstants.YEAR, d1.year);
        db.update(MyConstants.TABLE_NAME_SUBJECTS, cv, MyConstants.SUBJECT + "= ?", new String[]{na});
    }
    public void update_todaylearning(String a, int ind) {
        //  db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants.SUBJECT + "= ?", new String[]{String.valueOf(d1.day)});
        // d1.day
        //  db.update(MyConstants.TABLE_NAME_QUESTIONS, cv, MyConstants.SUBJECT + "= ?", new String[]{String.valueOf(a)});
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.TODAYLEARNED, ind);
        db.update(MyConstants.TABLE_NAME_SUBJECTS, cv, MyConstants.SUBJECT + "= ?", new String[]{a});

    }
    public Cursor getYourTableContents2() {
        openDB();
        Cursor data = db.rawQuery("SELECT * FROM " + "name_today", null);
        return data;
    }
    public int testNoti(){
    String query_today = "SELECT " + MyConstants._ID + ", " + MyConstants.SUBJECT + ", " + MyConstants.TODAY + ", " + MyConstants.TOMONTH + ", " + MyConstants.TOYEAR + " FROM " + MyConstants.TABLE_NAME_TODAY;
        int ff = 0;
        Cursor yourCursor = getYourTableContents2();
        while (yourCursor.moveToNext()) {
            ff += 1;
        }
        int i=0;
        if(ff > 0) {
            Cursor cursor = db.rawQuery(query_today, null);
            cursor.moveToFirst();
            Calendar a =new GregorianCalendar();
            int day1 = a.get(Calendar.DATE);
            int month1 =a.get(Calendar.MONTH);
            int year1 = a.get(Calendar.YEAR);
            cursor.moveToFirst();
            do {
                int today = cursor.getInt(cursor.getColumnIndex(MyConstants.TODAY));
                int tomonth = cursor.getInt(cursor.getColumnIndex(MyConstants.TOMONTH));
                int toyear = cursor.getInt(cursor.getColumnIndex(MyConstants.TOYEAR));
                if (day1 == today && month1 == tomonth && year1 == toyear) {
                    i=1;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return i;
}
    public void insert_TABLE_SUBJ(PlanToSub pl) {
        ContentValues cv = new ContentValues();
        Cursor cursor = db.query(MyConstants.TABLE_NAME_SUBJECTS, null, null, null, null, null, null);

        cv.put(MyConstants.SUBJECT,pl.getSub().getName_of_sub());
        cv.put(MyConstants.DAY, pl.getDay());
        cv.put(MyConstants.MONTH, pl.getMonth());
        cv.put(MyConstants.YEAR, pl.getYear());
        cv.put(MyConstants.TODAYLEARNED, pl.getTodaylearned());
        cv.put(MyConstants.TDAY, pl.getToday().day);
        cv.put(MyConstants.TMONTH, pl.getToday().month);
        cv.put(MyConstants.TYEAR, pl.getToday().year);

        db.delete(MyConstants.TABLE_NAME_SUBJECTS, MyConstants.SUBJECT + " = ?", new String[]{pl.getSub().getName_of_sub()});
        db.insert(MyConstants.TABLE_NAME_SUBJECTS, null, cv);

        cursor.close();
    }
    public void insert_TABLE_QUE(String name, Question q) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.SUBJECT,  name);

        cv.put(MyConstants.TITLE, q.question);
        cv.put(MyConstants.SUBTITLE, q.answer);
        cv.put(MyConstants.LASTDAY, q.getday());
        cv.put(MyConstants.LASTMONTH, q.getmonth());
        cv.put(MyConstants.LASTYEAR, q.getyear());
        cv.put(MyConstants.SIZE_OF_VIEW, q.getSize_of_view());
        cv.put(MyConstants.KNOW, q.getknow());


        db.insert(MyConstants.TABLE_NAME_QUESTIONS, null, cv);
    }
    public void insert_TABLE_TODAY(PlanToSub pl) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < pl.getPlan_to_day().size(); i++) {
            cv.put(MyConstants.SUBJECT, pl.getSub().getName_of_sub());
            cv.put(MyConstants.TODAY, pl.getPlan_to_day().get(i).getDate().day);
            cv.put(MyConstants.TOMONTH, pl.getPlan_to_day().get(i).getDate().month);
            cv.put(MyConstants.TOYEAR, pl.getPlan_to_day().get(i).getDate().year);
            cv.put(MyConstants.TODAY_OST_QUE, pl.getPlan_to_day().get(i).getSize_of_quetion());
            db.insert(MyConstants.TABLE_NAME_TODAY, null, cv);
        }
    }
    public void insert_TABLE_TODAY2(String name_of_sub, PlanToDay pl) {
        ContentValues cv = new ContentValues();
            cv.put(MyConstants.SUBJECT, name_of_sub);
            cv.put(MyConstants.TODAY, pl.getDate().day);
            cv.put(MyConstants.TOMONTH, pl.getDate().month);
            cv.put(MyConstants.TOYEAR, pl.getDate().year);
            cv.put(MyConstants.TODAY_OST_QUE, pl.getSize_of_quetion());
            db.insert(MyConstants.TABLE_NAME_TODAY, null, cv);
    }
    public Cursor getYourTableTime() {
        openDB();
        Cursor data = db.rawQuery("SELECT * FROM " + "name_time", null);
        return data;
    }
    public boolean time_bool() {
        String query_time = "SELECT " + MyConstants._ID + ", " + MyConstants.NOTI + ", " + MyConstants.HOURS + ", " + MyConstants.MINUTES + " FROM " + MyConstants.TABLE_NAME_TIME;
        int ff = 0;
        Cursor yourCursor = getYourTableTime();
        while (yourCursor.moveToNext()) {
            ff += 1;
        }
        if(ff > 0) {
            return true;
        }
        return false;
    }
    public void insert_TABLE_TIME(int hh, int mm, int noti) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.NOTI, noti);
        cv.put(MyConstants.HOURS, hh);
        cv.put(MyConstants.MINUTES, mm);
        db.insert(MyConstants.TABLE_NAME_TIME, null, cv);
    }
    public int getMin(){
        String query_time = "SELECT " + MyConstants._ID + ", " +  MyConstants.MINUTES + " FROM " + MyConstants.TABLE_NAME_TIME;
        Cursor cursor = db.rawQuery(query_time, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(MyConstants.MINUTES));
}
    public int getHours(){
        String query_time = "SELECT " + MyConstants._ID + ", " + MyConstants.HOURS  + " FROM " + MyConstants.TABLE_NAME_TIME;
        Cursor cursor = db.rawQuery(query_time, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(MyConstants.HOURS));
    }
    public int getNoti(){
        String query_time = "SELECT " + MyConstants._ID + ", " +  MyConstants.NOTI + " FROM " + MyConstants.TABLE_NAME_TIME;
        Cursor cursor = db.rawQuery(query_time, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(MyConstants.NOTI));
    }
    public void update_time(int hh, int mm) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.HOURS, hh);
        cv.put(MyConstants.MINUTES, mm);
        db.update(MyConstants.TABLE_NAME_TIME, cv, MyConstants._ID + "= ?", new String[]{String.valueOf(1)});
    }

    public void update_timeNOTI(int noti) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.NOTI, noti);
        db.update(MyConstants.TABLE_NAME_TIME, cv, MyConstants._ID + "= ?", new String[]{String.valueOf(1)});
    }
    public void closeDB() {
        myDBHelper.close();
    }
}

