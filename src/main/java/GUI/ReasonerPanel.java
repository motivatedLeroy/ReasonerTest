package GUI;

import Controller.AddRuleButtonListener;
import Controller.StartReasoningButtonListener;
import org.visualdataweb.vowl.protege.VOWLControlViewComponent;
import org.visualdataweb.vowl.protege.VOWLSideBarComponent;
import org.visualdataweb.vowl.protege.VOWLViewComponent;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ReasonerPanel extends JPanel{

    private JTabbedPane jTabbedPane = new JTabbedPane();

    public JTextArea ruleTextArea = new JTextArea();
    private JPanel schemaReasoningPanel = new JPanel();
    private JPanel instanceReasoningPanel = new JPanel();
    private JLabel vocabulary = new JLabel("Vocabulary");

    public JTable rdfTable = new JTable();
    private JScrollPane leftInstanceReasoningPanel = new JScrollPane(rdfTable);
    private JPanel rightInstanceReasoningPanel = new JPanel();
    private JPanel rightInstanceReasoningSubPanel0 = new JPanel();
    private JPanel rightInstanceReasoningSubPanel0Helper = new JPanel();
    private JPanel rightInstanceReasoningSubPanel1Helper = new JPanel();
    private JPanel rightInstanceReasoningSubPanel1 = new JPanel();
    private JPanel rightInstanceReasoningSubPanel2 = new JPanel();
    private JPanel rightInstanceReasoningSubPanel3 = new JPanel();
    private JScrollPane rightInstanceReasoningSubPanel4 = new JScrollPane(ruleTextArea);
    private JPanel rightInstanceReasoningSubPanel5 = new JPanel();

    private JPanel leftSchemaReasoningPanel = new JPanel();
    private JPanel rightSchemaReasoningPanel = new JPanel();

    public VOWLViewComponent vowlViewComponent = new VOWLViewComponent();
    public VOWLSideBarComponent vowlSideBarComponent = new VOWLSideBarComponent();
    public VOWLControlViewComponent vowlControlViewComponent = new VOWLControlViewComponent();

    public JComboBox<String> subjectComboBox = new JComboBox<>();
    public JComboBox<String> predicateComboBox = new JComboBox<>();
    public JComboBox<String> objectComboBox = new JComboBox<>();

    public ArrayList<String> ruleSet = new ArrayList<>();
    public ArrayList<String> fileNames = new ArrayList<>();

    public DefaultTableModel dtm = new DefaultTableModel();


    /*
     * Allgemeine Layouts zum Anordnen untergeordneter JPanels
     */
    private BoxLayout reasonerPanelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

    private BoxLayout instanceReasoningPanelLayout = new BoxLayout(instanceReasoningPanel, BoxLayout.X_AXIS);
    private BoxLayout rightInstanceReasoningPanelLayout = new BoxLayout(rightInstanceReasoningPanel, BoxLayout.Y_AXIS);
    private BoxLayout rightInstanceReasoningSubPanel0Layout = new BoxLayout(rightInstanceReasoningSubPanel0, BoxLayout.X_AXIS);
    private BoxLayout rightInstanceReasoningSubPanel1Layout = new BoxLayout(rightInstanceReasoningSubPanel1, BoxLayout.X_AXIS);
    private BoxLayout rightInstanceReasoningSubPanel2Layout = new BoxLayout(rightInstanceReasoningSubPanel2, BoxLayout.X_AXIS);
    private BoxLayout rightInstanceReasoningSubPanel3Layout = new BoxLayout(rightInstanceReasoningSubPanel3, BoxLayout.X_AXIS);

    private BoxLayout schemaReasoningPanelLayout = new BoxLayout(schemaReasoningPanel, BoxLayout.X_AXIS);
    private BoxLayout rightSchemaReasoningPanelLayout = new BoxLayout(rightSchemaReasoningPanel, BoxLayout.Y_AXIS);
    private BoxLayout leftSchemaReasoningPanelLayout = new BoxLayout(leftSchemaReasoningPanel, BoxLayout.Y_AXIS);


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


        dtm.addColumn("Subject");
        dtm.addColumn("Predicate");
        dtm.addColumn("Object");
        rdfTable.setModel(dtm);


        //midCenterPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.33), (int)(screenSize.height*0.75)));
        leftInstanceReasoningPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.7), (int)(screenSize.height*0.75)));
        leftInstanceReasoningPanel.setMinimumSize(new Dimension((int)(screenSize.width*0.7), (int)(screenSize.height*0.75)));
        leftInstanceReasoningPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.7), (int)(screenSize.height*0.75)));
        leftInstanceReasoningPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        //midCenterPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        rightInstanceReasoningPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.2), (int)(screenSize.height*0.75)));
        rightInstanceReasoningPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.3), (int)(screenSize.height*0.75)));
        rightInstanceReasoningPanel.setLayout(rightInstanceReasoningPanelLayout);
        rightInstanceReasoningPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        rightInstanceReasoningPanel.add(rightInstanceReasoningSubPanel0);
        rightInstanceReasoningPanel.add(rightInstanceReasoningSubPanel1);
        rightInstanceReasoningPanel.add(rightInstanceReasoningSubPanel2);
        rightInstanceReasoningPanel.add(rightInstanceReasoningSubPanel3);
        rightInstanceReasoningPanel.add(rightInstanceReasoningSubPanel4);
        rightInstanceReasoningPanel.add(rightInstanceReasoningSubPanel5);

        rightInstanceReasoningSubPanel0.setLayout(rightInstanceReasoningSubPanel0Layout);
        rightInstanceReasoningSubPanel0.add(rightInstanceReasoningSubPanel0Helper);
        vocabulary.setFont(new Font(vocabulary.getFont().getName(),vocabulary.getFont().getStyle(),20));
        rightInstanceReasoningSubPanel0.add(vocabulary);
        rightInstanceReasoningSubPanel0.add(rightInstanceReasoningSubPanel1Helper);

        rightInstanceReasoningSubPanel1.setLayout(rightInstanceReasoningSubPanel1Layout);
        rightInstanceReasoningSubPanel1.add(add_subject);
        rightInstanceReasoningSubPanel1.add(Box.createRigidArea(new Dimension(50, 0)));
        rightInstanceReasoningSubPanel1.add(add_predicate);
        rightInstanceReasoningSubPanel1.add(Box.createRigidArea(new Dimension(50, 0)));
        rightInstanceReasoningSubPanel1.add(add_object);

        rightInstanceReasoningSubPanel2.setLayout(rightInstanceReasoningSubPanel2Layout);
        rightInstanceReasoningSubPanel2.setPreferredSize(new Dimension((int)(screenSize.width*0.5), (int)(screenSize.height*0.08)));
        rightInstanceReasoningSubPanel2.setBorder(new EmptyBorder(40,150,0,150));
        subjectComboBox.setPreferredSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        subjectComboBox.setMaximumSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        rightInstanceReasoningSubPanel2.add(subjectComboBox);
        rightInstanceReasoningSubPanel2.add(Box.createRigidArea(new Dimension(65, 0)));
        predicateComboBox.setPreferredSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        predicateComboBox.setMaximumSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        rightInstanceReasoningSubPanel2.add(predicateComboBox);
        rightInstanceReasoningSubPanel2.add(Box.createRigidArea(new Dimension(50, 0)));
        objectComboBox.setPreferredSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        objectComboBox.setMaximumSize(new Dimension((int)(screenSize.width*0.05), (int)(screenSize.height*0.1)));
        rightInstanceReasoningSubPanel2.add(objectComboBox);

        rightInstanceReasoningSubPanel3.setLayout(rightInstanceReasoningSubPanel3Layout);
        rightInstanceReasoningSubPanel3.setBorder(new EmptyBorder(40,0,40,0));
        rightInstanceReasoningSubPanel3.add(add_condition);
        rightInstanceReasoningSubPanel3.add(Box.createRigidArea(new Dimension(50, 0)));
        rightInstanceReasoningSubPanel3.add(add_conclusion);
        rightInstanceReasoningSubPanel3.add(Box.createRigidArea(new Dimension(50, 0)));
        rightInstanceReasoningSubPanel3.add(open_mathematical_operators);

        rightInstanceReasoningSubPanel4.setBorder(new EmptyBorder(10,0,10,0));

        rightInstanceReasoningSubPanel5.setBorder(new EmptyBorder(40,0,40,0));
        addToRuleSet.addActionListener(new AddRuleButtonListener(this));
        rightInstanceReasoningSubPanel5.add(addToRuleSet);
        rightInstanceReasoningSubPanel5.add(Box.createRigidArea(new Dimension(50, 0)));
        start_reasoning.addActionListener(new StartReasoningButtonListener(this));
        rightInstanceReasoningSubPanel5.add(start_reasoning);


        instanceReasoningPanel.setLayout(instanceReasoningPanelLayout);
        instanceReasoningPanel.add(leftInstanceReasoningPanel);
        instanceReasoningPanel.add(rightInstanceReasoningPanel);
        instanceReasoningPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.66), (int)(screenSize.height)));
        ruleTextArea.setFont(new Font(ruleTextArea.getFont().getName(), 0, 20));
        ruleTextArea.setBorder(new CompoundBorder(new EtchedBorder(Color.GRAY, Color.DARK_GRAY),new EmptyBorder(10,10,10,10)));

