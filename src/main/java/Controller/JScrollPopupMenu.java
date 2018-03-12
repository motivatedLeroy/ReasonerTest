package Controller;

import Controller.MenuItemListeners.MenuItemFreeVariablesListener;
import Controller.MenuItemListeners.MenuItemListener;
import Controller.MenuItemListeners.MenuItemMathListener;
import Controller.MenuItemListeners.MenuItemResetListener;
import GUI.InstanceReasoningScrollPane;
import GUI.Main;
import GUI.RDFTable;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.apache.jena.rdf.model.*;

import java.awt.*;
import java.awt.Container;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.*;

public class JScrollPopupMenu extends JPopupMenu {

    private com.mxgraph.swing.mxGraphComponent mxGraphComponent;
    private com.mxgraph.model.mxCell mxCell;
    private InstanceReasoningScrollPane instanceReasoningScrollPane;
    private ArrayList<Color> colors = new ArrayList<>();
    private RDFTable rdfTable;

    private Model tempModel = ModelFactory.createDefaultModel();
    private final Property subclassOfPredicate = tempModel.createProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
    private final Property typePredicate = tempModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");


    protected int maximumVisibleRows = 14;



    public JScrollPopupMenu(RDFTable rdfTable, mxGraphComponent mxGraphComponent, int x, int y, int graphComponentNumber, mxCell mxCell, InstanceReasoningScrollPane instanceReasoningScrollPane) {
        super();
        this.mxGraphComponent = mxGraphComponent;
        this.mxCell = mxCell;
        this.instanceReasoningScrollPane = instanceReasoningScrollPane;
        this.rdfTable = rdfTable;
        JMenuItem item;

        setLayout(new ScrollPopupMenuLayout());

        super.add(getScrollBar());
        addMouseWheelListener(new MouseWheelListener() {
            @Override public void mouseWheelMoved(MouseWheelEvent event) {
                JScrollBar scrollBar = getScrollBar();
                int amount = (event.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
                        ? event.getUnitsToScroll() * scrollBar.getUnitIncrement()
                        : (event.getWheelRotation() < 0 ? -1 : 1) * scrollBar.getBlockIncrement();

                scrollBar.setValue(scrollBar.getValue() + amount);
                event.consume();
            }
        });

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
            addMathOperator("-");
            addMathOperator("*");
            addMathOperator("/");
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

    private JScrollBar popupScrollBar;
    protected JScrollBar getScrollBar() {
        if(popupScrollBar == null) {
            popupScrollBar = new JScrollBar(JScrollBar.VERTICAL);
            popupScrollBar.addAdjustmentListener(new AdjustmentListener() {
                @Override public void adjustmentValueChanged(AdjustmentEvent e) {
                    doLayout();
                    repaint();
                }
            });

            popupScrollBar.setVisible(false);
        }

        return popupScrollBar;
    }

    public int getMaximumVisibleRows() {
        return maximumVisibleRows;
    }

    public void setMaximumVisibleRows(int maximumVisibleRows) {
        this.maximumVisibleRows = maximumVisibleRows;
    }

    public void paintChildren(Graphics g){
        Insets insets = getInsets();
        g.clipRect(insets.left, insets.top, getWidth(), getHeight() - insets.top - insets.bottom);
        super.paintChildren(g);
    }

    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);

        if(maximumVisibleRows < getComponentCount()-1) {
            getScrollBar().setVisible(true);
        }
    }

    public void remove(int index) {
        // can't remove the scrollbar
        ++index;

        super.remove(index);

        if(maximumVisibleRows >= getComponentCount()-1) {
            getScrollBar().setVisible(false);
        }
    }

