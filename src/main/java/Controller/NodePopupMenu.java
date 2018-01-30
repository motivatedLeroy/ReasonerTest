package Controller;

import Controller.MenuItemListeners.MenuItemFreeVariablesListener;
import Controller.MenuItemListeners.MenuItemListener;
import Controller.MenuItemListeners.MenuItemMathListener;
import Controller.MenuItemListeners.MenuItemResetListener;
import GUI.InstanceReasoningScrollPane;
import GUI.Main;
import GUI.RDFTable;
import GUI.ReasonerPanel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.apache.jena.atlas.iterator.Iter;
import org.apache.jena.rdf.model.*;


import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;


public class NodePopupMenu extends JPopupMenu {

    private mxGraphComponent mxGraphComponent;
    private mxCell mxCell;
    private InstanceReasoningScrollPane instanceReasoningScrollPane;
    private ArrayList<Color> colors = new ArrayList<>();
    private RDFTable rdfTable;

    private Model tempModel = ModelFactory.createDefaultModel();
    private final Property subclassOfPredicate = tempModel.createProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
    private final Property typePredicate = tempModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

    public NodePopupMenu(RDFTable rdfTable, mxGraphComponent mxGraphComponent, int x, int y, int graphComponentNumber, mxCell mxCell, InstanceReasoningScrollPane instanceReasoningScrollPane){
        this.mxGraphComponent = mxGraphComponent;
        this.mxCell = mxCell;
        this.instanceReasoningScrollPane = instanceReasoningScrollPane;
        this.rdfTable = rdfTable;
        JMenuItem item;


        if( graphComponentNumber == 0){

            TreeSet<String> subjects;
            String baseSubject = (String)mxCell.getValue();
            if(baseSubject.equals("")){
                subjects = findSubjects();
            }else{
                subjects = findSubClasses(baseSubject);
            }
            Iterator<String> i = subjects.iterator();
            while(i.hasNext()){
                String next = i.next();
                this.add(item = new JMenuItem(next));
                item.addActionListener(new MenuItemListener(mxGraphComponent, mxCell));
            }
        }
        if( graphComponentNumber == 1){
            TreeSet<String> predicates = findPredicates();
            Iterator<String> i = predicates.iterator();
            while(i.hasNext()){
                this.add(item = new JMenuItem(i.next()));
                item.addActionListener(new MenuItemListener(mxGraphComponent, mxCell));
            }
            addMathOperator("+");
            addMathOperator(" -");
            addMathOperator("*");
            addMathOperator(" /");
            addMathOperator("<");
            addMathOperator(">");
            addMathOperator("=");
        }
        if(graphComponentNumber ==2){
            TreeSet<String> objects = findObjects();
            Iterator<String> i = objects.iterator();
            while(i.hasNext()){
                String next = i.next();
                this.add(item = new JMenuItem(next));
                item.addActionListener(new MenuItemListener(mxGraphComponent, mxCell));
            }
        }

        addCustomValue();
        this.add(item = new JMenuItem("Reset"));
        item.addActionListener(new MenuItemResetListener(mxGraphComponent, mxCell));
        addFreeVariables();

        this.show(this.mxGraphComponent,x,y);
    }

    public void addMathOperator(String operator){
        JMenuItem item;
        this.add(item = new JMenuItem());
        item.setLayout(new BoxLayout(item, BoxLayout.X_AXIS));
        item.setPreferredSize(new Dimension((int)(Main.screenWidth*0.2), (int)(Main.screenHeight*0.02)));
        item.add(new JLabel(" Mathematical Operation: "+operator+"  |  Value: "));
        item.add(new JTextField());
        item.addActionListener(new MenuItemMathListener(mxGraphComponent, mxCell));
    }

    public void addCustomValue(){
        JMenuItem item;
        this.add(item = new JMenuItem());
        item.setPreferredSize(new Dimension((int)(Main.screenWidth*0.2), (int)(Main.screenHeight*0.02)));
        item.add(new JLabel(" Free Variable"));
        item.addActionListener(new MenuItemFreeVariablesListener(mxGraphComponent, mxCell, true, null));
    }

