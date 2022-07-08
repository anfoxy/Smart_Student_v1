package com.example.myapplication1;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;

//ди
public class Subject {
  private    String name_of_sub;
  private ArrayList <Question> question;

    public Subject() {
        name_of_sub="";
        question=new ArrayList<Question>();
    }
    public Subject(String name) {
        name_of_sub=name;
        question=new ArrayList<Question>();
    }
    public Subject(String name, ArrayList<Question> arr_questions) {
        name_of_sub=name;
        question=arr_questions;
    }

    public Subject( Subject s) {
        name_of_sub=s.name_of_sub;
        question= new ArrayList<>(s.question);
    }
    public ArrayList<Question> getQuestion() {
        return question;
    }

    public String getName_of_sub() {
        return name_of_sub;
    }

    public void setName_of_sub(String name_of_sub) {
        this.name_of_sub = name_of_sub;
    }

    public void setQuestion(ArrayList<Question> question) {
        this.question = question;
    }

    void add_question(Question quest)
    //добавить вопрос в конец списка
    {
        question.add(quest);
    }
   //public void add_questionArr(ArrayList<Question> quest) { question= new ArrayList<>(quest); }
   public void delete_question(int nom)
    //удалить вопрос по номеру
    {
        if(nom>=0&&nom<question.size()) question.remove(nom);
    }
    void change_question (int nom, String quest, String answer){
        //изменить вопрос по номеру, если "", менять не будет
        if(nom>=0&&nom<question.size()) {
            if(answer!="")question.get(nom).answer = answer;
            if(quest!="")question.get(nom).question = quest;
        }
    }

    Question get_Class_Question (int nom){
        if(nom < question.size() && nom >= 0) return question.get(nom);
        return new Question();
    }
   public int get_size_all_quest(){
        //количество вопросов
        return question.size();
    }
   public int get_size_know()
    //количество выученных вопросов
    {
        int d=0;
        for(int i=0; i<question.size(); i++){
            if(question.get(i).Getknow_answer()) d++;
        }
        return d;
    }
   public int get_size_no_know()
    //количество невыченных вопросов
    {
        int d=0;
        for(int i=0; i<question.size(); i++){
            if(!question.get(i).Getknow_answer()) d++;
        }
        return d;
    }

    //--------------------Выдача вопросов для алгоритма---------
    Date_simple o_oldest_day(){
        Date_simple res= new Date_simple(1, 1, 1);
        if(question.size()>0){
            res=question.get(0).GetDate();
            for (int i = 1; i < question.size(); i++) {
                if(res.before(question.get(i).GetDate())&&question.get(i).Getknow_answer()) res=question.get(i).GetDate();
            }
        }
        return  res;
    }
    //находит дату самого старого вопроса, который мы выучили
    int max_size_of_view(){
        int res=0;
        if(question.size()>0){
            res=question.get(0).get_size_of_view();
            for (int i = 1; i < question.size(); i++) {
                if(question.get(i).get_size_of_view()>res &&question.get(i).Getknow_answer()) res=question.get(i).get_size_of_view();
            }
        }
        return  res;
    }
    // находит количество просмотров самого сложного вопроса
    ArrayList<Integer> O_array_oldest(Date_simple max) {
        ArrayList<Integer> res=new ArrayList<Integer>();
        Date_simple point= new Date_simple(1, 1, 1);
        for (int i = 0; i < question.size(); i++) {
            if(question.get(i).GetDate().before(max)&&question.get(i).Getknow_answer()){
                if(point.equally(question.get(i).GetDate())){
                    res.add(i);
                    point=question.get(i).GetDate();
                }
                else if(point.before(question.get(i).GetDate())){
                    res.clear();
                    res.add(i);
                    point=question.get(i).GetDate();
                }
            }
        }
        return res;
    }
    //находит самые старые вопросы до даты max, выводится более одного элемента, если в один день было несколько самых старых вопросов
    ArrayList<Integer> O_get_array_new_q(int size){
        ArrayList<Integer> res=new ArrayList<Integer>();
        for (int i = 0; i < question.size(); i++) {
            if (!(question.get(i).Getknow_answer())&&(question.get(i).get_size_of_view()==0)){
                res.add(i);
            }
        }
        return res;
    }
    //выдает абсолютно навые непрорешеные вопросы в нужном обьеме
    ArrayList<Integer> O_difficult_q(int max){
        ArrayList<Integer> res=new ArrayList<Integer>();
        int max1=0;
        for (int i = 0; i < question.size(); i++) {
            if(question.get(i).Getknow_answer()&&question.get(i).get_size_of_view()<max){
                if(question.get(i).get_size_of_view()==max1){
                    res.add(i);
                }
                else if(question.get(i).get_size_of_view()>max1){
                    res.clear();
                    res.add(i);
                    max1=question.get(i).get_size_of_view();
                }
            }
        }
        return res;
    }
    //возвращает самые сложные выучкные вопроы. Возвращает более 1го элемента, если самых сложных несколько
    ArrayList<Integer> O_base_q(int size){
        ArrayList<Integer> res=new ArrayList<Integer>();
        for (int i = 0; i < question.size(); i++) {
            if(res.size()== size) break;
            if (question.get(i).get_size_of_view()>0&&(!(question.get(i).Getknow_answer()))){
                res.add(i);
            }
        }
        return res;
    }
    //Вопросы, которые мы решали, но не запоинили


}
