package Controller.DragAndDrop;

import GUI.DragAndDrop.DraggableJPanel;
import GUI.InstanceReasoningScrollPane;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DraggablePanelDeleteListener implements KeyListener {

    private InstanceReasoningScrollPane instanceReasoningScrollPane;

    public DraggablePanelDeleteListener(InstanceReasoningScrollPane instanceReasoningScrollPane){
        this.instanceReasoningScrollPane = instanceReasoningScrollPane;
        //Hijack the keyboard manager

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode() == 127){
            int leftCounter = instanceReasoningScrollPane.leftsideJPanel.getComponentCount();
            int rightCounter = instanceReasoningScrollPane.rightsideJPanel.getComponentCount();
            for(int i = 0; i < leftCounter; i++){
                DraggableJPanel panel = (DraggableJPanel)instanceReasoningScrollPane.leftsideJPanel.getComponent(i);
                if(panel.mxGraphComponent != null){
                    CompoundBorder border = (CompoundBorder)panel.mxGraphComponent.getBorder();
                    MatteBorder matteBorder = (MatteBorder)border.getOutsideBorder();
                    if(matteBorder.getMatteColor().equals(Color.RED)){
                        instanceReasoningScrollPane.leftsideJPanel.remove(panel);
                        instanceReasoningScrollPane.leftsideJPanel.repaint();
                    }
                }

            }

            for(int i = 0; i < rightCounter; i++){
                DraggableJPanel panel = (DraggableJPanel)instanceReasoningScrollPane.rightsideJPanel.getComponent(i);
                if(panel.mxGraphComponent != null){
                    CompoundBorder border = (CompoundBorder)panel.mxGraphComponent.getBorder();
                    MatteBorder matteBorder = (MatteBorder)border.getOutsideBorder();
                    if(matteBorder.getMatteColor().equals(Color.RED)){
                        instanceReasoningScrollPane.rightsideJPanel.remove(panel);
                        instanceReasoningScrollPane.rightsideJPanel.repaint();
                    }
                }

            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}



//Custom dispatcher

