package GUI;

import Controller.RuleButtonListener;
import Controller.RuleExecutionButton;
import org.apache.jena.rdf.model.RDFWriter;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Writer;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {

    private JFrame mainWindow = new JFrame("Rule - based Reasoning");
    private JTextArea ruleTextArea = new JTextArea();
    private JTextArea rdfPreviewArea = new JTextArea();

    private JPanel mainPanel = new JPanel();
    private JPanel topLine = new JPanel();
    private JPanel midLine = new JPanel();
    private JPanel bottomLine = new JPanel();
    private JPanel topLeftPanel = new JPanel();
    private JPanel topMidPanel = new JPanel();
    private JPanel topRightPanel = new JPanel();
    private JScrollPane midLeftPanel = new JScrollPane(rdfPreviewArea);
    private JScrollPane midCenterPanel = new JScrollPane(ruleTextArea);
    private JPanel midRightPanel = new JPanel();
    private JPanel bottomLeftPanel = new JPanel();
    private JPanel bottomMidPanel = new JPanel();
    private JPanel bottomRightPanel = new JPanel();


    private JPanel detailsPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");
    private JMenuItem exit = new JMenuItem("Exit");

    /*
     * Allgemeine Layouts zum Anordnen untergeordneter JPanels
     */
    private BoxLayout mainPanelLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
    private BoxLayout topLineLayout = new BoxLayout(topLine, BoxLayout.X_AXIS);
    private BoxLayout bottomLineLayout = new BoxLayout(bottomLine, BoxLayout.X_AXIS);
    private BoxLayout midLineLayout = new BoxLayout(midLine, BoxLayout.X_AXIS);

    /*
     *  Layouts für die internen JPanels die auf das untere bzw. obere JPanel gelegt werden
     */
    private BorderLayout topLeftLayout = new BorderLayout();
    private BorderLayout topRightLayout = new BorderLayout();
    private BorderLayout midLeftLayout = new BorderLayout();
    private BorderLayout bottomLeftLayout = new BorderLayout();
    private BoxLayout bottomMidLayout = new BoxLayout(bottomMidPanel, BoxLayout.Y_AXIS);

    //private BoxLayout midRightLayout = new BoxLayout(midRightPanel, BoxLayout.Y_AXIS);
    private GridLayout midRightLayout = new GridLayout(0,3, 30, 20);

    private BoxLayout bottomRightLayout = new BoxLayout(bottomRightPanel, BoxLayout.Y_AXIS);

    /*
     * Im Fenster sichtbare Komponenten und verwendete Listen zum Füllen der Elemente
     */
    private JButton executeRule = new JButton("Execute Rule");
    private JButton newRule = new JButton("ADD New Rule");
    private JButton rule0 = new JButton("ADD Triple (?a ?b ?c)");
    private JButton rule1 = new JButton("ADD isLiteral(?x)");
    private JButton rule2 = new JButton("ADD notLiteral(?x)");
    private JButton rule3 = new JButton("ADD lessThan(?x, ?y)");
    private JButton rule4 = new JButton("ADD greaterThan(?x, ?y)");
    private JButton rule5 = new JButton("ADD sum(?a, ?b, ?c)");
    private JButton rule6 = new JButton("ADD addOne(?a, ?c)");
    private JButton rule7 = new JButton("CLEAR");


    public Main(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();





        executeRule.setEnabled(true);

       /*
        * Komponenten der TopLine werden zusammengefügt, ein Label, eine Combobox, sowie der Button für die Bonusaufgabe
        */

        topLeftPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.6), (int)(screenSize.height*0.1)));
        topLeftPanel.setLayout(topLeftLayout);


        topMidPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.25), (int)(screenSize.height*0.05)));

        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

        topRightPanel.setLayout(topRightLayout);
        topRightPanel.add(detailsPanel, BorderLayout.CENTER);
        topRightPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.2), (int)(screenSize.height*0.05)));


        topLine.setLayout(topLineLayout);
        topLine.add(topLeftPanel);
        topLine.add(topMidPanel);
        topLine.add(topRightPanel);
        topLine.setBorder(new EmptyBorder(10, 10, 30, 0));

       /*
        * Komponenten der MidLine werden zusammengefügt (Tabelle in JScrollPane + Modell für die Tabelle
        */

        //ruleTextArea.setLineWrap(true);
        midCenterPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.33), (int)(screenSize.height*0.4)));
        midLeftPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.33), (int)(screenSize.height*0.4)));
        midLeftPanel.setBorder(new EmptyBorder(0,10,0,10));
        midRightPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.33), (int)(screenSize.height*0.4)));
        midRightPanel.setLayout(midRightLayout);
        midRightPanel.setBorder(new EmptyBorder(0,20,0,20));

        newRule.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        rule0.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        rule1.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        rule2.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        rule3.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        rule4.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        rule5.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        rule6.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        rule7.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height)*4));
        newRule.addActionListener(new RuleButtonListener(" [rule1:]", ruleTextArea));
        rule0.addActionListener(new RuleButtonListener(" (?a ?b ?c)", ruleTextArea));
        rule1.addActionListener(new RuleButtonListener(" isLiteral(?x)", ruleTextArea));
        rule2.addActionListener(new RuleButtonListener(" notLiteral(?x)", ruleTextArea));
        rule3.addActionListener(new RuleButtonListener(" lessThan(?x, ?y)", ruleTextArea));
        rule4.addActionListener(new RuleButtonListener(" greaterThan(?x, ?y)", ruleTextArea));
        rule5.addActionListener(new RuleButtonListener(" sum(?a, ?b, ?c)", ruleTextArea));
        rule6.addActionListener(new RuleButtonListener(" addOne(?a, ?c)", ruleTextArea));
        rule7.addActionListener(new RuleButtonListener("", ruleTextArea));
        midRightPanel.add(newRule);
        midRightPanel.add(rule0);
        midRightPanel.add(rule1);
        midRightPanel.add(rule2);
        midRightPanel.add(rule3);
        midRightPanel.add(rule4);
        midRightPanel.add(rule5);
        midRightPanel.add(rule6);
        midRightPanel.add(rule7);



        //midLine = new JScrollPane(ruleTextArea);
        midLine.setLayout(midLineLayout);
        midLine.add(midLeftPanel);
        midLine.add(midCenterPanel);
        midLine.add(midRightPanel);
        midLine.setPreferredSize(new Dimension((int)(screenSize.width*0.66), (int)(screenSize.height)));
        ruleTextArea.setFont(new Font(ruleTextArea.getFont().getName(), 0, 20));
        ruleTextArea.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new EtchedBorder(Color.GRAY, Color.DARK_GRAY)));
        rdfPreviewArea.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new EtchedBorder(Color.GRAY, Color.DARK_GRAY)));
        rdfPreviewArea.setFont(new Font(ruleTextArea.getFont().getName(), 0, 20));
        rdfPreviewArea.setEditable(false);

        rdfPreviewArea.setText("<rdf:RDF\n" +
                "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
                "    xmlns:eg=\"urn:x-hp:eg/\" > \n" +
                "  <rdf:Description rdf:about=\"urn:x-hp:eg/A\">\n" +
                "    <eg:p rdf:resource=\"urn:x-hp:eg/B\"/>\n" +
                "  </rdf:Description>\n" +
                "  <rdf:Description rdf:about=\"urn:x-hp:eg/C\">\n" +
                "    <eg:p rdf:resource=\"urn:x-hp:eg/D\"/>\n" +
                "  </rdf:Description>\n" +
                "  <rdf:Description rdf:about=\"urn:x-hp:eg/B\">\n" +
                "    <eg:p rdf:resource=\"urn:x-hp:eg/C\"/>\n" +
                "  </rdf:Description>\n" +
                "</rdf:RDF>");
       /*
        * Komponenten für die BottomLine werden zusammengefügt. 2 JPanels, die jeweils eine Tabelle
        * und ein Label enthalten sowie ein Panel, dass 1 Label und 2 Buttons enthält
        */
        bottomLeftPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.33), (int)(screenSize.height*0.4)));
        bottomLeftPanel.setLayout(bottomLeftLayout);

        bottomLeftPanel.setBorder(new EmptyBorder(0,10,10,10));



        bottomMidPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.33), (int)(screenSize.height*0.5)));
        bottomMidPanel.setLayout(bottomMidLayout);
        executeRule.setMaximumSize(new Dimension((int)(screenSize.width*0.2), (int)(screenSize.height*4)));
        RuleExecutionButton listener = new RuleExecutionButton(ruleTextArea);
        executeRule.addActionListener(listener);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        listener.getData().write(outputStream);
        String rdfInput = new String(outputStream.toByteArray());
        rdfPreviewArea.setText(rdfInput);

        Component glue1 = Box.createVerticalGlue();
        glue1.setMaximumSize(new Dimension(0, 1000));
        bottomMidPanel.add(glue1);
        Component glue2 = Box.createVerticalGlue();
        glue2.setMaximumSize(new Dimension(0, 2000));
        bottomMidPanel.add(glue2);
        bottomMidPanel.add(executeRule);

        bottomMidPanel.setBorder(new EmptyBorder(0,10,10,10));


        bottomRightPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.2), (int)(screenSize.height*0.2)));






        Component glue3 = Box.createVerticalGlue();
        glue3.setMaximumSize(new Dimension(0, 4000));
        bottomMidPanel.add(glue3);




        bottomLine.setLayout(bottomLineLayout);




        bottomLine.add(bottomLeftPanel);
        bottomLine.add(bottomMidPanel);
        bottomLine.add(bottomRightPanel);

        bottomLine.setPreferredSize(new Dimension((int)(screenSize.width*0.66), (int)(screenSize.height*0.5)));


       /*
        * Die 3 Lines werden zunächst auf dem MainPanel (JPanel) und dann zusammen mit dem JMenu auf dem MainWindow (JFrame) verankert
        */
        mainPanel.setLayout(mainPanelLayout);

        mainPanel.add(topLine);
        mainPanel.add(midLine);
        mainPanel.add(bottomLine);

        menu.add(exit);
        menuBar.add(menu);


        mainWindow.setJMenuBar(menuBar);
        mainWindow.add(mainPanel);
        mainWindow.setBounds(0,0,(int)(screenSize.width*0.9), (int)(screenSize.height*0.9));
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }

    public static void main(String[] args){
        Main main = new Main();
    }


    }
