package GUI.DragAndDrop;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

class ComponentHandler extends TransferHandler{

    private Container con;

    @Override
    public int getSourceActions(JComponent c){
        con = c.getParent();
        return MOVE;
    }

    @Override
    public Transferable createTransferable(final JComponent c){
        return new Transferable(){
            @Override
            public Object getTransferData(DataFlavor flavor){
                Component[] components = new Component[1];
                components[0] = c;
                return components;
            }

            @Override
            public DataFlavor[] getTransferDataFlavors(){
                DataFlavor[] flavors = new DataFlavor[1];
                flavors[0] = DraggableJPanel.COMPONENT_FLAVOR;
                return flavors;
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor){
                return flavor.equals(DraggableJPanel.COMPONENT_FLAVOR);
            }
        };
    }

    @Override
    public void exportDone(JComponent c, Transferable t, int action){
        System.out.println(action);
        System.out.println(c.getBounds());
        con.repaint();

    }
}
