package com.codebind;

import com.codebind.DataSet.Dtos.HeartInstance;

import javax.swing.*;

public class App extends JFrame {
    private JPanel MainPanel;

    private static String filePath;

    private static void InitUiElements(){
        JFrame f = new JFrame();
        f.setContentPane(new App().MainPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }

    public static void main(String[] args){
        InitUiElements();

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
        catch (Exception e){ }



    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
