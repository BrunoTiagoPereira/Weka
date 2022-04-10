package com.codebind;

import javax.swing.*;

public class App extends JFrame {
    private static String filePath;

    public App(){

    }
    public JPanel MainPanel;
    private JLabel formTitle;
    private  JTextField ageTxt;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox1;


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



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
