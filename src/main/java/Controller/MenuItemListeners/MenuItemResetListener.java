package Controller.MenuItemListeners;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemResetListener implements ActionListener {

    private com.mxgraph.swing.mxGraphComponent mxGraphComponent;
    private mxCell mxCell;

    public MenuItemResetListener(mxGraphComponent mxGraphComponent, mxCell mxCell) {
        this.mxGraphComponent = mxGraphComponent;
        this.mxCell = mxCell;
    }
    public void actionPerformed(ActionEvent e) {
        mxGraph graph = mxGraphComponent.getGraph();
        mxCell.setValue("");
        mxCell cell = (mxCell)((mxGraphModel)mxGraphComponent.getGraph().getModel()).getCell(mxCell.getId());
        cell.setValue("");
        graph.refresh();

    }
}
