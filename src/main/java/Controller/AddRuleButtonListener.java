package Controller;

import GUI.ReasonerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddRuleButtonListener implements ActionListener {

    private ReasonerPanel reasonerPanel;

    public AddRuleButtonListener(ReasonerPanel reasonerPanel){
        this.reasonerPanel = reasonerPanel;
    }


    public void actionPerformed(ActionEvent e) {

    }
}
