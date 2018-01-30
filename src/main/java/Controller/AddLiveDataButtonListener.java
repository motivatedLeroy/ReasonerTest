package Controller;

import GUI.AddLiveDataJDialog;
import GUI.BrokerPanel;
import com.sun.corba.se.pept.broker.Broker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddLiveDataButtonListener implements ActionListener {
    private BrokerPanel brokerPanel;

    public AddLiveDataButtonListener(BrokerPanel brokerPanel){
        this.brokerPanel = brokerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddLiveDataJDialog jDialog = new AddLiveDataJDialog(brokerPanel);
    }
}
