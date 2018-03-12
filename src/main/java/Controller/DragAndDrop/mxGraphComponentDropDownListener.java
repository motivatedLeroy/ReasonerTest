package Controller.DragAndDrop;

import Controller.JScrollPopupMenu;
import GUI.InstanceReasoningScrollPane;
import GUI.Main;
import GUI.RDFTable;
import GUI.ReasonerPanel;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.apache.jena.base.Sys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mxGraphComponentDropDownListener implements MouseListener {

    private mxGraphComponent mxGraphComponent;
    private ReasonerPanel reasonerPanel;
    private InstanceReasoningScrollPane instanceReasoningScrollPane;

    public mxGraphComponentDropDownListener(mxGraphComponent mxGraphComponent, ReasonerPanel reasonerPanel, InstanceReasoningScrollPane instanceReasoningScrollPane){
        this.mxGraphComponent = mxGraphComponent;
        this.reasonerPanel = reasonerPanel;
        this.instanceReasoningScrollPane = instanceReasoningScrollPane;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object cell = mxGraphComponent.getCellAt(e.getX(), e.getY());
        try{
            int index = reasonerPanel.rdfTabbedPane.getSelectedIndex();
            JScrollPane scrollPane = (JScrollPane)reasonerPanel.rdfTabbedPane.getComponent(index);
            JViewport viewport = (JViewport)scrollPane.getComponent(0);
            RDFTable rdfTable = (RDFTable)viewport.getComponent(0);

            JScrollPopupMenu popupMenu1 = new JScrollPopupMenu(rdfTable,mxGraphComponent, e.getX()+30 , e.getY()+30, Integer.parseInt(((mxCell)cell).getId()), (mxCell)cell, instanceReasoningScrollPane);
            }catch(NullPointerException ex){
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
