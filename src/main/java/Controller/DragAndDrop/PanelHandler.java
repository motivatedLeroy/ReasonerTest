package Controller.DragAndDrop;

import GUI.DragAndDrop.DraggableJPanel;

import javax.swing.*;
import java.awt.*;

public class PanelHandler extends TransferHandler {

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
            Container container = (Container) support.getComponent();
            container.add(component);

            container.getParent().revalidate();
            container.getParent().repaint();

            JLabel label = (JLabel) component;
            DropLocation location = support.getDropLocation();
            label.setLocation(location.getDropPoint());
            return true;
        }
        return false;
    }
}
