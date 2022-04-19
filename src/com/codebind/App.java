package com.codebind;

import com.codebind.DataSet.Dtos.HeartInstance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App extends JFrame {
    private static String filePath;

    public App(){

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                var errors = validateInputs();

                boolean hasErrors = !isNullOrEmpty(errors);
                if(hasErrors){
                    JOptionPane.showMessageDialog(null,errors, "Erros", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Weka dt = new Weka(filePath);

                var instance = new HeartInstance();
                instance.age = Double.parseDouble(ageTxt.getText());
                instance.sex = Double.parseDouble(sexCmb.getSelectedItem().toString());
                instance.cp = Double.parseDouble(cpCmb.getSelectedItem().toString());
                instance.trtbps = Double.parseDouble(trtbpsTxt.getText());
                instance.chol = Double.parseDouble(cholTxt.getText());
                instance.fbs = Double.parseDouble(fbsCmb.getSelectedItem().toString());
                instance.rest_ecg = Double.parseDouble(restEcgCmb.getSelectedItem().toString());
                instance.thalach = Double.parseDouble(thalachTxt.getText());
                instance.exng = Double.parseDouble(exngCmb.getSelectedItem().toString());
                instance.oldpeak = Double.parseDouble(oldpeakTxt.getText());
                instance.slp = Double.parseDouble(slpCmb.getSelectedItem().toString());
                instance.caa = Double.parseDouble(caaCmb.getSelectedItem().toString());
                instance.thall = Double.parseDouble(thalachTxt.getText());
                //63,1,3,145,233,1,0,150,0,2.3,0,0,1



                try{
                    dt.readData();
                    var J48Result =  dt.J48(instance);
                    var J48CrossEvaluationResult = dt.J48CrossEvaluation(instance);
                    var IBKResult = dt.IBK(instance);

                    J48Lbl.setText("Precisão: " + J48Result.precision + " " + "Previsão: " + J48Result.classPredict);
                    J48CrossEvaluationLbl.setText("Precisão: " + J48CrossEvaluationResult.precision + " " + "Previsão: " + J48CrossEvaluationResult.classPredict);
                    IBKLbl.setText("Precisão: " + IBKResult.precision + " " + "Previsão: " + IBKResult.classPredict);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public JPanel MainPanel;
    private JLabel formTitle;
    private JTextField ageTxt;
    private JTextField trtbpsTxt;
    private JTextField cholTxt;
    private JComboBox sexCmb;
    private JComboBox cpCmb;
    private JComboBox fbsCmb;
    private JComboBox restEcgCmb;
    private JTextField thalachTxt;
    private JComboBox exngCmb;
    private JTextField oldpeakTxt;
    private JComboBox slpCmb;
    private JComboBox caaCmb;
    private JComboBox thallCmb;
    private JButton submitButton;
    private JLabel J48Lbl;
    private JLabel J48CrossEvaluationLbl;
    private JLabel IBKLbl;


    private static JPanel currentPanel;

    private static void InitUiElements(){
        JFrame f = new JFrame();

        var app = new App();
        var panel = app.MainPanel;
        currentPanel = panel;
        f.setContentPane(panel);

        f.setSize(1200, 720);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }


    public static void main(String[] args){
        InitUiElements();
        filePath = "C:\\Users\\bruno\\Downloads\\heart.arff";
        /*
        filePath = "C:\\Users\\bruno\\Downloads\\heart.arff";
        Weka dt = new Weka(filePath);

        var heartInstance = new HeartInstance(63,1,3,145,233,1,0,150,0,2.3,0,0,1);
        //63,1,3,145,233,1,0,150,0,2.3,0,0,1

        try{
            dt.readData();
            System.out.println( dt.J48(heartInstance));
            System.out.println( dt.J48CrossEvaluation(heartInstance));
            System.out.println( dt.IBK(heartInstance));

        }
        catch (Exception e){

            System.out.println(e);
        }
        */




    }


    private String validateInputs(){

        StringBuilder sb = new StringBuilder();
        var txtFields = getTxtFields();

        var emptyFields = txtFields
                .stream()
                .filter(f -> isNullOrEmpty(f.getText()))
                .map(f-> f.getName())
                .toArray()
                ;

        if(emptyFields.length > 0){
            sb.append("Os campos informados não estão preenchidos: \n");
            for (var fieldName: emptyFields) {
                sb.append(fieldName + "\n");
            }
        }

        var hasLettersFields = txtFields
                .stream()
                .filter(f -> !Arrays.stream(emptyFields).anyMatch(x -> x == f.getName()))
                .filter(f -> hasLetters(f.getText()))
                .map(f-> f.getName() + "\n")
                .toArray()
                ;

        if(hasLettersFields.length > 0){
            sb.append("\n Os campos informados não devem ter letras: \n");
            for (var fieldName: hasLettersFields) {
                sb.append(fieldName);
            }
        }

        return sb.toString();
    }

    private ArrayList<JTextField> getTxtFields(){

        ArrayList<JTextField> txtFields = new ArrayList<>();

        ageTxt.setName("Idade (Age)");
        thalachTxt.setName("Máximo de ritmo do coração (Thalach)");
        trtbpsTxt.setName("Pressão do sangue descansando (Trtbps)");
        cholTxt.setName("Colesterol coletado (Chol)");
        oldpeakTxt.setName("Depressão do segmento ST induzida (Oldpeak)");

        txtFields.add(ageTxt);
        txtFields.add(thalachTxt);
        txtFields.add(trtbpsTxt);
        txtFields.add(cholTxt);
        txtFields.add(oldpeakTxt);

        return txtFields;
    }

    private boolean isNullOrEmpty(String str){
        return str == null || str.isBlank() || str.isEmpty();
    }
    private boolean hasLetters(String str){

        String regex = "^\\d+(\\.\\d+)*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);

        boolean onlyNumbers = matcher.matches();

        return !onlyNumbers;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
