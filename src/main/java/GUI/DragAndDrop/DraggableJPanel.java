package GUI.DragAndDrop;

import Controller.NodePopupMenu;
import com.github.jsonldjava.utils.Obj;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DraggableJPanel extends JPanel {

    public static DataFlavor COMPONENT_FLAVOR;
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


    public DraggableJPanel(mxGraphComponent mxGraphComponent){
        JPanel content = new JPanel();
        try{
            COMPONENT_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" + Component[].class.getName() + "\"");
        }
        catch(Exception e){
            System.out.println(e);
        }

        setTransferHandler( new PanelHandler() );
        //setBorder(new MatteBorder(2,2,2,2, Color.BLACK));


        // Handle only mouse click events
        mxGraphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Object cell = mxGraphComponent.getCellAt(e.getX(), e.getY());
                //if (cell != null && ((mxCell)cell).isVertex()) {
                NodePopupMenu popupMenu1 = new NodePopupMenu(mxGraphComponent, e.getX()+30 , e.getY()+30, Integer.parseInt(((mxCell)cell).getId()), (mxCell)cell);
                //}
            }
        });





        mxGraphComponent.setSize(new Dimension(400,400));
        mxGraphComponent.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new CompoundBorder(new MatteBorder(2,2,2,2, Color.BLACK), new EmptyBorder(20,20,20,20))));
        //mxGraphComponent.setBackground(Color.GREEN);
        mxGraphComponent.addMouseListener( listener );
        mxGraphComponent.setTransferHandler( handler );
        add( mxGraphComponent );
        /*content.setSize(new Dimension(400,400));
        content.setBorder(new MatteBorder(10,10,10,10, Color.BLUE));
        content.setBackground(Color.RED);
        content.addMouseListener( listener );
        content.setTransferHandler( new PanelHandler() );
        add( content );*/

    }


    public DraggableJPanel(){
        JPanel content = new JPanel();
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        try{
            COMPONENT_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" + Component[].class.getName() + "\"");
        }
        catch(Exception e){
            System.out.println(e);
        }

        setTransferHandler( new PanelHandler() );

    }





    public void addmxGraphComponent(mxGraphComponent mxGraphComponent){
        mxGraphComponent.setSize(new Dimension(400,400));
        mxGraphComponent.setBorder(new MatteBorder(10,10,10,10, Color.BLACK));
        mxGraphComponent.addMouseListener( listener );
        mxGraphComponent.setTransferHandler( handler );
        add( mxGraphComponent );
    }

}
