package GUI.DragAndDrop;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class mxDragAndDropComponent extends mxGraphComponent {

    public boolean rightSide;

    public mxDragAndDropComponent(mxGraph mxGraph, boolean rightSide){
        super(mxGraph);
        this.rightSide = rightSide;
    }
}
