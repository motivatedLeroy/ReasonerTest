package Controller;

import GUI.DragAndDrop.DraggableJPanel;
import GUI.InstanceReasoningScrollPane;
import GUI.RDFTable;
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
import org.apache.jena.base.Sys;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartReasoningButtonListener implements ActionListener {
    private InstanceReasoningScrollPane instanceReasoningScrollPane;
    private ReasonerPanel reasonerPanel;
    private HashMap<String, String> mathOperators = new HashMap<>();

    public StartReasoningButtonListener(InstanceReasoningScrollPane instanceReasoningScrollPane, ReasonerPanel reasonerPanel){
        this.instanceReasoningScrollPane = instanceReasoningScrollPane;
        this.reasonerPanel = reasonerPanel;
        this.mathOperators.put("+", "sum( ");
        this.mathOperators.put("*", "product( ");
        this.mathOperators.put("-", "difference( ");
        this.mathOperators.put("/", "quotient( ");

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String subjectString;
        String [] subjectArray;
        String predicateString;
        String [] predicateArray;
        String objectString;
        String [] objectArray;

        int leftsideJPanelComponentCount = instanceReasoningScrollPane.leftsideJPanel.getComponentCount();
        int rightsideJPanelComponentCount = instanceReasoningScrollPane.rightsideJPanel.getComponentCount();
        int index = reasonerPanel.rdfTabbedPane.getSelectedIndex();
        JScrollPane scrollPane = (JScrollPane)reasonerPanel.rdfTabbedPane.getComponent(index);
        JViewport viewport = (JViewport)scrollPane.getComponent(0);
        RDFTable rdfTable = (RDFTable)viewport.getComponent(0);

        String rulePart ="[rule2: ";

        for(int i = 0; i < leftsideJPanelComponentCount; i++){

            DraggableJPanel panel = (DraggableJPanel)(instanceReasoningScrollPane.leftsideJPanel.getComponent(i));
            if(panel.mxGraphComponent != null){

                mxCell mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("0");
                subjectString = rdfTable.getModelToTableMapper_Subject().get(mxCell.getValue())+ " ";
                subjectArray = subjectString.split("\\^\\^");
                rulePart += " ( " +subjectArray[subjectArray.length-1];

                mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("1");
                Pattern pattern = Pattern.compile("^([-+*\\/])");
                Matcher matcher = pattern.matcher(mxCell.getValue().toString());
                boolean check = matcher.find();

                if(check){
                    if(mathOperators.containsKey(matcher.group(1))){
                        predicateString =mxCell.getValue().toString();
                        String operator = matcher.group(1);
                        matcher.reset();

                        pattern = pattern.compile("(.*)\\(");
                        matcher = pattern.matcher(rulePart);
                        matcher.find();

                        rulePart = matcher.group(1) + mathOperators.get(operator);
                        rulePart += subjectArray[subjectArray.length-1]+ " ";

                        matcher.reset();
                        pattern = pattern.compile("(\\d+(?:\\.\\d+)?)");
                        matcher = pattern.matcher(predicateString);
                        matcher.find();
                        rulePart += matcher.group(1)+ " ";

                        matcher.reset();

                    }else{
                        predicateString = rdfTable.getModelToTableMapper_Predicate().get(mxCell.getValue())+ " ";
                        predicateArray = predicateString.split("\\^\\^");
                        rulePart += predicateArray[predicateArray.length-1];
                    }
                }else{
                    predicateString = rdfTable.getModelToTableMapper_Predicate().get(mxCell.getValue())+ " ";
                    predicateArray = predicateString.split("\\^\\^");
                    rulePart += predicateArray[predicateArray.length-1];
                }



                mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("2");
                objectString = rdfTable.getModelToTableMapper_Object().get(mxCell.getValue())+ " ";
                objectArray = objectString.split("\\^\\^");
                rulePart += objectArray[objectArray.length-1] + " ) ";
            }
        }
        rulePart += " -> ";

        for(int i = 0; i < rightsideJPanelComponentCount; i++){
            DraggableJPanel panel = (DraggableJPanel)(instanceReasoningScrollPane.rightsideJPanel.getComponent(i));
            if(panel.mxGraphComponent != null){
                mxCell mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("0");
                subjectString = rdfTable.getModelToTableMapper_Subject().get(mxCell.getValue())+ " ";
                subjectArray = subjectString.split("\\^\\^");
                rulePart += "( "+ subjectArray[subjectArray.length-1];

                mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("1");
                predicateString = rdfTable.getModelToTableMapper_Predicate().get(mxCell.getValue())+ " ";
                predicateArray = predicateString.split("\\^\\^");
                rulePart += predicateArray[predicateArray.length-1];

                mxCell =  (mxCell) ((mxGraphModel)panel.mxGraphComponent.getGraph().getModel()).getCell("2");
                objectString = rdfTable.getModelToTableMapper_Object().get(mxCell.getValue())+ " ";
                objectArray = objectString.split("\\^\\^");
                rulePart += objectArray[objectArray.length-1]+ " ) ";
            }
        }

        rulePart += "]";

        System.out.println(rulePart);



        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rulePart));
        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, reasonerPanel.model);

        //inf.write(System.out);

        /*StmtIterator list = inf.listStatements(null, null, (RDFNode) null);
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

        */


        /*Iterator it = rdfTable.getModelToTableMapper_Object().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }*/



        instanceReasoningScrollPane.resetRuleField();
    }
}
