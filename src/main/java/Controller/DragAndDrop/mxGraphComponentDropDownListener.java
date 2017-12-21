package Controller.DragAndDrop;

import Controller.NodePopupMenu;
import GUI.InstanceReasoningScrollPane;
import GUI.ReasonerPanel;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

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
            NodePopupMenu popupMenu1 = new NodePopupMenu(mxGraphComponent, e.getX()+30 , e.getY()+30, Integer.parseInt(((mxCell)cell).getId()), (mxCell)cell, reasonerPanel, instanceReasoningScrollPane);
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
