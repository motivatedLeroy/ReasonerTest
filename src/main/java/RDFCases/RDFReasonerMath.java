package RDFCases;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.Iterator;

public class RDFReasonerMath {



    // create an empty Model
    Model model = ModelFactory.createDefaultModel();

    // create the resource
    Resource numbers = model.createResource("eg:numbers");
    Property number = model.createProperty("eg:number0");
    Property number1 = model.createProperty("eg:number1");


    Resource emptyNode = model.createResource();

    public void test(){

        /*emptyNode.addProperty(VCARD.Given, given);
        emptyNode.addProperty(VCARD.Family, familyName);
        A.addProperty(VCARD.N, emptyNode);*/
    }




    public void setUpExample(){
        model.setNsPrefix("x","eg:");
        numbers.addProperty(number,"0");
        numbers.addProperty(number1,"1");
  /*      numbers.addProperty(model.createProperty("eg:","number1"),"1");
        numbers.addProperty(model.createProperty("eg:","number2"),"2");
        numbers.addProperty(model.createProperty("eg:","number3"),"3");
        numbers.addProperty(model.createProperty("eg:","number4"),"4");
        numbers.addProperty(model.createProperty("eg:","number5"),"5");
        numbers.addProperty(model.createProperty("eg:","number6"),"6");
        numbers.addProperty(model.createProperty("eg:","number7"),"7");
        numbers.addProperty(model.createProperty("eg:","number8"),"8");
        numbers.addProperty(model.createProperty("eg:","number9"),"9");
        numbers.addProperty(model.createProperty("eg:","number10"),"10");
*/








        /*StmtIterator iterator = model.listStatements();

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

        }*/



        String rules =
                // "[r11: (?r eg:concatFirst ?p), (?r eg:concatSecond ?q) -> " +
                //"     [r1b: (?x ?r ?y) <- (?x ?p ?z) (?z ?q ?y)] ]";

            "     [r1: (?A ?T ?B) (?A ?R ?C) addOne(?B ?C) -> print(?B)]";
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
        InfModel inf = ModelFactory.createInfModel(reasoner, model);
        model.write(System.out);

        System.out.println("A *  =>");
        Iterator list = inf.listStatements(numbers, null, (RDFNode)null);
        while (list.hasNext()) {
            System.out.println(" - " + list.next());
        }





    }

    public static void main(String []args){

        RDFReasonerMath reasoner = new RDFReasonerMath();

        reasoner.setUpExample();
    }



}
