package GUI;

import Controller.DragAndDrop.DraggablePanelDeleteListener;
import Controller.DragAndDrop.KeyDispatcher;
import Controller.DragAndDrop.PanelHandler;
import GUI.DragAndDrop.*;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.util.*;

public class InstanceReasoningScrollPane extends JSplitPane {


    public JPanel leftsideJPanel = new JPanel();
    public JPanel rightsideJPanel = new JPanel();
    public static int styleCounter = 0;

    private JScrollPane leftSideScrollPane = new JScrollPane(leftsideJPanel);
    private JScrollPane rightSideScrollPane = new JScrollPane(rightsideJPanel);

    public static ArrayList<Color> freeVariables;

    public InstanceReasoningScrollPane(){
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.freeVariables = new ArrayList<>();



        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher( new KeyDispatcher(this));

        resetRuleField();

    }

    private boolean painted;


    public void resetRuleField(){
        leftsideJPanel = new JPanel();
        rightsideJPanel = new JPanel();

        TitledBorder leftBorder = BorderFactory.createTitledBorder("Conditions");
        TitledBorder rightBorder = BorderFactory.createTitledBorder("Consequents");
        leftsideJPanel.setBorder(leftBorder);
        ((javax.swing.border.TitledBorder) leftsideJPanel.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 18));
        ((javax.swing.border.TitledBorder) leftsideJPanel.getBorder()).setTitleJustification(TitledBorder.CENTER);
        rightsideJPanel.setBorder(rightBorder);
        ((javax.swing.border.TitledBorder) rightsideJPanel.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 18));
        ((javax.swing.border.TitledBorder) rightsideJPanel.getBorder()).setTitleJustification(TitledBorder.CENTER);

        leftSideScrollPane = new JScrollPane(leftsideJPanel);
        rightSideScrollPane = new JScrollPane(rightsideJPanel);

        this.setLeftComponent(this.leftSideScrollPane);
        this.setRightComponent(this.rightSideScrollPane);

        DraggableJPanel leftSide1 = new DraggableJPanel();
        DraggableJPanel leftSide2 = new DraggableJPanel();
        DraggableJPanel leftSide3 = new DraggableJPanel();
        DraggableJPanel leftSide4 = new DraggableJPanel();

        DraggableJPanel rightSide1 = new DraggableJPanel();
        DraggableJPanel rightSide2 = new DraggableJPanel();
        DraggableJPanel rightSide3 = new DraggableJPanel();
        DraggableJPanel rightSide4 = new DraggableJPanel();


        leftsideJPanel.setLayout(new BoxLayout(leftsideJPanel, BoxLayout.Y_AXIS));
        rightsideJPanel.setLayout(new BoxLayout(rightsideJPanel, BoxLayout.Y_AXIS));

        leftsideJPanel.add(leftSide1);
        leftsideJPanel.add(leftSide2);
        leftsideJPanel.add(leftSide3);
        leftsideJPanel.add(leftSide4);

        rightsideJPanel.add(rightSide1);
        rightsideJPanel.add(rightSide2);
        rightsideJPanel.add(rightSide3);
        rightsideJPanel.add(rightSide4);
    }

}
