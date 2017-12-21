package Controller.MenuItemListeners;

import GUI.FreeVariableColorChooser;
import GUI.InstanceReasoningScrollPane;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;
import javax.swing.plaf.ColorChooserUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenuItemFreeVariablesListener implements ActionListener{

    private com.mxgraph.swing.mxGraphComponent mxGraphComponent;
    private mxCell mxCell;
    private boolean newVariable;
    private Color color;

    public MenuItemFreeVariablesListener(mxGraphComponent mxGraphComponent, mxCell mxCell, boolean newVariable, Color color) {
        this.mxGraphComponent = mxGraphComponent;
        this.mxCell = mxCell;
        this.newVariable = newVariable;
        this.color = color;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mxGraph graph = mxGraphComponent.getGraph();
        graph.getModel().beginUpdate();
        try{
            if(newVariable){
                mxStylesheet sheet = graph.getStylesheet();
                Map<String, Object> style = sheet.getStyles().get(mxCell.getStyle());

                Color c = JColorChooser.showDialog(null, "Choose a Color", null);
                style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(c));
                sheet.putCellStyle("newStyle"+ InstanceReasoningScrollPane.styleCounter, style);
                graph.getModel().setStyle(mxCell, "newStyle"+InstanceReasoningScrollPane.styleCounter);
                mxCell.setValue("Free Variable!");
                InstanceReasoningScrollPane.styleCounter++;
                if(!InstanceReasoningScrollPane.freeVariables.contains(c)){
                    InstanceReasoningScrollPane.freeVariables.add(c);
                }
            }else{
                mxStylesheet sheet = graph.getStylesheet();
                Map<String, Object> style = sheet.getStyles().get(mxCell.getStyle());

                style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(color));
                sheet.putCellStyle("newStyle"+ InstanceReasoningScrollPane.styleCounter, style);
                graph.getModel().setStyle(mxCell, "newStyle"+InstanceReasoningScrollPane.styleCounter);
                mxCell.setValue("Free Variable!");
                InstanceReasoningScrollPane.styleCounter++;
                mxCell.setValue("Free Variable!");
            }


        }finally {
            graph.getModel().endUpdate();
            //graph.repaint();

        }

    }
}
