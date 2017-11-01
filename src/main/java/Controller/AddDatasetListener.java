package Controller;

import GUI.AddDatasetJDialog;
import GUI.BrokerPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDatasetListener implements ActionListener {

    private BrokerPanel brokerPanel;


    public AddDatasetListener(BrokerPanel brokerPanel){
        this.brokerPanel = brokerPanel;
    }

    public void actionPerformed(ActionEvent e) {
        AddDatasetJDialog addDatasetJDialog = new AddDatasetJDialog(brokerPanel);
    }




}
