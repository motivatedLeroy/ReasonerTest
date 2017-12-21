package Controller.DragAndDrop;

import GUI.DragAndDrop.DraggableJPanel;
import GUI.InstanceReasoningScrollPane;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HighlightListener implements MouseListener {

    private mxGraphComponent mxGraphComponent;

    public HighlightListener(mxGraphComponent mxGraphComponent){
        this.mxGraphComponent = mxGraphComponent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component parent = (Component)e.getSource();
        while (!parent.getClass().equals(InstanceReasoningScrollPane.class)){
            parent = parent.getParent();
        }
        InstanceReasoningScrollPane scrollPane = (InstanceReasoningScrollPane) parent;
        int leftCounter = scrollPane.leftsideJPanel.getComponentCount();
        int rightCounter = scrollPane.rightsideJPanel.getComponentCount();
        for(int i = 0; i < leftCounter; i++){
            DraggableJPanel panel = (DraggableJPanel)scrollPane.leftsideJPanel.getComponent(i);
            if(panel.mxGraphComponent != null && !panel.mxGraphComponent.equals(mxGraphComponent)){
                panel.mxGraphComponent.setBorder(new CompoundBorder(new MatteBorder(2,2,2,2, Color.BLACK), new EmptyBorder(20,10,20,10)));
            }
        }

        for(int i = 0; i < rightCounter; i++){
            DraggableJPanel panel = (DraggableJPanel)scrollPane.rightsideJPanel.getComponent(i);
            if(panel.mxGraphComponent != null && !panel.mxGraphComponent.equals(mxGraphComponent)){
                panel.mxGraphComponent.setBorder(new CompoundBorder(new MatteBorder(2,2,2,2, Color.BLACK), new EmptyBorder(20,10,20,10)));
            }
        }
        mxGraphComponent.setBorder(new CompoundBorder(new MatteBorder(2, 2, 2, 2, Color.RED), new EmptyBorder(20, 10, 20, 10)));

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
