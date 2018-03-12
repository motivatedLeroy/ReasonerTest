package GUI;

import Controller.TripleToReasoningSessionListener;
import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class RDFTable extends JTable {

    private ReasonerPanel reasonerPanel;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    public static HashMap<String, String> modelToTableMapper_Subject = new HashMap<>();
    public static HashMap<String, String> modelToTableMapper_Predicate = new HashMap<>();;
    public static HashMap<String, String> modelToTableMapper_Object = new HashMap<>();;
    public Model model;
    public boolean liveData;


    public RDFTable(ReasonerPanel reasonerPanel, boolean liveData){
        this.reasonerPanel = reasonerPanel;
        this.addMouseListener(new TripleToReasoningSessionListener(this.reasonerPanel.rightInstanceReasoningSubPanel,this, reasonerPanel));
        this.model = ModelFactory.createDefaultModel();
        this.liveData = liveData;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
        Component c = super.prepareRenderer(renderer, row, column);

        if (!isRowSelected(row))
        {
            c.setBackground(getBackground());
            int modelRow = convertRowIndexToModel(row);
            String value0 = (String)getModel().getValueAt(modelRow, 0);
            String value1 = (String)getModel().getValueAt(modelRow, 1);
            String value2 = (String)getModel().getValueAt(modelRow, 2);
            if (value0.equals("")&& value1.equals("") && value2.equals("")){
                c.setBackground(UIManager.getColor ( "Panel.background" ));
            }
        }
        return c;
    }

    public void readRDFTableModel(Model model){
        this.model = model;
        initializeDefaultTableModel();
        StmtIterator iterator = this.model.listStatements();

        HashSet<String> tempSub0 = new HashSet<>();
        HashSet<String> tempPred = new HashSet<>();
        HashSet<String> tempPred1 = new HashSet<>();

        List<String[]> sortingList = new ArrayList();
        int sortingCounter = 0;

        while (iterator.hasNext()) {
            Statement stmt = iterator.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object#

            String[] subjectArray = subject.toString().split("/");
            String[] objectArray = object.toString().split("/");
            String[] predicateArray = predicate.toString().split("#");

            String subjectString = subjectArray[subjectArray.length-1];
            String predicateString = predicateArray[predicateArray.length-1];
            String objectString = objectArray[objectArray.length-1];

            if(!modelToTableMapper_Subject.containsKey(subjectString)){
                modelToTableMapper_Subject.put(subjectString, subject.toString());
            }
            if(!modelToTableMapper_Predicate.containsKey(predicateString)){
                modelToTableMapper_Predicate.put(predicateString, predicate.toString());
            }
            if(!modelToTableMapper_Object.containsKey(objectString)){
                modelToTableMapper_Object.put(objectString, object.toString());
            }


            if (tempSub0.contains(subject.toString())) {
                if(tempPred.contains(predicate.toString())) {

                    this.defaultTableModel.addRow(new String[]{"", predicateString, objectString});
                    sortingList.add(new String[] {predicateString,objectString });
                }else{

                    this.defaultTableModel.addRow(new String[]{"", predicateString, objectString});

                    sortingList.add(new String[] {predicateString,objectString });
                }


            }else{
                tempPred = new HashSet<>();

                this.defaultTableModel.addRow(new String[]{"","",""});



                Collections.sort(sortingList, new Comparator<String[]>() {
                    @Override
                    public int compare(final String[] entry1, final String[] entry2) {
                        final String predicate1 = entry1[0];
                        final String predicate2 = entry2[0];
                        return predicate1.compareTo(predicate2);
                    }
                });


                for(int i = 0; i < sortingList.size();i++){
                    this.defaultTableModel.setValueAt(sortingList.get(i)[0],sortingCounter+i,1);
                    this.defaultTableModel.setValueAt(sortingList.get(i)[1],sortingCounter+i,2);
                }

                sortingList = new ArrayList<>();
                sortingCounter = this.defaultTableModel.getRowCount();


                this.defaultTableModel.addRow(new String[]{subjectString,predicateString, objectString});

                sortingList.add(new String[] {predicateString,objectString });

            }
            tempSub0.add(subject.toString());
            tempPred.add(predicate.toString());
            tempPred1.add(predicate.toString());
        }

    }


    public void readCSVTableModel(Model model){
        this.model = model;
        initializeDefaultTableModel();
        StmtIterator iterator = this.model.listStatements();

        HashSet<String> tempSub0 = new HashSet<>();
        HashSet<String> tempPred = new HashSet<>();
        HashSet<String> tempPred1 = new HashSet<>();


        while (iterator.hasNext()) {
            Statement stmt = iterator.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object#

            //String[] subjectArray = subject.toString().split("/");
            String[] objectArray = object.toString().split("\\^\\^");
            String[] predicateArray = predicate.toString().split("\\.");

            String subjectString = subject.toString();
            String predicateString = "";
            if(predicateArray.length>0){
                predicateString = predicateArray[predicateArray.length-1];
            }else{
                predicateString = predicate.toString();
            }

            String objectString = objectArray[0];

            if(!modelToTableMapper_Subject.containsKey(subjectString)){
                modelToTableMapper_Subject.put(subjectString, subject.toString());
            }
            if(!modelToTableMapper_Predicate.containsKey(predicateString)){
                modelToTableMapper_Predicate.put(predicateString, predicate.toString());
            }
            if(!modelToTableMapper_Object.containsKey(objectString)){
                modelToTableMapper_Object.put(objectString, object.toString());
            }


            if (tempSub0.contains(subject.toString())) {
                if(tempPred.contains(predicate.toString())) {

                    this.defaultTableModel.addRow(new String[]{"", predicateString, objectString});
                }else{

                    this.defaultTableModel.addRow(new String[]{"", predicateString, objectString});

                }


            }else{
                tempPred = new HashSet<>();

                this.defaultTableModel.addRow(new String[]{"","",""});

                this.defaultTableModel.addRow(new String[]{subjectString,predicateString, objectString});

            }
            tempSub0.add(subject.toString());
            tempPred.add(predicate.toString());
            tempPred1.add(predicate.toString());
        }

    }



    private void initializeDefaultTableModel(){
        this.defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Subject");
        defaultTableModel.addColumn("Predicate");
        defaultTableModel.addColumn("Object");
        this.setModel(defaultTableModel);
    }

    public HashMap<String, String> getModelToTableMapper_Subject() {
        return modelToTableMapper_Subject;
    }

    public HashMap<String, String> getModelToTableMapper_Predicate() {
        return modelToTableMapper_Predicate;
    }

    public HashMap<String, String> getModelToTableMapper_Object() {
        return modelToTableMapper_Object;
    }




}
