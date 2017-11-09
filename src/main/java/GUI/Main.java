package GUI;


import javax.swing.*;
import java.awt.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {

    private JFrame mainWindow = new JFrame("Rule - based Reasoning");
    private JTabbedPane tabbedPane = new JTabbedPane();
    /*private JTextArea ruleTextArea = new JTextArea();
    private JTextArea rdfPreviewArea = new JTextArea();
*/
    private ReasonerPanel reasonerPanel = new ReasonerPanel();
    private BrokerPanel brokerPanel = new BrokerPanel(reasonerPanel);


    //private JPanel detailsPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");
    private JMenuItem exit = new JMenuItem("Exit");
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    /*
     * Allgemeine Layouts zum Anordnen untergeordneter JPanels
     */
    public Main(){


        menu.add(exit);
        menuBar.add(menu);


        mainWindow.setJMenuBar(menuBar);
        tabbedPane.add("Broker", brokerPanel);
        tabbedPane.add("Reasoning Service", reasonerPanel);
        mainWindow.add(tabbedPane);
        mainWindow.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        //mainWindow.setBounds(0,0,(int)(screenSize.width*0.9), (int)(screenSize.height*0.9));
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setVisible(true);



    }

    public static void main(String[] args){

        Main main = new Main();
    }


    }