    public void show(Component invoker, int x, int y){
        JScrollBar scrollBar = getScrollBar();
        if(scrollBar.isVisible()){
            int extent = 0;
            int max = 0;
            int i = 0;
            int unit = -1;
            int width = 0;
            for(Component comp : getComponents()) {
                if(!(comp instanceof JScrollBar)) {
                    Dimension preferredSize = comp.getPreferredSize();
                    width = Math.max(width, preferredSize.width);
                    if(unit < 0){
                        unit = preferredSize.height;
                    }
                    if(i++ < maximumVisibleRows) {
                        extent += preferredSize.height;
                    }
                    max += preferredSize.height;
                }
            }

            Insets insets = getInsets();
            int widthMargin = insets.left + insets.right;
            int heightMargin = insets.top + insets.bottom;
            scrollBar.setUnitIncrement(unit);
            scrollBar.setBlockIncrement(extent);
            scrollBar.setValues(0, heightMargin + extent, 0, heightMargin + max);

            width += scrollBar.getPreferredSize().width + widthMargin;
            int height = heightMargin + extent;

            setPopupSize(new Dimension(width, height));
        }

        super.show(invoker, x, y);
    }


    public void addMathOperator(String operator){
        JMenuItem item;
        this.add(item = new JMenuItem());
        item.setLayout(new BoxLayout(item, BoxLayout.X_AXIS));
        item.setPreferredSize(new Dimension((int)(Main.screenWidth*0.2), (int)(Main.screenHeight*0.02)));
        item.add(new JLabel(" Mathematical Operation: "+operator+"  |  Value: "));
        TextFieldTest field = new TextFieldTest();
        item.add(field);
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



    protected static class ScrollPopupMenuLayout implements LayoutManager{
        @Override public void addLayoutComponent(String name, Component comp) {}
        @Override public void removeLayoutComponent(Component comp) {}

        @Override public Dimension preferredLayoutSize(Container parent) {
            int visibleAmount = Integer.MAX_VALUE;
            Dimension dim = new Dimension();
            for(Component comp :parent.getComponents()){
                if(comp.isVisible()) {
                    if(comp instanceof JScrollBar){
                        JScrollBar scrollBar = (JScrollBar) comp;
                        visibleAmount = scrollBar.getVisibleAmount();
                    }
                    else {
                        Dimension pref = comp.getPreferredSize();
                        dim.width = Math.max(dim.width, pref.width);
                        dim.height += pref.height;
                    }
                }
            }

            Insets insets = parent.getInsets();
            dim.height = Math.min(dim.height + insets.top + insets.bottom, visibleAmount);

            return dim;
        }

        @Override public Dimension minimumLayoutSize(Container parent) {
            int visibleAmount = Integer.MAX_VALUE;
            Dimension dim = new Dimension();
            for(Component comp : parent.getComponents()) {
                if(comp.isVisible()){
                    if(comp instanceof JScrollBar) {
                        JScrollBar scrollBar = (JScrollBar) comp;
                        visibleAmount = scrollBar.getVisibleAmount();
                    }
                    else {
                        Dimension min = comp.getMinimumSize();
                        dim.width = Math.max(dim.width, min.width);
                        dim.height += min.height;
                    }
                }
            }

            Insets insets = parent.getInsets();
            dim.height = Math.min(dim.height + insets.top + insets.bottom, visibleAmount);

            return dim;
        }

        @Override public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();

            int width = parent.getWidth() - insets.left - insets.right;
            int height = parent.getHeight() - insets.top - insets.bottom;

            int x = insets.left;
            int y = insets.top;
            int position = 0;

            for(Component comp : parent.getComponents()) {
                if((comp instanceof JScrollBar) && comp.isVisible()) {
                    JScrollBar scrollBar = (JScrollBar) comp;
                    Dimension dim = scrollBar.getPreferredSize();
                    scrollBar.setBounds(x + width-dim.width, y, dim.width, height);
                    width -= dim.width;
                    position = scrollBar.getValue();
                }
            }

            y -= position;
            for(Component comp : parent.getComponents()) {
                if(!(comp instanceof JScrollBar) && comp.isVisible()) {
                    Dimension pref = comp.getPreferredSize();
                    comp.setBounds(x, y, width, pref.height);
                    y += pref.height;
                }
            }
        }
    }
}