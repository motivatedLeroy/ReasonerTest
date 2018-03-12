package GUI;


import EvaluationClasses.WaterLevells.Values;
import EvaluationClasses.WaterLevells.WaterLevels;
import EvaluationClasses.WeatherData.WeatherDataComplete;
import EvaluationClasses.WeatherData.WeatherDataWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {

    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private JFrame mainWindow = new JFrame("Rule - based Reasoning");
    private JTabbedPane tabbedPane = new JTabbedPane();
    /*private JTextArea ruleTextArea = new JTextArea();
    private JTextArea rdfPreviewArea = new JTextArea();
*/
    public static ReasonerPanel reasonerPanel = new ReasonerPanel();
    private BrokerPanel brokerPanel = new BrokerPanel(reasonerPanel);



    //private JPanel detailsPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");
    private JMenuItem exit = new JMenuItem("Exit");

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

    public static void main(String[] args) throws IOException {

        WeatherDataComplete.loadLiveData();

        Main main = new Main();
    }




}

