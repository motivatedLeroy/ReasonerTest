package Controller;

import GUI.DragAndDrop.DraggableJPanel;
import GUI.DragAndDrop.mxDragAndDropComponent;
import GUI.InstanceReasoningScrollPane;
import GUI.RDFTable;
import GUI.ReasonerPanel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.apache.jena.base.Sys;
import org.apache.jena.vocabulary.RDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Hashtable;
import GUI.Main;


public class TripleToReasoningSessionListener implements MouseListener {

    private String subject;
    private String predicate;
    private String object;
    private InstanceReasoningScrollPane instanceReasoningScrollPane;
    private RDFTable rdfTable;
    private ReasonerPanel reasonerPanel;

    public TripleToReasoningSessionListener(InstanceReasoningScrollPane instanceReasoningScrollPane, RDFTable rdfTable, ReasonerPanel reasonerPanel){
        this.instanceReasoningScrollPane = instanceReasoningScrollPane;
        this.rdfTable = rdfTable;
        this.reasonerPanel = reasonerPanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            mxGraph graph = new mxGraph();

            Object parent = graph.getDefaultParent();
            Hashtable<String, Object> style = new Hashtable<String, Object>();
            style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(new Color(10,194, 148)));
            style.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
            style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(new Color(0, 0, 0)));
            style.put(mxConstants.STYLE_STROKEWIDTH, 3);
            style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
            style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
            mxStylesheet stylesheet = graph.getStylesheet();
            stylesheet.putCellStyle("Ellipse_Style", style);


            graph.getModel().beginUpdate();
            try{
                subject = ((String)rdfTable.getValueAt(rdfTable.getSelectedRow(), 0));
                object = ((String)rdfTable.getValueAt(rdfTable.getSelectedRow(), 2));
                predicate = (String)rdfTable.getValueAt(rdfTable.getSelectedRow(), 1);



                Object v2 = graph.insertVertex(parent, "2", this.object, (int)(Main.screenWidth*0.18), 0, (int)(Main.screenWidth*0.12),
                        (int)(Main.screenHeight*0.08), "Ellipse_Style");
                ((mxCell)v2).setId("2");
                mxCell cell2 = (mxCell)((mxGraphModel)graph.getModel()).getCell("2");
                cell2.setValue(this.object);

                Object v1 = graph.insertVertex(parent, "0", this.subject, 0, 0, (int)(Main.screenWidth*0.12),
                        (int)(Main.screenHeight*0.08), "Ellipse_Style");
                ((mxCell)v1).setId("0");
                mxCell cell1 = (mxCell)((mxGraphModel)graph.getModel()).getCell("0");
                cell1.setValue(this.subject);

                Object v3 = graph.insertEdge(parent, "1", this.predicate, v1, v2, "Arrow_Style");
                ((mxCell)v3).setId("1");
                mxCell cell3 = (mxCell)((mxGraphModel)graph.getModel()).getCell("1");
                cell3.setValue(this.predicate);
            }
            finally{
                graph.getModel().endUpdate();
            }

            mxDragAndDropComponent graphComponent = new mxDragAndDropComponent(graph, false);
            graphComponent.setEnabled(false);
            DraggableJPanel draggableJPanel = new DraggableJPanel(graphComponent, this.reasonerPanel, instanceReasoningScrollPane);
            instanceReasoningScrollPane.leftsideJPanel.add(draggableJPanel);
            draggableJPanel.setVisible(true);
            instanceReasoningScrollPane.repaint();
            instanceReasoningScrollPane.setDividerLocation(0.5);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
