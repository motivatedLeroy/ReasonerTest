package RDFCases;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class RDFReasoner {



    // Build a trivial example data set
    /*Model rdfsExample = ModelFactory.createDefaultModel();
    Property A = rdfsExample.createProperty("eg");
    Property B = rdfsExample.createProperty("concatSecond");
    Property C = rdfsExample.createProperty("C");
    Property D = rdfsExample.createProperty("D");
    Resource emptyNode1 = rdfsExample.createResource("A");
    Resource emptyNode2 = rdfsExample.createResource("B");*/


    // create an empty Model
    Model model = ModelFactory.createDefaultModel();

    // create the resource









    public void setUpExample(){
        //String NS = "urn:x-hp:eg/";
        model.setNsPrefix("eg","urn:x-hp:eg/");
        Property p = model.createProperty("urn:x-hp:eg/p");
        Property number0 = model.createProperty("urn:x-hp:eg/x");
        Property number1 = model.createProperty("urn:x-hp:eg/y");
        Resource A = model.createResource("urn:x-hp:eg/A");
        Resource B = model.createResource("urn:x-hp:eg/B");
        Resource C = model.createResource("urn:x-hp:eg/C");
        Resource D = model.createResource("urn:x-hp:eg/D");



        //B.addProperty(model.createProperty("eg:q"), C);
        A.addProperty(p, B.addProperty(p,C.addProperty(p,D)));
        //A.addProperty(number0, "0");
        //A.addProperty(number1, "1");
        A.addLiteral(number0, 0);
        A.addLiteral(number1, 1);




        StmtIterator iterator = model.listStatements();

        while (iterator.hasNext()){
            Statement stmt      = iterator.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");

        }



        //String rules =  "[rule0: (?a eg:x ?b) (?a eg:y ?c)  addOne(?b, ?c)-> print(?a)]";
                        //"     [rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
        //List rules = Rule.rulesFromURL("file:demo.rules");
        File rules = new File("src/main/resources/demo.rules");

        Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(rules.getAbsolutePath()));

        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, model);
        model.write(System.out);
        PrintWriter out = new PrintWriter(System.out);

        System.out.println("A *  =>");
        //StmtIterator list = inf.listStatements(inf.getResource("urn:x-hp:eg/A"), inf.getProperty("urn:x-hp:eg/p"), inf.getResource("urn:x-hp:eg/D"));
        StmtIterator list = inf.listStatements(inf.getResource("urn:x-hp:eg/A"), null, (RDFNode) null);
        /*while (list.hasNext()) {
            Statement s = list.nextStatement();

            for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
                Derivation deriv = (Derivation) id.next();
                deriv.printTrace(out, true);
            }
        }*/

        //for (StmtIterator i = inf.listStatements(inf.getResource("urn:x-hp:eg/"+"A"), inf.getProperty("urn:x-hp:eg/p"), inf.getResource("urn:x-hp:eg/D")); i.hasNext(); ) {
            for (StmtIterator i = inf.listStatements(inf.getResource("urn:x-hp:eg/"+"A"), null, (RDFNode) null); i.hasNext(); ) {
            Statement s = i.nextStatement();
            System.out.println("Statement is " + s);
            for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
                Derivation deriv = (Derivation) id.next();
                deriv.printTrace(out, true);
            }
        }
        out.flush();
    }





    public static void main(String []args){

        RDFReasoner reasoner = new RDFReasoner();
        reasoner.setUpExample();
    }



}
