package GUI;

import Controller.AddRuleButtonListener;
import Controller.StartReasoningButtonListener;
import Controller.UpdateRDFTableListener;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.visualdataweb.vowl.protege.VOWLControlViewComponent;
import org.visualdataweb.vowl.protege.VOWLSideBarComponent;
import org.visualdataweb.vowl.protege.VOWLViewComponent;
import prefuse.util.ColorLib;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class ReasonerPanel extends JPanel{



    public JTextArea ruleTextArea = new JTextArea();
    public final Model model = ModelFactory.createDefaultModel();


    public VOWLViewComponent vowlViewComponent = new VOWLViewComponent();
    public VOWLSideBarComponent vowlSideBarComponent = new VOWLSideBarComponent();
    public VOWLControlViewComponent vowlControlViewComponent = new VOWLControlViewComponent();

    public InstanceReasoningScrollPane rightInstanceReasoningSubPanel4 = new InstanceReasoningScrollPane();
    public RDFTable rdfTable = new RDFTable(this);

    public JComboBox<String> subjectComboBox = new JComboBox<>();
    public JComboBox<String> predicateComboBox = new JComboBox<>();
    public JComboBox<String> objectComboBox = new JComboBox<>();

    public ArrayList<String> ruleSet = new ArrayList<>();
    public ArrayList<String> fileNames = new ArrayList<>();












    public ReasonerPanel(){

         JTabbedPane jTabbedPane = new JTabbedPane();

         JPanel schemaReasoningPanel = new JPanel();
         JPanel instanceReasoningPanel = new JPanel();


         JScrollPane leftInstanceReasoningPanel = new JScrollPane(rdfTable);
         JPanel rightInstanceReasoningPanel = new JPanel();

         //JScrollPane rightInstanceReasoningSubPanel4 = new JScrollPane(ruleTextArea);

         //InstanceReasoningScrollPane rightInstanceReasoningSubPanel4 = new InstanceReasoningScrollPane();
         JPanel rightInstanceReasoningSubPanel5 = new JPanel();

         JPanel leftSchemaReasoningPanel = new JPanel();
         JPanel rightSchemaReasoningPanel = new JPanel();



        /*
     * Allgemeine Layouts zum Anordnen untergeordneter JPanels
     */
         BoxLayout reasonerPanelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

         BoxLayout instanceReasoningPanelLayout = new BoxLayout(instanceReasoningPanel, BoxLayout.X_AXIS);
         BoxLayout rightInstanceReasoningPanelLayout = new BoxLayout(rightInstanceReasoningPanel, BoxLayout.Y_AXIS);


         BoxLayout schemaReasoningPanelLayout = new BoxLayout(schemaReasoningPanel, BoxLayout.X_AXIS);
         BoxLayout rightSchemaReasoningPanelLayout = new BoxLayout(rightSchemaReasoningPanel, BoxLayout.Y_AXIS);
         BoxLayout leftSchemaReasoningPanelLayout = new BoxLayout(leftSchemaReasoningPanel, BoxLayout.Y_AXIS);

        /*
     * Im Fenster sichtbare Komponenten und verwendete Listen zum Füllen der Elemente
     */

         JButton updateInstaceDataButton = new JButton("Update Instance - Data");
         JButton start_reasoning = new JButton("Start reasoning - process");







        //midCenterPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.33), (int)(screenSize.height*0.75)));
        leftInstanceReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.5), (int)(Main.screenSize.height*0.75)));
        leftInstanceReasoningPanel.setMinimumSize(new Dimension((int)(Main.screenSize.width*0.5), (int)(Main.screenSize.height*0.75)));
        leftInstanceReasoningPanel.setMaximumSize(new Dimension((int)(Main.screenSize.width*0.5), (int)(Main.screenSize.height*0.75)));
        leftInstanceReasoningPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        //midCenterPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        rightInstanceReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.49), (int)(Main.screenSize.height*0.75)));
        rightInstanceReasoningPanel.setMaximumSize(new Dimension((int)(Main.screenSize.width*0.49), (int)(Main.screenSize.height*0.75)));
        rightInstanceReasoningPanel.setMinimumSize(new Dimension((int)(Main.screenSize.width*0.49), (int)(Main.screenSize.height*0.75)));
        rightInstanceReasoningPanel.setLayout(rightInstanceReasoningPanelLayout);
        rightInstanceReasoningPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));

        //rightInstanceReasoningSubPanel4.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.5), (int)(Main.screenSize.height*0.7)));
        //rightInstanceReasoningSubPanel4.setMaximumSize(new Dimension((int)(Main.screenSize.width*0.5), (int)(Main.screenSize.height*0.7)));
        //rightInstanceReasoningSubPanel4.setMinimumSize(new Dimension((int)(Main.screenSize.width*0.5), (int)(Main.screenSize.height*0.7)));
        rightInstanceReasoningPanel.add(rightInstanceReasoningSubPanel4);
        //rightInstanceReasoningPanel.add(rightInstanceReasoningSubPanel5);


        rightInstanceReasoningSubPanel4.setBorder(new EmptyBorder(10,0,10,0));

        //rightInstanceReasoningSubPanel4.setLayout(jSplitPaneLayout);


        //rightInstanceReasoningSubPanel5.setBorder(new EmptyBorder(40,0,40,0));
        //rightInstanceReasoningSubPanel5.setMaximumSize(new Dimension((int)(Main.screenSize.width*0.2), (int)(Main.screenSize.height*0.1)));

        //addToRuleSet.addActionListener(new AddRuleButtonListener(this));
        updateInstaceDataButton.addActionListener(new UpdateRDFTableListener(this));

        rightInstanceReasoningSubPanel5.add(updateInstaceDataButton);
        start_reasoning.addActionListener(new StartReasoningButtonListener(this));
        rightInstanceReasoningSubPanel5.add(start_reasoning);
        rightInstanceReasoningSubPanel5.setBorder(new EmptyBorder(0,0,50,0));


        instanceReasoningPanel.setLayout(instanceReasoningPanelLayout);
        instanceReasoningPanel.add(leftInstanceReasoningPanel);
        instanceReasoningPanel.add(rightInstanceReasoningPanel);
        instanceReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.66), (int)(Main.screenSize.height)));
        ruleTextArea.setFont(new Font(ruleTextArea.getFont().getName(), 0, 20));
        ruleTextArea.setBorder(new CompoundBorder(new EtchedBorder(Color.GRAY, Color.DARK_GRAY),new EmptyBorder(10,10,10,10)));


        //leftsideJPanel.setBackground(Color.blue);
        //rightsideJPanel.setBackground(Color.BLACK);


        leftSchemaReasoningPanel.setLayout(leftSchemaReasoningPanelLayout);
        leftSchemaReasoningPanel.add(vowlViewComponent);
        leftSchemaReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.6), (int)(Main.screenSize.height)));

        rightSchemaReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.4), (int)(Main.screenSize.height)));
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
        this.add(rightInstanceReasoningSubPanel5);
    }




}
