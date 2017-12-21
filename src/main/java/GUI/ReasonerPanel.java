package GUI;

import Controller.StartReasoningButtonListener;
import Controller.UpdateRDFTableListener;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import org.visualdataweb.vowl.protege.VOWLControlViewComponent;
import org.visualdataweb.vowl.protege.VOWLSideBarComponent;
import org.visualdataweb.vowl.protege.VOWLViewComponent;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class ReasonerPanel extends JPanel{



    public final Model model = ModelFactory.createDefaultModel();


    public VOWLViewComponent vowlViewComponent = new VOWLViewComponent();
    public VOWLSideBarComponent vowlSideBarComponent = new VOWLSideBarComponent();
    public VOWLControlViewComponent vowlControlViewComponent = new VOWLControlViewComponent();

    public InstanceReasoningScrollPane rightInstanceReasoningSubPanel = new InstanceReasoningScrollPane();

    public RDFTable rdfTable = new RDFTable(this);

    public ArrayList<String> fileNames = new ArrayList<>();
    JPanel schemaReasoningPanel = new JPanel();


    public ReasonerPanel(){

         JTabbedPane jTabbedPane = new JTabbedPane();


        JSplitPane instanceReasoningPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JSplitPane leftInstanceReasoningPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JScrollPane ontologyTablePane = new JScrollPane(rdfTable);
        //JScrollPane csvTablePanel = new JScrollPane(rdfTable);

         JPanel rightInstanceReasoningPanel = new JPanel();
         JPanel instanceReasoningSubPanel = new JPanel();


         JPanel leftSchemaReasoningPanel = new JPanel();
         JPanel rightSchemaReasoningPanel = new JPanel();



        /*
     * Allgemeine Layouts zum Anordnen untergeordneter JPanels
     */
         BoxLayout reasonerPanelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

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
        //leftInstanceReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.3), (int)(Main.screenSize.height*0.75)));
        leftInstanceReasoningPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));
        leftInstanceReasoningPanel.setTopComponent(ontologyTablePane);
        //leftInstanceReasoningPanel.setBottomComponent(csvTablePanel);

        //rightInstanceReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.7), (int)(Main.screenSize.height*0.75)));
        rightInstanceReasoningPanel.setLayout(rightInstanceReasoningPanelLayout);
        rightInstanceReasoningPanel.setBorder(new CompoundBorder(new EmptyBorder(40,10,40,10),new MatteBorder(2,2,2,2, Color.BLACK)));

        rightInstanceReasoningPanel.add(this.rightInstanceReasoningSubPanel);
        this.rightInstanceReasoningSubPanel.setBorder(new EmptyBorder(10,0,10,0));



        updateInstaceDataButton.addActionListener(new UpdateRDFTableListener(this));

        instanceReasoningSubPanel.add(updateInstaceDataButton);
        start_reasoning.addActionListener(new StartReasoningButtonListener(this.rightInstanceReasoningSubPanel, this));
        instanceReasoningSubPanel.add(start_reasoning);
        instanceReasoningSubPanel.setBorder(new EmptyBorder(0,0,50,0));



        //instanceReasoningPanel.setLayout(instanceReasoningPanelLayout);
        instanceReasoningPanel.setLeftComponent(leftInstanceReasoningPanel);
        instanceReasoningPanel.setRightComponent(rightInstanceReasoningPanel);
        instanceReasoningPanel.setBorder(new CompoundBorder(new EtchedBorder(Color.GRAY, Color.DARK_GRAY),new EmptyBorder(10,10,10,10)));



        leftSchemaReasoningPanel.setLayout(leftSchemaReasoningPanelLayout);
        leftSchemaReasoningPanel.add(vowlViewComponent);
        leftSchemaReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.5), (int)(Main.screenSize.height)));

        rightSchemaReasoningPanel.setPreferredSize(new Dimension((int)(Main.screenSize.width*0.5), (int)(Main.screenSize.height)));
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
        this.add(instanceReasoningSubPanel);
    }




}
