package Controller.DragAndDrop;

import GUI.DragAndDrop.DraggableJPanel;
import GUI.DragAndDrop.mxDragAndDropComponent;
import GUI.InstanceReasoningScrollPane;
import GUI.ReasonerPanel;
import com.mxgraph.swing.mxGraphComponent;
import org.apache.jena.base.Sys;

import javax.swing.*;
import java.awt.*;

public class PanelHandler extends TransferHandler {

    ReasonerPanel reasonerPanel;

    public PanelHandler(ReasonerPanel reasonerPanel){
        this.reasonerPanel = reasonerPanel;
    }

    @Override
    public boolean canImport(TransferSupport support){
        if (!support.isDrop()){
            return false;
        }

        boolean canImport = support.isDataFlavorSupported(DraggableJPanel.COMPONENT_FLAVOR);
        return canImport;
    }

    @Override
    public boolean importData(TransferSupport support){
        if(((DraggableJPanel) support.getComponent()).getComponentCount() < 1){
            if (!canImport(support)) {
                return false;
            }

            Component[] components;

            try {
                components = (Component[]) support.getTransferable().getTransferData(DraggableJPanel.COMPONENT_FLAVOR);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            Component component = components[0];
           /* Container container = (Container)support.getComponent();
            container.add(component);*/

            Component parent = support.getComponent();
            while(!parent.getClass().equals(InstanceReasoningScrollPane.class)){
                parent = parent.getParent();
            }

            InstanceReasoningScrollPane instanceReasoningScrollPane = (InstanceReasoningScrollPane)parent;
            DraggableJPanel draggableJPanel = new DraggableJPanel((mxDragAndDropComponent)component, reasonerPanel,instanceReasoningScrollPane);

            for(int i = 0; i < instanceReasoningScrollPane.leftsideJPanel.getComponentCount(); i++){
                DraggableJPanel draggableJPanel1 = (DraggableJPanel) instanceReasoningScrollPane.leftsideJPanel.getComponent(i);
                if(draggableJPanel.mxGraphComponent.equals(draggableJPanel1.mxGraphComponent)){
                    instanceReasoningScrollPane.rightsideJPanel.add(draggableJPanel);
                    instanceReasoningScrollPane.leftsideJPanel.remove(i);

                    instanceReasoningScrollPane.updateUI();
                    return true;
                }
            }


            for(int i = 0; i < instanceReasoningScrollPane.rightsideJPanel.getComponentCount(); i++){
                DraggableJPanel draggableJPanel1 = (DraggableJPanel) instanceReasoningScrollPane.rightsideJPanel.getComponent(i);
                if(draggableJPanel.mxGraphComponent.equals(draggableJPanel1.mxGraphComponent)){
                    instanceReasoningScrollPane.leftsideJPanel.add(draggableJPanel);
                    instanceReasoningScrollPane.rightsideJPanel.remove(i);

                    instanceReasoningScrollPane.updateUI();

                    return true;
                }
            }




            return true;
            /*System.out.println(jPanel.getParent().getParent().getParent().getClass());
            for(int i = 0; i < jPanel.getComponentCount(); i++){
                DraggableJPanel draggableJPanel = ((DraggableJPanel)jPanel.getComponent(i));

                if(draggableJPanel.mxGraphComponent == null){
                    System.out.println("Leeres Feld gefunden!");
                    draggableJPanel.add(support.getComponent());
                    draggableJPanel.setBackground(Color.BLUE);
                    break;
                }
            }*/

            /*container.getParent().revalidate();
            container.getParent().repaint();
            return true;*/
        }
        return false;
    }
}
