package Controller;

import org.apache.jena.base.Sys;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RuleButtonListener implements ActionListener {

    private String rulePrimitive;
    private JTextArea ruleWindow;

    public RuleButtonListener(String rulePrimitive, JTextArea ruleWindow){
        this.rulePrimitive = rulePrimitive;
        this.ruleWindow =ruleWindow;
    }


    public void actionPerformed(ActionEvent e) {
        String newText = ruleWindow.getText();
        if (rulePrimitive.contains("rule")){
            ruleWindow.setText(ruleWindow.getText()+"\n"+rulePrimitive);
        }else if(rulePrimitive.equals("")) {
                ruleWindow.setText("");
            }else{
                String [] splitArray = newText.split("]");
                newText ="";
                for(int i= 0; i < splitArray.length; i++){
                    if(i < splitArray.length-1){
                        newText += splitArray[i];
                    }else{
                        newText+= splitArray[i]+rulePrimitive+"]";
                    }
                }
                ruleWindow.setText(newText);

            }
    }

}
