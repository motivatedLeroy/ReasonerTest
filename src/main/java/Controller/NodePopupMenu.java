package Controller;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.visualdataweb.vowl.SwingElements.Controller.JMenuExistingNodeListener;
import org.visualdataweb.vowl.SwingElements.Controller.JMenuNewNodeListener;
import org.visualdataweb.vowl.protege.VOWLViewComponent;
import prefuse.data.Node;
import prefuse.visual.VisualItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NodePopupMenu extends JPopupMenu {

    public static VisualItem node2;
    private mxGraphComponent mxGraphComponent;
    private mxCell mxCell;

    public NodePopupMenu(mxGraphComponent mxGraphComponent, int x, int y, int graphComponentNumber, mxCell mxCell){
        this.mxGraphComponent = mxGraphComponent;
        this.mxCell = mxCell;
        JMenuItem item;
        for(int i = 0; i < VOWLViewComponent.getTripleURIs().size(); i++){
            this.add(item = new JMenuItem(VOWLViewComponent.getTripleURIs().get(i)[graphComponentNumber]));
            item.setHorizontalTextPosition(JMenuItem.RIGHT);
            item.addActionListener(menuItemListener);
        }
        if( graphComponentNumber == 1){
            this.add(item = new JMenuItem(" "));
            item.setLayout(new BoxLayout(item, BoxLayout.X_AXIS));
            item.add(new JLabel("Set equation: "));
            item.add(new JTextField());
            item.addActionListener(menuItemMathListener);
        }
        this.add(item = new JMenuItem("SSSSSSSSSSSSSSSSS"));
        item.addActionListener(menuItemListener);

        this.show(this.mxGraphComponent,x,y);
    }

    ActionListener menuItemListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mxGraph graph = mxGraphComponent.getGraph();
            mxCell.setValue(((JMenuItem)e.getSource()).getText());
            graph.refresh();
        }
    };

    ActionListener menuItemMathListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mxGraph graph = mxGraphComponent.getGraph();
            mxCell.setValue(((JTextField) ((JMenuItem) e.getSource()).getComponent(1)).getText());
            graph.refresh();
        }
    };
}
