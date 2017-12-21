package GUI.DragAndDrop;

import Controller.DragAndDrop.*;
import Controller.NodePopupMenu;
import GUI.InstanceReasoningScrollPane;
import GUI.ReasonerPanel;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DraggableJPanel extends JPanel {


    public static DataFlavor COMPONENT_FLAVOR;
    public mxGraphComponent mxGraphComponent;
    private TransferHandler handler = new ComponentHandler();

    MouseListener listener = new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e)
        {
            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.MOVE);
        }
    };


    public DraggableJPanel(mxGraphComponent mxGraphComponent, ReasonerPanel reasonerPanel, InstanceReasoningScrollPane instanceReasoningScrollPane){
        try{
            COMPONENT_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" + Component[].class.getName() + "\"");
        }
        catch(Exception e){
            System.out.println(e);
        }

        setTransferHandler( new PanelHandler() );
        mxGraphComponent.getGraphControl().addMouseListener(new mxGraphComponentDropDownListener(mxGraphComponent, reasonerPanel, instanceReasoningScrollPane));
        mxGraphComponent.addMouseListener(new HighlightListener(mxGraphComponent));


        mxGraphComponent.setBorder(new CompoundBorder(new MatteBorder(2,2,2,2, Color.BLACK), new EmptyBorder(20,10,20,10)));
        mxGraphComponent.addMouseListener( listener );
        mxGraphComponent.getViewport().getView().addMouseListener(new HighlightListener(mxGraphComponent));
        mxGraphComponent.setTransferHandler( handler );
        mxGraphComponent.addKeyListener(new DraggablePanelDeleteListener(instanceReasoningScrollPane));
        this.mxGraphComponent = mxGraphComponent;

        add(this.mxGraphComponent);
    }



    public DraggableJPanel(){
        try{
            COMPONENT_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" + Component[].class.getName() + "\"");
        }
        catch(Exception e){
            System.out.println(e);
        }
        setTransferHandler( new PanelHandler() );
    }

    public void removemxGraphComponent(){
        this.remove(mxGraphComponent);
    }
}
