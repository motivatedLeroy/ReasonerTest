package Controller;

import GUI.ReasonerPanel;
import org.apache.jena.rdf.model.*;

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
                        //System.out.println(stmt.toString());
                        updatedModel.add(stmt);
                        findTypes(subject.toString());
                    }

                }
                findSubClasses(resource.toString());
        }
        reasonerPanel.rdfTable.setRDFTableModel(updatedModel);
        updatedModel = ModelFactory.createDefaultModel();
    }

    public void findSubClasses(String uri){
        Resource resource = tempModel.createResource(uri);
        ArrayList<Statement> result = new ArrayList<>();


            StmtIterator subclassIterator = reasonerPanel.model.listStatements(null, subclassOfPredicate, resource);
            while (subclassIterator.hasNext()) {
                Statement stmt = subclassIterator.nextStatement();
                Resource subclassSubject = stmt.getSubject();

                    StmtIterator subclassIterator2 = reasonerPanel.model.listStatements(subclassSubject, null, (RDFNode)null);
                    while (subclassIterator2.hasNext()) {
                        Statement subclassStmt = subclassIterator2.nextStatement();  // get next statement
                        Property subclassStmtPredicate= subclassStmt.getPredicate();   // get the predicate
                        RDFNode subclassStmtObject = subclassStmt.getObject();      // get the object#
                        if(!updatedModel.contains(subclassStmt)) {
                            updatedModel.add(subclassStmt);
                            findTypes(subclassSubject.toString());
                        }                    }
                 findSubClasses(subclassSubject.toString());
            }
    }


    public void findTypes(String uri){
        Resource resource = tempModel.createResource(uri);
        ArrayList<Statement> result = new ArrayList<>();


        StmtIterator typeIterator = reasonerPanel.model.listStatements(null, typePredicate, resource);
        while (typeIterator.hasNext()) {
            Statement stmt = typeIterator.nextStatement();
            Resource typeSubject = stmt.getSubject();


            StmtIterator typeIterator2 = reasonerPanel.model.listStatements(typeSubject, null, (RDFNode)null);
            while (typeIterator2.hasNext()) {
                Statement typeStmt = typeIterator2.nextStatement();  // get next statement
                Property typePredicate= typeStmt.getPredicate();   // get the predicate
                RDFNode typeObject = typeStmt.getObject();
                if(!updatedModel.contains(typeStmt)) {
                    //System.out.println("TYPE: " + typeStmt.toString());
                    updatedModel.add(typeStmt);
                }
            }
        }
    }


}
