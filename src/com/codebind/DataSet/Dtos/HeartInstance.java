package com.codebind.DataSet.Dtos;

import java.lang.reflect.Field;

public class HeartInstance {

    public HeartInstance(double age,
                         double sex,
                         double cp,
                         double trtbps,
                         double chol,
                         double fbs,
                         double rest_ecg,
                         double thalach,
                         double exng,
                         double oldpeak,
                         double slp,
                         double caa,
                         double thall ) {
        this.age = age;
        this.sex = sex;
        this.cp = cp;
        this.trtbps = trtbps;
        this.chol = chol;
        this.fbs = fbs;
        this.rest_ecg = rest_ecg;
        this.thalach = thalach;
        this.exng = exng;
        this.oldpeak = oldpeak;
        this.slp = slp;
        this.caa = caa;
        this.thall = thall;
    }

    public HeartInstance(){

    }

    public double age;
    public double sex;
    public double cp;
    public double trtbps;
    public double chol;
    public double fbs;
    public double rest_ecg;
    public double thalach;
    public double exng;
    public double oldpeak;
    public double slp;
    public double caa;
    public double thall;

    public int getNumAttributes(){
        return getFields().length;
    }

    public Field[] getFields(){
        return this.getClass().getDeclaredFields();
    }

}