    public void addFreeVariables(){
        for(int i = 0; i < instanceReasoningScrollPane.freeVariables.size(); i++){
            JMenuItem item;
            this.add(item = new JMenuItem());
            item.setBackground(instanceReasoningScrollPane.freeVariables.get(i));
            item.setPreferredSize(new Dimension((int)(Main.screenWidth*0.2), (int)(Main.screenHeight*0.02)));
            item.addActionListener(new MenuItemFreeVariablesListener(mxGraphComponent, mxCell, false, instanceReasoningScrollPane.freeVariables.get(i)));
        }
    }



    public TreeSet<String> findSubClasses(String baseSubject){
        Resource resource = tempModel.createResource(rdfTable.getModelToTableMapper_Subject().get(baseSubject));
        TreeSet<String> cellValues = findTypes(resource.toString());
        StmtIterator subclassIterator = rdfTable.model.listStatements(null, subclassOfPredicate, resource);
        while (subclassIterator.hasNext()) {
            Statement stmt = subclassIterator.nextStatement();
            Resource subclassSubject = stmt.getSubject();
            String[] subclassSubjectArray = subclassSubject.toString().split("/");
            String subclassSubjectShort = subclassSubjectArray[subclassSubjectArray.length-1];
                if(!cellValues.contains(subclassSubjectShort)) {
                    cellValues.add(subclassSubjectShort);
                    TreeSet<String> types = findTypes(subclassSubject.toString());
                    Iterator<String> i = types.iterator();
                    while(i.hasNext()){
                        String type = i.next();
                        if(!cellValues.contains(type)){
                            cellValues.add(type);
                        }
                    }
                }
            findSubClasses(subclassSubject.toString());
        }
        return cellValues;
    }


    public TreeSet<String> findTypes(String uri){
        TreeSet<String> cellValues = new TreeSet<>();
        Resource resource = tempModel.createResource(uri);
        StmtIterator typeIterator = rdfTable.model.listStatements(null, typePredicate, resource);
        while (typeIterator.hasNext()) {
            Statement stmt = typeIterator.nextStatement();
            Resource typeSubject = stmt.getSubject();
            String[] typeSubjectArray = typeSubject.toString().split("/");
            String typeSubjectShort = typeSubjectArray[typeSubjectArray.length-1];
            if(!cellValues.contains(typeSubjectShort)) {
                cellValues.add(typeSubjectShort);
            }
        }
        return cellValues;
    }

    public TreeSet<String> findSubjects(){
        TreeSet<String> cellValues = new TreeSet<>();
        StmtIterator predicateIterator = rdfTable.model.listStatements(null, null, (RDFNode)null);
        while (predicateIterator.hasNext()) {
            Statement stmt = predicateIterator.nextStatement();
            Resource subject = stmt.getSubject();
            String[] subjectArray = subject.toString().split("/");
            String predicateShort = subjectArray[subjectArray.length-1];
            if (!cellValues.contains(predicateShort)){
                cellValues.add(predicateShort);
            }
        }
        return cellValues;
    }


    public TreeSet<String> findPredicates(){
        TreeSet<String> cellValues = new TreeSet<>();
        StmtIterator predicateIterator = rdfTable.model.listStatements(null, null, (RDFNode)null);
        while (predicateIterator.hasNext()) {
            Statement stmt = predicateIterator.nextStatement();
            Property predicate = stmt.getPredicate();
            String[] predicateArray = predicate.toString().split("/");
            String predicateShort = predicateArray[predicateArray.length-1];
            if (!cellValues.contains(predicateShort)){
                cellValues.add(predicateShort);
            }
        }
        return cellValues;
    }

    public TreeSet<String> findObjects(){
        TreeSet<String> cellValues = new TreeSet<>();
        StmtIterator predicateIterator = rdfTable.model.listStatements(null, null, (RDFNode)null);
        while (predicateIterator.hasNext()) {
            Statement stmt = predicateIterator.nextStatement();
            RDFNode object = stmt.getObject();
            String[] objectArray = object.toString().split("/");
            String objectShort = objectArray[objectArray.length-1];
            if (!cellValues.contains(objectShort)){
                cellValues.add(objectShort);
            }
        }
        return cellValues;
    }

}
