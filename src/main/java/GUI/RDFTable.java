package GUI;

import Controller.TripleToReasoningSessionListener;
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


    public RDFTable(ReasonerPanel reasonerPanel){
        this.reasonerPanel = reasonerPanel;
        this.addMouseListener(new TripleToReasoningSessionListener(this.reasonerPanel.rightInstanceReasoningSubPanel4,this));
    }

    @Override
    public Component prepareRenderer(
            TableCellRenderer renderer, int row, int column)
    {
        Component c = super.prepareRenderer(renderer, row, column);

        //  add custom rendering here

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

    public void setRDFTableModel(Model model){
        initializeDefaultTableModel();
        StmtIterator iterator = model.listStatements();

        ComboBoxModel subjectComboBoxModel = reasonerPanel.subjectComboBox.getModel();
        Vector subjectComboBoxItems = new Vector();
        ComboBoxModel predicateComboBoxModel = reasonerPanel.predicateComboBox.getModel();
        Vector predicateComboBoxItems = new Vector();
        ComboBoxModel objectComboBoxModel = reasonerPanel.objectComboBox.getModel();
        Vector objectComboBoxItems = new Vector();
        HashSet<String> tempSub0 = new HashSet<>();
        HashSet<String> tempPred = new HashSet<>();
        HashSet<String> tempPred1 = new HashSet<>();

        List<String[]> sortingList = new ArrayList();
        int sortingCounter = 0;
        /*for(int i = 0; i< subjectComboBoxModel.getSize(); i++){
            subjectComboBoxItems.add(subjectComboBoxModel.getElementAt(i));
        }
        for(int i = 0; i< predicateComboBoxModel.getSize(); i++){
            predicateComboBoxItems.add(predicateComboBoxModel.getElementAt(i));
        }
        for(int i = 0; i< objectComboBoxModel.getSize(); i++){
            objectComboBoxItems.add(objectComboBoxModel.getElementAt(i));
        }*/
        while (iterator.hasNext()) {
            Statement stmt = iterator.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object#
            String[] subjectArray = subject.toString().split("/");
            String[] objectArray = object.toString().split("/");
            String[] predicateArray = predicate.toString().split("#");

            if (tempSub0.contains(subject.toString())) {
                if(tempPred.contains(predicate.toString())) {

                    this.defaultTableModel.addRow(new String[]{"", predicateArray[predicateArray.length-1], objectArray[objectArray.length-1]});
                    //System.out.println(predicateArray[predicateArray.length-1]+" " +objectArray[objectArray.length-1]);
                    sortingList.add(new String[] {predicateArray[predicateArray.length-1],objectArray[objectArray.length-1] });
                }else{
                    if(tempPred1.contains(predicate.toString())){
                        objectComboBoxItems.add(objectArray[objectArray.length-1]);

                    }else{
                        objectComboBoxItems.add(objectArray[objectArray.length-1]);
                        predicateComboBoxItems.add(predicateArray[predicateArray.length-1]);
                    }

                    this.defaultTableModel.addRow(new String[]{"", predicateArray[predicateArray.length-1], objectArray[objectArray.length-1]});

                    //System.out.println(predicateArray[predicateArray.length-1]+" " +objectArray[objectArray.length-1]);
                    sortingList.add(new String[] {predicateArray[predicateArray.length-1],objectArray[objectArray.length-1] });
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


                this.defaultTableModel.addRow(new String[]{subjectArray[subjectArray.length-1],predicateArray[predicateArray.length-1], objectArray[objectArray.length-1]});

                sortingList.add(new String[] {predicateArray[predicateArray.length-1],objectArray[objectArray.length-1] });



                subjectComboBoxItems.add(subjectArray[subjectArray.length-1]);
                if(tempPred1.contains(predicate.toString())){
                    objectComboBoxItems.add(objectArray[objectArray.length-1]);
                }else {
                    predicateComboBoxItems.add(predicateArray[predicateArray.length-1]);
                    objectComboBoxItems.add(objectArray[objectArray.length-1]);
                }
            }
            tempSub0.add(subject.toString());
            tempPred.add(predicate.toString());
            tempPred1.add(predicate.toString());
        }
        subjectComboBoxModel = new DefaultComboBoxModel(subjectComboBoxItems);
        reasonerPanel.subjectComboBox.setModel(subjectComboBoxModel);
        predicateComboBoxModel = new DefaultComboBoxModel(predicateComboBoxItems);
        reasonerPanel.predicateComboBox.setModel(predicateComboBoxModel);
        objectComboBoxModel = new DefaultComboBoxModel(objectComboBoxItems);
        reasonerPanel.objectComboBox.setModel(objectComboBoxModel);
    }

    private void initializeDefaultTableModel(){
        this.defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Subject");
        defaultTableModel.addColumn("Predicate");
        defaultTableModel.addColumn("Object");
        this.setModel(defaultTableModel);
    }




}
