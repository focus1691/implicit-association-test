package com.example.josh.iat;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

    protected List<Double> black_man_gun = new ArrayList<Double>();
    protected List<Double> white_man_gun = new ArrayList<Double>();
    protected List<Double> differences = new ArrayList<Double>();
    //protected double[] differences;
    protected int N;
    private int comparisons;
    protected double mean;
    protected double SD;
    protected double SE;
    protected int df;
    protected double T;
    protected boolean significant = false;

    public Statistics(int N) {
        this.N = N;
        comparisons = 2;
        df = N - 1;
    }

    public void addScore(double timeTaken, String image) {
        switch(image) {
            case "Black": black_man_gun.add(timeTaken);
                break;
            case "White": white_man_gun.add(timeTaken);
                break;
        }
    }

    public void computeDifferences() {
        int i;
        for (i = 0; i < comparisons; i++) {
            differences.add(white_man_gun.get(i) - black_man_gun.get(i));
        }
    }

    public void computeMean() {
        int i;
        int total = 0;

        for (i = 0; i < comparisons; i++) {
            total += differences.get(i);
        }
        mean = total / N;
    }

    public void computeSD() {
        int i;
        int total = 0;

        for (i = 0; i < comparisons; i++) {
            total += Math.pow(differences.get(i) - mean, 2);
        }
        total = total / df;
        SD = Math.sqrt(total);
    }

    public void computeSE() {
        SE = SD / (Math.sqrt(N));
    }

    public void computeT() {
        T = (SD - 0) / SE;
    }

    public void computeSignificance() {
        if (T > 1.75) significant = true;
    }
}