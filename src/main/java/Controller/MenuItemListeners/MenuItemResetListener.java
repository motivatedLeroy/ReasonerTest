package Controller.MenuItemListeners;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemResetListener implements ActionListener {

    private mxGraphComponent mxGraphComponent;
    private mxCell mxCell;

    public MenuItemResetListener(mxGraphComponent mxGraphComponent, mxCell mxCell) {
        this.mxGraphComponent = mxGraphComponent;
        this.mxCell = mxCell;
    }
    public void actionPerformed(ActionEvent e) {
        mxGraph graph = mxGraphComponent.getGraph();
        graph.getModel().beginUpdate();
        try{
            mxCell cell = (mxCell)((mxGraphModel)mxGraphComponent.getGraph().getModel()).getCell(mxCell.getId());
            graph.getModel().setValue(mxCell, "");
            System.out.println(graph.getModel().getStyle(mxCell));
            System.out.println(graph.getModel().getStyle(cell));
        }finally {
            graph.getModel().endUpdate();
        }

    }
}
