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
        graph.getModel().beginUpdate();
        try{
            mxCell cell = (mxCell)((mxGraphModel)mxGraphComponent.getGraph().getModel()).getCell(mxCell.getId());
            graph.getModel().setValue(mxCell, ((JMenuItem)e.getSource()).getText());
            graph.getModel().setValue(cell, ((JMenuItem)e.getSource()).getText());
        }finally {
            graph.getModel().endUpdate();
        }

    }
}
