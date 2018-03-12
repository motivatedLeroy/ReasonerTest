package GUI.DragAndDrop;

import Controller.DragAndDrop.*;
import GUI.InstanceReasoningScrollPane;
import GUI.ReasonerPanel;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.handler.mxGraphTransferHandler;
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
import java.awt.event.MouseMotionAdapter;

import com.mxgraph.swing.mxGraphComponent.mxGraphControl;

public class DraggableJPanel extends JPanel {


    public static DataFlavor COMPONENT_FLAVOR;
    public mxDragAndDropComponent mxGraphComponent;
    public ReasonerPanel reasonerPanel;

    MouseMotionAdapter listener = new MouseMotionAdapter(){
        @Override
        public void mouseDragged(MouseEvent e)
        {
            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.MOVE);
        }
    };

    MouseMotionAdapter mxGraphControlListener = new MouseMotionAdapter(){
        @Override
        public void mouseDragged(MouseEvent e)
        {
            mxGraphControl c = (mxGraphControl) e.getSource();
            mxGraphComponent p = c.getGraphContainer();
            TransferHandler handler = p.getTransferHandler();
            handler.exportAsDrag(p, e, TransferHandler.MOVE);
        }
    };


    public DraggableJPanel(mxDragAndDropComponent mxGraphComponent, ReasonerPanel reasonerPanel, InstanceReasoningScrollPane instanceReasoningScrollPane){
        try{
            COMPONENT_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" + Component[].class.getName() + "\"");
        }
        catch(Exception e){
            System.out.println(e);
        }

        TransferHandler handler = new ComponentHandler();

        setTransferHandler(new PanelHandler(reasonerPanel));
        mxGraphComponent.getGraphControl().addMouseListener(new mxGraphComponentDropDownListener(mxGraphComponent, reasonerPanel, instanceReasoningScrollPane));
        mxGraphComponent.addMouseListener(new HighlightListener(mxGraphComponent));


        mxGraphComponent.setBorder(new CompoundBorder(new MatteBorder(2,2,2,2, Color.BLACK), new EmptyBorder(20,10,20,10)));
        mxGraphComponent.setTransferHandler(handler);
        mxGraphComponent.addMouseMotionListener( listener );
        mxGraphComponent.getViewport().getView().addMouseListener(new HighlightListener(mxGraphComponent));

        //mxGraphComponent.getViewport().addMouseListener(mxGraphControlListener);
        mxGraphComponent.getViewport().getView().addMouseMotionListener(mxGraphControlListener);
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
        setTransferHandler( new PanelHandler(reasonerPanel) );
    }

    public void removemxGraphComponent(){
        this.remove(mxGraphComponent);
    }
}
