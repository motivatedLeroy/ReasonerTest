package Controller;

import GUI.DragAndDrop.DraggableJPanel;
import GUI.InstanceReasoningScrollPane;
import GUI.ReasonerPanel;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StartReasoningButtonListener implements ActionListener {
    private InstanceReasoningScrollPane instanceReasoningScrollPane;
    private ReasonerPanel reasonerPanel;

    public StartReasoningButtonListener(InstanceReasoningScrollPane instanceReasoningScrollPane, ReasonerPanel reasonerPanel){
        this.instanceReasoningScrollPane = instanceReasoningScrollPane;
        this.reasonerPanel = reasonerPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int leftsideJPanelComponentCount = instanceReasoningScrollPane.leftsideJPanel.getComponentCount();
        int rightsideJPanelComponentCount = instanceReasoningScrollPane.rightsideJPanel.getComponentCount();
        String rulePart ="[(";

        for(int i = 0; i < leftsideJPanelComponentCount; i++){

            DraggableJPanel panel = (DraggableJPanel)(instanceReasoningScrollPane.leftsideJPanel.getComponent(i));
            if(panel.mxGraphComponent != null){
                mxCell mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("0");
                rulePart += reasonerPanel.rdfTable.getModelToTableMapper_Subject().get(mxCell.getValue())+ " ";
                mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("1");
                rulePart += reasonerPanel.rdfTable.getModelToTableMapper_Predicate().get(mxCell.getValue())+ " ";
                mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("2");
                rulePart += reasonerPanel.rdfTable.getModelToTableMapper_Object().get(mxCell.getValue())+ ") ";
                System.out.println(rulePart);
            }
        }
        rulePart += " -> (";

        for(int i = 0; i < rightsideJPanelComponentCount; i++){
            DraggableJPanel panel = (DraggableJPanel)(instanceReasoningScrollPane.rightsideJPanel.getComponent(i));
            if(panel.mxGraphComponent != null){
                mxCell mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("0");
                rulePart += reasonerPanel.rdfTable.getModelToTableMapper_Subject().get(mxCell.getValue())+ " ";
                mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("1");
                rulePart += reasonerPanel.rdfTable.getModelToTableMapper_Predicate().get(mxCell.getValue())+ " ";
                mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("2");
                rulePart += reasonerPanel.rdfTable.getModelToTableMapper_Object().get(mxCell.getValue())+ ") ";
                System.out.println(rulePart);
            }
        }
        rulePart =" -> ]";


        String rules =  "[rule0: (?a http://www.workingontologist.org/Examples/Chapter3/biography.owl#livedIn ?c) " +
                "(?b http://www.workingontologist.org/Examples/Chapter3/biography.owl#married ?a) ->" +
                " print(?a)]";
                //" (?b http://www.workingontologist.org/Examples/Chapter3/biography.owl#livedIn ?c)]";
        //String rules =  "[rule0: (?a http://www.w3.org/2000/01/rdf-schema#label ?b) -> (?a eg:test ?b)]";
        //List rules = Rule.rulesFromURL("file:demo.rules");

        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));

        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, reasonerPanel.model);

        //inf.write(System.out);

        StmtIterator list = inf.listStatements(null, null, (RDFNode) null);
        PrintWriter out = new PrintWriter(System.out);

        while (list.hasNext()) {
            Statement s = list.nextStatement();
            Resource subject = s.getSubject();
            Property predicate = s.getPredicate();
            RDFNode object = s.getObject();
            System.out.println(subject.toString() + " "+ predicate.toString() + " "+ object.toString());

            for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
                Derivation deriv = (Derivation) id.next();
                deriv.printTrace(out, true);
            }
        }

        instanceReasoningScrollPane.ruleSet = new ArrayList<>();
    }
}
