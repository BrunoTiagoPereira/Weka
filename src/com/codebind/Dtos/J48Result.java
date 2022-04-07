package com.codebind.Dtos;

import weka.classifiers.trees.J48;

public class J48Result extends AlgorithmResult{

    public J48 tree;
    public double correctInstances;
    public J48Result(){

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("algorithmName => J48" + "\n");
        sb.append("tree => \n");
        sb.append(tree + "\n");

        sb.append("correctInstances => " + correctInstances + "\n");

        sb.append("predict => " + this.classPredict + "\n");

        sb.append("precision => " + this.precision + "\n");

        return sb.toString();
    }
}
