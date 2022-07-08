package com.example.myapplication1.db;


import com.example.myapplication1.Date_simple;

public class MyConstants {
    public static final int DB_VERSION = 38;
    public static final String DB_NAME = "DBaseV.db";

    public static final String TABLE_NAME_SUBJECTS = "name_sub";
    public static final String TABLE_NAME_QUESTIONS = "name_que";
    public static final String TABLE_NAME_TODAY = "name_today";
    public static final String  TABLE_NAME_TIME = "name_time";

    // Общий id для Subject, Question и Date
    public static final String _ID = "_id";
    // Таблица предметов
    public static final String SUBJECT = "subject";
    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String TODAYLEARNED = "todaylearned";
    public static final String TDAY = "tday";
    public static final String TMONTH = "tmonth";
    public static final String TYEAR = "tyear";

    // Таблица вопросов
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String LASTDAY = "lastday";
    public static final String LASTMONTH = "lastmonth";
    public static final String LASTYEAR = "lastyear";
    public static final String SIZE_OF_VIEW = "size_of_view";
    public static final String KNOW = "know";
    // толбца планов
    public static final String TODAY = "today";
    public static final String TOMONTH = "tomonth";
    public static final String TOYEAR = "toyear";
    public static final String TODAY_OST_QUE = "today_ost_que";

    // Столбцы для оповещения
    public static final String HOURS = "hh";
    public static final String MINUTES = "mm";
    public static final String NOTI = "noti";

    public static final String TABLE_STRUCTURE_SUB = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_SUBJECTS + " (" + _ID + " INTEGER PRIMARY KEY," + SUBJECT + " TEXT," + DAY + " INTEGER," + MONTH + " INTEGER," +
            YEAR + " INTEGER," + TODAYLEARNED + " INTEGER," + TDAY + " INTEGER," + TMONTH + " INTEGER," +
            TYEAR + " INTEGER)";
    public static final String TABLE_STRUCTURE_QUE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_QUESTIONS + " (" + _ID + " INTEGER PRIMARY KEY," + SUBJECT + " TEXT," + TITLE + " TEXT," +
            SUBTITLE + " TEXT," + LASTDAY + " INTEGER," + LASTMONTH + " INTEGER," + LASTYEAR + " INTEGER," + SIZE_OF_VIEW + " TEXT," + KNOW + " TEXT)";
    public static final String TABLE_STRUCTURE_TODAY = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_TODAY + " (" + _ID + " INTEGER PRIMARY KEY," + SUBJECT + " TEXT," + TODAY + " INTEGER," + TOMONTH + " INTEGER," +
            TOYEAR + " INTEGER," + TODAY_OST_QUE + " INTEGER)";
    public static final String TABLE_STRUCTURE_TIME = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_TIME + " (" + _ID + " INTEGER PRIMARY KEY," +  NOTI + " INTEGER," +  HOURS + " INTEGER," + MINUTES + " INTEGER)";

    public static final String DROP_TABLE_SUB = "DROP TABLE IF EXISTS " + TABLE_NAME_SUBJECTS;
    public static final String DROP_TABLE_QUE = "DROP TABLE IF EXISTS " + TABLE_NAME_QUESTIONS;
    public static final String DROP_TABLE_TODAY = "DROP TABLE IF EXISTS " + TABLE_NAME_TODAY;
    public static final String DROP_TABLE_TIME = "DROP TABLE IF EXISTS " + TABLE_NAME_TIME;
}