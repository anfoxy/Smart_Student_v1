package com.example.myapplication1;

public class newPlan {

    private String name;//название предмета
    private String kol;// количество вопросов на данную дату
    private int ind;//индекс плана по данному предмету
    private int InPredmeta;//индекс предмета
    public newPlan(String name,int indPlana,String kol,int InPredmeta){
        this.ind=indPlana;
        this.name=name;
        this.kol=kol;
        this.InPredmeta=InPredmeta;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getInd() {
        return this.ind;
    }
    public void setInd(int ind) {
        this.ind = ind;
    }

    public int getInPredmeta() {
        return this.InPredmeta;
    }
    public void setInPredmeta(int InPredmeta) {
        this.InPredmeta = InPredmeta;
    }

    public String getKol() {
        return this.kol;
    }
    public void setKol(String kol) {
        this.kol = kol;
    }

}
