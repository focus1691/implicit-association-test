package com.example.josh.iat;

import io.realm.RealmObject;

public class Record extends RealmObject {
    private String firstName;
    private String lastName;
    private String ethnicity;
    private String gender;
    private int age;
    private String country;
    private double trials;
    private double mean;
    private double SD;
    private double SE;
    private int df;
    private double T;
    private int N;
    private boolean significance;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTrials() {
        return trials;
    }

    public void setTrials(double trials) {
        this.trials = trials;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getSD() {
        return SD;
    }

    public void setSD(double SD) {
        this.SD = SD;
    }

    public double getSE() {
        return SE;
    }

    public void setSE(double SE) {
        this.SE = SE;
    }

    public int getDf() {
        return df;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public double getT() {
        return T;
    }

    public void setT(double t) {
        T = t;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public boolean isSignificance() {
        return significance;
    }

    public void setSignificance(boolean significance) {
        this.significance = significance;
    }
}