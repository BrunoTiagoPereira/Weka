package com.codebind;
import java.util.Random;

import com.codebind.DataSet.Dtos.HeartInstance;
import com.codebind.Dtos.IBKResult;
import com.codebind.Dtos.J48Result;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;


public class Weka {

    private String filePath;
    private Instances instances;
    public double correctInstances = 0;

    public Weka(String filePath){
        this.filePath = filePath;
    }

    public void readData() throws Exception{
        DataSource source = new DataSource(this.filePath);
        instances = source.getDataSet();
        if(instances.classIndex() == -1)
            instances.setClassIndex(instances.numAttributes() - 1);
    }


    public J48Result J48(HeartInstance toClassify) throws Exception {
        J48 tree = this.getTree();

        Evaluation evaluation;
        evaluation = new Evaluation(instances);
        evaluation.evaluateModel(tree, instances);

        Instance newInst = getWekaInstanceFromHeartInstance(toClassify);

        var classIndex = toClassify.getNumAttributes();

        var result = new J48Result();
        result.tree = tree;
        result.correctInstances = evaluation.correct();
        result.classPredict = getPredictedClass(tree,newInst);

        var teste = evaluation.truePositiveRate(classIndex);
        result.precision = getPrecision(evaluation.truePositiveRate(classIndex), evaluation.falsePositiveRate(classIndex));
        return result;
    }

    public J48Result J48CrossEvaluation(HeartInstance toClassify) throws  Exception{
        J48 tree = this.getTree();

        Instance newInst = getWekaInstanceFromHeartInstance(toClassify);

        Evaluation crossEvaluation;
        crossEvaluation = new Evaluation(instances);
        crossEvaluation.crossValidateModel(tree,instances,10,new Random(1));

        var classIndex = toClassify.getNumAttributes();

        var result = new J48Result();

        result.tree = tree;
        result.correctInstances = crossEvaluation.correct();
        result.classPredict = getPredictedClass(tree,newInst);
        //result.precision = getPrecision(crossEvaluation.truePositiveRate(classIndex), crossEvaluation.falsePositiveRate(classIndex));

        return result;
    }

    public IBKResult IBK(HeartInstance toClassify) throws  Exception{
        IBk k3 = new IBk(3);
        k3.buildClassifier(instances);

        Instance newInst = getWekaInstanceFromHeartInstance(toClassify);

        String predictedClass = getPredictedClass(k3,newInst);

        Evaluation evaluation;
        evaluation = new Evaluation(instances);
        evaluation.evaluateModel(k3, instances);

        var classIndex = toClassify.getNumAttributes();


        var result = new IBKResult();
        result.classPredict = predictedClass;
        //result.precision = getPrecision(evaluation.truePositiveRate(classIndex), evaluation.falsePositiveRate(classIndex));

        return result;
    }

    public J48 getTree() throws  Exception{
        J48 tree = new J48();
        tree.buildClassifier(instances);

        return tree;
    }

    public double getPrecision(double truePositive, double falsePositive) {
        return (truePositive/ (truePositive + falsePositive));
    }
    public String getPredictedClass(Classifier classifier, Instance instance) throws Exception {
        int attributesCount = new HeartInstance().getNumAttributes();
        double pred = classifier.classifyInstance(instance);
        //busca a última coluna (classe)
        Attribute attribute = instances.attribute(attributesCount);
        String predictedClass = attribute.value((int) pred);

        return predictedClass;
    }

    public Instance getWekaInstanceFromHeartInstance(HeartInstance instance) throws Exception{
        var newInst = new DenseInstance(instance.getNumAttributes());
        newInst.setDataset(instances);

        var fields = instance.getFields();
        for(int i = 0; i < instance.getNumAttributes(); i++){
            newInst.setValue(i,(double)fields[i].get(instance));
        }

        return newInst;
    }

    public String showInstances(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < instances.numInstances(); i++) {
            Instance current = instances.instance(i);
            sb.append((i + 1) + ": " + current + "\n");
        }

        return sb.toString();
    }
}
