package Controller;

import GUI.RDFTable;
import GUI.ReasonerPanel;
import org.apache.jena.rdf.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class UpdateRDFTableListener implements ActionListener {

    private ReasonerPanel reasonerPanel;
    private Model tempModel = ModelFactory.createDefaultModel();
    private final Property subclassOfPredicate = tempModel.createProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
    private final Property typePredicate = tempModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    private Model updatedModel = ModelFactory.createDefaultModel();

    public UpdateRDFTableListener(ReasonerPanel reasonerPanel){
        this.reasonerPanel = reasonerPanel;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int index = reasonerPanel.rdfTabbedPane.getSelectedIndex();
        JScrollPane scrollPane = (JScrollPane)reasonerPanel.rdfTabbedPane.getComponent(index);
        RDFTable rdfTable = (RDFTable)scrollPane.getComponent(0);

        HashSet<String> uris = reasonerPanel.vowlViewComponent.getClickedNodesURIs();
        Iterator<String> iterator = uris.iterator();
        while(iterator.hasNext()){
            String tempURI = iterator.next();
            Resource resource = tempModel.createResource(tempURI);

                StmtIterator stmtIterator = reasonerPanel.model.listStatements(resource, null, (RDFNode)null);
                while (stmtIterator.hasNext()) {
                    Statement stmt = stmtIterator.nextStatement();  // get next statement
                    Resource subject = stmt.getSubject();     // get the subject
                    if(!updatedModel.contains(stmt)){
                        updatedModel.add(stmt);
                        findTypes(subject.toString(), rdfTable);
                    }

                }
                findSubClasses(resource.toString(), rdfTable);
        }

        rdfTable.readRDFTableModel(updatedModel);
        updatedModel = ModelFactory.createDefaultModel();
    }

    public void findSubClasses(String uri, RDFTable rdfTable){
        Resource resource = tempModel.createResource(uri);
        ArrayList<Statement> result = new ArrayList<>();


            StmtIterator subclassIterator = rdfTable.model.listStatements(null, subclassOfPredicate, resource);
            while (subclassIterator.hasNext()) {
                Statement stmt = subclassIterator.nextStatement();
                Resource subclassSubject = stmt.getSubject();

                    StmtIterator subclassIterator2 = rdfTable.model.listStatements(subclassSubject, null, (RDFNode)null);
                    while (subclassIterator2.hasNext()) {
                        Statement subclassStmt = subclassIterator2.nextStatement();  // get next statement
                        if(!updatedModel.contains(subclassStmt)) {
                            updatedModel.add(subclassStmt);
                            findTypes(subclassSubject.toString(), rdfTable);
                        }                    }
                 findSubClasses(subclassSubject.toString(),rdfTable);
            }
    }


    public void findTypes(String uri, RDFTable rdfTable){
        Resource resource = tempModel.createResource(uri);
        ArrayList<Statement> result = new ArrayList<>();


        StmtIterator typeIterator = rdfTable.model.listStatements(null, typePredicate, resource);
        while (typeIterator.hasNext()) {
            Statement stmt = typeIterator.nextStatement();
            Resource typeSubject = stmt.getSubject();


            StmtIterator typeIterator2 = rdfTable.model.listStatements(typeSubject, null, (RDFNode)null);
            while (typeIterator2.hasNext()) {
                Statement typeStmt = typeIterator2.nextStatement();
                if(!updatedModel.contains(typeStmt)) {
                    updatedModel.add(typeStmt);
                }
            }
        }
    }


}
