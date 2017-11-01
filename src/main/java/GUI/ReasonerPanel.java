package GUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReasonerPanel extends JPanel{

    private JTextArea ruleTextArea = new JTextArea();
    private JPanel reasonerMidLine = new JPanel();
    private JLabel vocabulary = new JLabel("Vocabulary");

    public JTable rdfTable = new JTable();
    private JScrollPane midLeftPanel = new JScrollPane(rdfTable);
    //private JScrollPane midCenterPanel = new JScrollPane(ruleTextArea);
    private JPanel midRightPanel = new JPanel();
    private JPanel midRightSubPanel0 = new JPanel();
    private JPanel midRightSubPanel0Helper = new JPanel();
    private JPanel midRightSubPanel0Helper1 = new JPanel();
    private JPanel midRightSubPanel1 = new JPanel();
    private JPanel midRightSubPanel2 = new JPanel();
    private JPanel midRightSubPanel3 = new JPanel();
    private JScrollPane midRightSubPanel4 = new JScrollPane(ruleTextArea);
    private JPanel midRightSubPanel5 = new JPanel();

    public JComboBox<String> subjectComboBox = new JComboBox<>();
    public JComboBox<String> predicateComboBox = new JComboBox<>();
    public JComboBox<String> objectComboBox = new JComboBox<>();


    public DefaultTableModel dtm = new DefaultTableModel();


    /*
     * Allgemeine Layouts zum Anordnen untergeordneter JPanels
     */
    private BoxLayout reasonerPanelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

    private BoxLayout midLineLayout = new BoxLayout(reasonerMidLine, BoxLayout.X_AXIS);
    private BoxLayout midRightLayout = new BoxLayout(midRightPanel, BoxLayout.Y_AXIS);
    private BoxLayout midRightSubPanel0Layout = new BoxLayout(midRightSubPanel0, BoxLayout.X_AXIS);
    private BoxLayout midRightSubPanel1Layout = new BoxLayout(midRightSubPanel1, BoxLayout.X_AXIS);
    private BoxLayout midRightSubPanel2Layout = new BoxLayout(midRightSubPanel2, BoxLayout.X_AXIS);
    private BoxLayout midRightSubPanel3Layout = new BoxLayout(midRightSubPanel3, BoxLayout.X_AXIS);

    /*
     * Im Fenster sichtbare Komponenten und verwendete Listen zum Füllen der Elemente
     */
    private JButton executeRule = new JButton("Execute Rule");
    private JButton newRule = new JButton("Add new rule");
    private JButton add_subject = new JButton("Add subject");
    private JButton add_predicate = new JButton("Add predicate");
    private JButton add_object = new JButton("Add object");
    private JButton add_condition = new JButton("Add condition");
    private JButton add_conclusion = new JButton("Add conclusion");
    private JButton open_mathematical_operators = new JButton("Open mathematical operators");
    private JButton addToRuleSet = new JButton("Add rule to ruleset");
    private JButton start_reasoning = new JButton("Start reasoning - process");
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();



    public ReasonerPanel(){
        executeRule.setEnabled(true);

       /*
        * Komponenten der TopLine werden zusammengefügt, ein Label, eine Combobox, sowie der Button für die Bonusaufgabe
        */






       /*
        * Komponenten der MidLine werden zusammengefügt (Tabelle in JScrollPane + Modell für die Tabelle
        */


        dtm.addColumn("Subject");
        dtm.addColumn("Predicate");
        dtm.addColumn("Object");
        rdfTable.setModel(dtm);


        //midCenterPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.33), (int)(screenSize.height*0.75)));
        midLeftPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.5), (int)(screenSize.height*0.75)));
        midLeftPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        //midCenterPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        midRightPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.5), (int)(screenSize.height*0.75)));
        midRightPanel.setLayout(midRightLayout);
        midRightPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        midRightPanel.add(midRightSubPanel0);
        midRightPanel.add(midRightSubPanel1);
        midRightPanel.add(midRightSubPanel2);
        midRightPanel.add(midRightSubPanel3);
        midRightPanel.add(midRightSubPanel4);
        midRightPanel.add(midRightSubPanel5);

        midRightSubPanel0.setLayout(midRightSubPanel0Layout);
        midRightSubPanel0.add(midRightSubPanel0Helper);
        vocabulary.setFont(new Font(vocabulary.getFont().getName(),vocabulary.getFont().getStyle(),20));
        midRightSubPanel0.add(vocabulary);
        midRightSubPanel0.add(midRightSubPanel0Helper1);

        midRightSubPanel1.setLayout(midRightSubPanel1Layout);
        midRightSubPanel1.add(add_subject);
        midRightSubPanel1.add(Box.createRigidArea(new Dimension(50, 0)));
        midRightSubPanel1.add(add_predicate);
        midRightSubPanel1.add(Box.createRigidArea(new Dimension(50, 0)));
        midRightSubPanel1.add(add_object);

        midRightSubPanel2.setLayout(midRightSubPanel2Layout);
        midRightSubPanel2.setPreferredSize(new Dimension((int)(screenSize.width*0.5), (int)(screenSize.height*0.001)));
        midRightSubPanel2.setBorder(new EmptyBorder(40,150,0,150));
        subjectComboBox.setPreferredSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        midRightSubPanel2.add(subjectComboBox);
        midRightSubPanel2.add(Box.createRigidArea(new Dimension(65, 0)));
        predicateComboBox.setPreferredSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        midRightSubPanel2.add(predicateComboBox);
        midRightSubPanel2.add(Box.createRigidArea(new Dimension(50, 0)));
        objectComboBox.setPreferredSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        midRightSubPanel2.add(objectComboBox);

        midRightSubPanel3.setLayout(midRightSubPanel3Layout);
        midRightSubPanel3.setBorder(new EmptyBorder(40,40,40,40));
        midRightSubPanel3.add(add_condition);
        midRightSubPanel3.add(Box.createRigidArea(new Dimension(50, 0)));
        midRightSubPanel3.add(add_conclusion);
        midRightSubPanel3.add(Box.createRigidArea(new Dimension(50, 0)));
        midRightSubPanel3.add(open_mathematical_operators);

        midRightSubPanel4.setPreferredSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.15)));
        midRightSubPanel4.setBorder(new EmptyBorder(10,40,10,40));

        midRightSubPanel5.setBorder(new EmptyBorder(40,40,40,40));
        midRightSubPanel5.add(addToRuleSet);
        midRightSubPanel5.add(Box.createRigidArea(new Dimension(50, 0)));
        midRightSubPanel5.add(start_reasoning);




        reasonerMidLine.setLayout(midLineLayout);
        reasonerMidLine.add(midLeftPanel);
        //reasonerMidLine.add(midCenterPanel);
        reasonerMidLine.add(midRightPanel);
        reasonerMidLine.setPreferredSize(new Dimension((int)(screenSize.width*0.66), (int)(screenSize.height)));
        ruleTextArea.setFont(new Font(ruleTextArea.getFont().getName(), 0, 20));
        ruleTextArea.setBorder(new CompoundBorder(new EtchedBorder(Color.GRAY, Color.DARK_GRAY),new EmptyBorder(10,10,10,10)));



       /*
        * Die 3 Lines werden zunächst auf dem MainPanel (JPanel) und dann zusammen mit dem JMenu auf dem MainWindow (JFrame) verankert
        */
        this.setLayout(reasonerPanelLayout);

        this.add(reasonerMidLine);
    }




}
