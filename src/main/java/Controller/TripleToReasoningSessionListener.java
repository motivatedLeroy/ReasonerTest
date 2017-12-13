package Controller;

import GUI.DragAndDrop.DraggableJPanel;
import GUI.InstanceReasoningScrollPane;
import GUI.RDFTable;
import com.mxgraph.model.mxCell;
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
import java.util.Hashtable;

public class TripleToReasoningSessionListener implements MouseListener {

    private String subject;
    private String predicate;
    private String object;
    private InstanceReasoningScrollPane instanceReasoningScrollPane;
    private RDFTable rdfTable;

    public TripleToReasoningSessionListener(InstanceReasoningScrollPane instanceReasoningScrollPane, RDFTable rdfTable){
        this.instanceReasoningScrollPane = instanceReasoningScrollPane;
        this.rdfTable = rdfTable;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            mxGraph graph = new mxGraph();

            Object parent = graph.getDefaultParent();
            Hashtable<String, Object> style = new Hashtable<String, Object>();
            style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(new Color(10,194, 148)));
            style.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
            style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(new Color(255, 0, 0)));
            style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
            style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
            mxStylesheet stylesheet = graph.getStylesheet();
            stylesheet.putCellStyle("Ellipse_Style", style);


            graph.getModel().beginUpdate();
            try{
                String[] subjectArray = ((String)rdfTable.getValueAt(rdfTable.getSelectedRow(), 0)).split("#");
                String[] objectArray = ((String)rdfTable.getValueAt(rdfTable.getSelectedRow(), 2)).split("#");
                subject = subjectArray[subjectArray.length-1];
                predicate = (String)rdfTable.getValueAt(rdfTable.getSelectedRow(), 1);
                object = objectArray[objectArray.length-1];

                Object v1 = graph.insertVertex(parent, "0", this.subject, 0, 0, 140,
                        100, "Ellipse_Style");
                ((mxCell)v1).setId("0");
                Object v2 = graph.insertVertex(parent, "1", this.object, 240, 0,140,
                        100, "Ellipse_Style");
                ((mxCell)v2).setId("2");
                Object v3 = graph.insertEdge(parent, "2", this.predicate, v1, v2);
                ((mxCell)v3).setId("1");

            }
            finally{
                graph.getModel().endUpdate();
            }

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            graphComponent.setEnabled(false);
            DraggableJPanel draggableJPanel = new DraggableJPanel(graphComponent);
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