/*
        rightInstanceReasoningSubPanel0.setBackground(Color.RED);
        rightInstanceReasoningSubPanel1.setBackground(Color.GRAY);
        rightInstanceReasoningSubPanel2.setBackground(Color.GREEN);
        rightInstanceReasoningSubPanel3.setBackground(Color.DARK_GRAY);
        rightInstanceReasoningSubPanel4.setBackground(Color.blue);
        rightInstanceReasoningSubPanel5.setBackground(Color.BLACK);*/


        leftSchemaReasoningPanel.setLayout(leftSchemaReasoningPanelLayout);
        leftSchemaReasoningPanel.add(vowlViewComponent);
        leftSchemaReasoningPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.6), (int)(screenSize.height)));

        rightSchemaReasoningPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.4), (int)(screenSize.height)));
        rightSchemaReasoningPanel.setLayout(rightSchemaReasoningPanelLayout);
        rightSchemaReasoningPanel.add(vowlSideBarComponent);
        rightSchemaReasoningPanel.add(vowlControlViewComponent);
        schemaReasoningPanel.setLayout(schemaReasoningPanelLayout);
        schemaReasoningPanel.add(leftSchemaReasoningPanel);
        schemaReasoningPanel.add(rightSchemaReasoningPanel);

        this.setLayout(reasonerPanelLayout);


        jTabbedPane.add(instanceReasoningPanel,"Instance - Reasoning");
        jTabbedPane.add(schemaReasoningPanel, "OWL - Schema - Reasoning");

        this.add(jTabbedPane);
    }




}
