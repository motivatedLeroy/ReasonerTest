package GUI;

import GUI.DragAndDrop.*;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.util.Hashtable;

public class InstanceReasoningScrollPane extends JSplitPane {


    public JPanel leftsideJPanel = new JPanel();
    public JPanel rightsideJPanel = new JPanel();

    private JScrollPane leftSideScrollPane = new JScrollPane(leftsideJPanel);
    private JScrollPane rightSideScrollPane = new JScrollPane(rightsideJPanel);


    public InstanceReasoningScrollPane(){
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.setLeftComponent(this.leftSideScrollPane);
        this.setRightComponent(this.rightSideScrollPane);



        mxGraph graph = new mxGraph();

        Object parent = graph.getDefaultParent();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(new Color(10,194, 148)));
        style.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
        style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(new Color(255, 0, 0)));
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
        mxStylesheet stylesheet = graph.getStylesheet();
        stylesheet.putCellStyle("Ellipse_Style", style);




        DraggableJPanel leftSide1 = new DraggableJPanel();
        DraggableJPanel leftSide2 = new DraggableJPanel();
        DraggableJPanel leftSide3 = new DraggableJPanel();
        DraggableJPanel leftSide4 = new DraggableJPanel();
        DraggableJPanel leftSide5 = new DraggableJPanel();
        DraggableJPanel leftSide6 = new DraggableJPanel();

        DraggableJPanel rightSide1 = new DraggableJPanel();
        DraggableJPanel rightSide2 = new DraggableJPanel();
        DraggableJPanel rightSide3 = new DraggableJPanel();
        DraggableJPanel rightSide4 = new DraggableJPanel();
        DraggableJPanel rightSide5 = new DraggableJPanel();
        DraggableJPanel rightSide6 = new DraggableJPanel();



        leftsideJPanel.setLayout(new BoxLayout(leftsideJPanel, BoxLayout.Y_AXIS));
        rightsideJPanel.setLayout(new BoxLayout(rightsideJPanel, BoxLayout.Y_AXIS));

        leftsideJPanel.add(leftSide1);
        leftsideJPanel.add(leftSide2);
        leftsideJPanel.add(leftSide3);
        leftsideJPanel.add(leftSide4);
        leftsideJPanel.add(leftSide5);
        leftsideJPanel.add(leftSide6);

        rightsideJPanel.add(rightSide1);
        rightsideJPanel.add(rightSide2);
        rightsideJPanel.add(rightSide3);
        rightsideJPanel.add(rightSide4);
        rightsideJPanel.add(rightSide5);
        rightsideJPanel.add(rightSide6);

        this.setDividerLocation(0.5);

    }
}
