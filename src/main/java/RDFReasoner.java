import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.vocabulary.RDFS;

import java.util.Iterator;

public class RDFReasoner {


    String NS = "eg:";

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
    Resource r = model.createResource("eg:r");
    Resource p = model.createResource("eg:p");
    Resource q = model.createResource("eg:q");
    Resource A = model.createResource("eg:A");
    Resource B = model.createResource("eg:B");
    Resource C = model.createResource("eg:C");

    Resource emptyNode = model.createResource();

    public void test(){

        /*emptyNode.addProperty(VCARD.Given, given);
        emptyNode.addProperty(VCARD.Family, familyName);
        A.addProperty(VCARD.N, emptyNode);*/
    }




    public void setUpExample(){
        r.addProperty(model.createProperty("eg:", "concatSecond"), q);
        r.addProperty(model.createProperty("eg:", "concatFirst"), p);
        A.addProperty(model.createProperty("eg:p"), B);
        B.addProperty(model.createProperty("eg:q"), C);




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



        String rules =
                "[r1: (?c eg:concatFirst ?p), (?c eg:concatSecond ?q) -> " +
                        "     [r1b: (?x ?c ?y) <- (?x ?p ?z) (?z ?q ?y)] ]";
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
        InfModel inf = ModelFactory.createInfModel(reasoner, model);
        System.out.println("A *  =>");
        Iterator list = inf.listStatements(A, null, (RDFNode)null);
        while (list.hasNext()) {
            System.out.println(" - " + list.next());
        }




    }

    public static void main(String []args){

        RDFReasoner reasoner = new RDFReasoner();
        reasoner.setUpExample();
    }



}
