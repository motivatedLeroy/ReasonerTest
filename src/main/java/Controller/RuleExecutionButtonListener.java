package Controller;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RuleExecutionButtonListener implements ActionListener {

    private Model data;
    private JTextArea ruleTextArea;
    private static String NS = "urn:x-hp:eg/";

    public RuleExecutionButtonListener(JTextArea ruleTextArea){
        this.ruleTextArea = ruleTextArea;
        this.data = FileManager.get().loadModel("musicRDF.rdf");
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(ruleTextArea.getText());
        Map<String, String> prefixes = new HashMap<String, String>();
        //prefixes.put("founder","http://www.recshop.fake/founder#");
        //prefixes.put("publisher","http://www.recshop.fake/publisher#");
        data.setNsPrefixes(prefixes);
        // create the resource
        //data.setNsPrefix("eg","urn:x-hp:eg/");

        /*Property p = data.createProperty("urn:x-hp:eg/p");
        Resource A = data.createResource("urn:x-hp:eg/A");
        Resource B = data.createResource("urn:x-hp:eg/B");
        Resource C = data.createResource("urn:x-hp:eg/C");
        Resource D = data.createResource("urn:x-hp:eg/D");
        A.addProperty(p, B.addProperty(p,C.addProperty(p,D)));*/
        //FileManager.get().loadModel("data03.ttl");

        //data.write(System.out);

        StmtIterator iterator = data.listStatements();

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


        String rules = //ruleTextArea.getText();
                "@prefix founder: <http://www.recshop.fake/founder> [rule1: (?a founder:founded ?b) (?b publisher:published ?c) -> (?a founder:Gründungsvater ?c)]";
                //"[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + "demo.rules")));
            List rules1 = Rule.parseRules(Rule.rulesParserFromReader(br));
            Reasoner reasoner = new GenericRuleReasoner(rules1);
            reasoner.setDerivationLogging(true);
            InfModel inf = ModelFactory.createInfModel(reasoner, data);

            PrintWriter out = new PrintWriter(System.out);
            for (StmtIterator i = inf.listStatements(null,null,(RDFNode)null);//inf.getResource(NS+"A"), 	inf.getProperty(NS+"p"), inf.getResource(NS+"D"));
                 i.hasNext(); ) {
                Statement s = i.nextStatement();
                System.out.println("Statement is " + s);
                for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
                    Derivation deriv = (Derivation) id.next();
                    deriv.printTrace(out, true);
                }
            }
            out.flush();


    }
    public Model getData() {
        return data;
    }
}
