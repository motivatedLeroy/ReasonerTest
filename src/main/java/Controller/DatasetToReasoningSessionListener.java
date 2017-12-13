package Controller;

import GUI.BrokerPanel;
import GUI.ReasonerPanel;
import domain.RdfFile;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.jena.propertytable.lang.CSV2RDF;
import org.apache.jena.rdf.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;

public class DatasetToReasoningSessionListener implements ActionListener {
    private BrokerPanel brokerPanel;
    private ReasonerPanel reasonerPanel;
    private HashMap<String, String> base = new HashMap<>();


    public DatasetToReasoningSessionListener(BrokerPanel brokerPanel, ReasonerPanel reasonerPanel){
        CSV2RDF.init();

        base.put("rdf", "RDF/XML");
        base.put("csv,", "text/csv");
        base.put("ttl", "TTL");
        this.brokerPanel = brokerPanel;
        this.reasonerPanel = reasonerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(brokerPanel.getSelectedRow() != null){
            RdfFile rdfFile = brokerPanel.getSelectedRow();
            try {

                CloseableHttpClient client = HttpClientBuilder.create().build();
                HttpPost httpPost = new HttpPost("http://localhost:5434/loadSingle");

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("fileName", rdfFile.fileName+"."+rdfFile.type));
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                HttpResponse response = client.execute(httpPost);

                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity);
                    StringReader reader = new StringReader(result);

                    File receivedFile = new File("src/main/resources/received/"+rdfFile.fileName+"."+rdfFile.type);
                    FileOutputStream fileOutputStream = new FileOutputStream(receivedFile);
                    fileOutputStream.write(result.getBytes());
                    fileOutputStream.close();

                    //reasonerPanel.vowlViewComponent(rdfFile.fileName+"."+rdfFile.type);
                    reasonerPanel.vowlViewComponent.initialiseOWLView();
                    reasonerPanel.vowlControlViewComponent.initialiseOWLView();
                    reasonerPanel.vowlSideBarComponent.initialiseOWLView();


                    reasonerPanel.model.read(reader, null, "RDF/XML");
                    reasonerPanel.rdfTable.setRDFTableModel(reasonerPanel.model);

                    /*StmtIterator iterator = reasonerPanel.model.listStatements();
                    while(iterator.hasNext()){
                        Statement stmt = iterator.nextStatement();  // get next statement
                        Resource subject = stmt.getSubject();
                        Property typePredicate= stmt.getPredicate();   // get the predicate
                        RDFNode typeObject = stmt.getObject();
                        System.out.println(subject.toString()+ " -->  "+ typePredicate.toString()+"  -->  "+ typeObject.toString() );
                    }*/


                }
                if (resEntity != null) {
                    EntityUtils.consume(resEntity);
                }

                client.close();

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            reasonerPanel.fileNames.add(rdfFile.fileName+"."+rdfFile.type);
        }else{
            JOptionPane.showMessageDialog(null, "Please select a row first.");
        }


    }






}
