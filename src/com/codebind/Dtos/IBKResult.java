package com.codebind.Dtos;

import weka.classifiers.trees.J48;

public class IBKResult extends AlgorithmResult{
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("algorithmName => IBK" + "\n");

        sb.append("predict => " + this.classPredict + "\n");

        sb.append("precision => " + this.precision + "\n");
        return sb.toString();
    }
}
