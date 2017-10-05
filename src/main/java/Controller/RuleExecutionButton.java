package Controller;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Iterator;

public class RuleExecutionButton implements ActionListener {

    private JTextArea ruleTextArea;
    private static String NS = "urn:x-hp:eg/";

    public RuleExecutionButton(JTextArea ruleTextArea){
        this.ruleTextArea = ruleTextArea;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(ruleTextArea.getText());


        Model data = ModelFactory.createDefaultModel();

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

        String rules = ruleTextArea.getText();//"[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
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
        out.flush();





    }
}
