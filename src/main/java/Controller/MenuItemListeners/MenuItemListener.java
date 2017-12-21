package Controller.MenuItemListeners;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemListener implements ActionListener {

    private mxGraphComponent mxGraphComponent;
    private mxCell mxCell;

    public MenuItemListener(mxGraphComponent mxGraphComponent, mxCell mxCell) {
        this.mxGraphComponent = mxGraphComponent;
        this.mxCell = mxCell;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mxGraph graph = mxGraphComponent.getGraph();
        mxCell.setValue(((JMenuItem)e.getSource()).getText());
        mxCell cell = (mxCell)((mxGraphModel)mxGraphComponent.getGraph().getModel()).getCell(mxCell.getId());
        cell.setValue(((JMenuItem)e.getSource()).getText());
        graph.refresh();
    }
}
