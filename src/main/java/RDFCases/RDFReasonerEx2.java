package RDFCases;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.ReasonerVocabulary;


import java.util.ArrayList;
import java.util.HashSet;

public class RDFReasonerEx2 {

    // Register a namespace for use in the demo
    String demoURI = "http://jena.hpl.hp.com/demo#";
    public void setUp(){



      /*  PrintUtil.registerPrefix("demo", demoURI);
        // Create an (RDF) specification of a hybrid reasoner which
// loads its data from an external file.
         */Model m = ModelFactory.createDefaultModel();
        Resource configuration =  m.createResource();
        configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
        configuration.addProperty(ReasonerVocabulary.PROPruleSet,  "demo.rules");


       // Create an instance of such a reasoner
        Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(null);


        // Load vowlViewComponent data
        //Model model = FileManager.get().loadModel("musicRDF.rdf");
        Model model = FileManager.get().loadModel("received/music.owl");

        InfModel infmodel = ModelFactory.createInfModel(reasoner, model);


        StmtIterator iterator = model.listStatements();
        ArrayList<String[]> statements = new ArrayList<>();
        HashSet<String> tempSub0 = new HashSet<>();
        HashSet<String> tempPred = new HashSet<>();

        /*while (iterator.hasNext()){
            Statement stmt      = iterator.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            String[] statement = new String[]{subject.toString(), predicate.toString()};

            String[] sub = statement[0].split("/");
            String tempSub1 = "";
            for(int i = 0; i < sub.length; i++){
                if(i < sub.length-1){
                    tempSub1+= sub[i];
                }
            }

            statement[0] = tempSub1;
            String [] pred = statement[1].split("/");

            if(tempSub0.contains(tempSub1) && tempPred.contains(pred[pred.length-1])){
                continue;
            }
            if(tempSub0.contains(tempSub1)){
                System.out.println("\t --> "+ pred[pred.length-1]);
            }else {
                System.out.println(tempSub1+" --> "+pred[pred.length-1]);
            }
            tempSub0.add(tempSub1);
            tempPred.add(pred[pred.length-1]);
            //System.out.println(sub.length);


           System.out.print(subject.toString());
            System.out.print("\t\t\t " + predicate.toString() + "\t\t\t ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");

        }*/


        while (iterator.hasNext()){
            Statement stmt      = iterator.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            //if(subject.toString().contains("gps_point")){
                System.out.print(subject.toString());
                System.out.print(" " + predicate.toString() + " ");
                if (object instanceof Resource) {
                    System.out.print(object.toString());
                } else {
                    // object is a literal
                    System.out.print(" \"" + object.toString() + "\"");
                }

                System.out.println(" .");
            //}


        }






        System.out.println("REASONINGS about A: ");
        // Query for all things related to "a" by "p"
        Property p = model.getProperty(demoURI, "p");
        Resource a = model.getResource(demoURI + "a");
        StmtIterator i = infmodel.listStatements(a, p, (RDFNode)null);
        while (i.hasNext()) {
            System.out.println(" - " + PrintUtil.print(i.nextStatement()));
        }

        System.out.println("REASONINGS about B: ");
        Resource b = model.getResource(demoURI + "b");
        StmtIterator i1 = infmodel.listStatements(b, p, (RDFNode)null);
        while (i1.hasNext()) {
            System.out.println(" - " + PrintUtil.print(i1.nextStatement()));
        }

        System.out.println("REASONINGS about C: ");
        Resource c = model.getResource(demoURI + "c");
        StmtIterator i2 = infmodel.listStatements(c, p, (RDFNode)null);
        while (i2.hasNext()) {
            System.out.println(" - " + PrintUtil.print(i2.nextStatement()));
        }

        System.out.println("REASONINGS about D: ");
        Resource d = model.getResource(demoURI + "d");
        StmtIterator i3 = infmodel.listStatements(d, p, (RDFNode)null);
        while (i3.hasNext()) {
            System.out.println(" - " + PrintUtil.print(i3.nextStatement()));
        }
    }


    private static String NS = "urn:x-hp:eg/";
    //public static void main(String args[]) {
        /*Model data = ModelFactory.createDefaultModel();

        // create the resource
        data.setNsPrefix("eg","urn:x-hp:eg/");

        Property p = data.createProperty("urn:x-hp:eg/p");
        Resource A = data.createResource("urn:x-hp:eg/A");
        Resource B = data.createResource("urn:x-hp:eg/B");
        Resource C = data.createResource("urn:x-hp:eg/C");
        Resource D = data.createResource("urn:x-hp:eg/D");
        A.addProperty(p, B.addProperty(p,C.addProperty(p,D)));
        //FileManager.get().loadModel("data03.ttl");

        data.write(System.out);

        String rules = "[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, data);

        PrintWriter out = new PrintWriter(System.out);
        for (StmtIterator i = inf.listStatements(inf.getResource(NS+"A"), 	inf.getProperty(NS+"p"), inf.getResource(NS+"D")); i.hasNext(); ) {
            Statement s = i.nextStatement();
            System.out.println("Statement is " + s);
            for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
                Derivation deriv = (Derivation) id.next();
                deriv.printTrace(out, true);
            }
        }
        out.flush(); 	}*/


    public static void main(String []args){

        RDFCases.RDFReasonerEx2 reasoner = new RDFCases.RDFReasonerEx2();
        reasoner.setUp();
    }


}
