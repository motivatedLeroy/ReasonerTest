package Controller.MenuItemListeners;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemMathListener implements ActionListener {

    private mxGraphComponent mxGraphComponent;
    private mxCell mxCell;

    public MenuItemMathListener(mxGraphComponent mxGraphComponent, mxCell mxCell) {
        this.mxGraphComponent = mxGraphComponent;
        this.mxCell = mxCell;
    }
    public void actionPerformed(ActionEvent e) {
        mxGraph graph = mxGraphComponent.getGraph();
        graph.getModel().beginUpdate();
        try{
            mxCell cell = (mxCell)((mxGraphModel)mxGraphComponent.getGraph().getModel()).getCell(mxCell.getId());
            graph.getModel().setValue(mxCell,((JLabel)((JMenuItem)e.getSource()).getComponent(0)).getText().split(" ")[3]+((JTextField) ((JMenuItem) e.getSource()).getComponent(1)).getText());
            graph.getModel().setValue(cell,((JLabel)((JMenuItem)e.getSource()).getComponent(0)).getText().split(" ")[3]+((JTextField) ((JMenuItem) e.getSource()).getComponent(1)).getText());
        }finally {
        graph.getModel().endUpdate();
    }

}
}
