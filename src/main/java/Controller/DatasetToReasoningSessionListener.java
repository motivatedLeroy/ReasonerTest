package Controller;

import GUI.BrokerPanel;
import GUI.ReasonerPanel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.apache.jena.rdf.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

public class DatasetToReasoningSessionListener implements ActionListener {
    private BrokerPanel brokerPanel;
    private ReasonerPanel reasonerPanel;

    public DatasetToReasoningSessionListener(BrokerPanel brokerPanel, ReasonerPanel reasonerPanel){
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

                System.out.println(response.getStatusLine());
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity);
                    System.out.println(result);

                    final Model model = ModelFactory.createDefaultModel();
                    model.read(new ByteArrayInputStream(result.getBytes()), null);
                    model.write(System.out, "TTL");
                    StmtIterator iterator = model.listStatements();

                    ComboBoxModel subjectComboBoxModel = reasonerPanel.subjectComboBox.getModel();
                    Vector subjectComboBoxItems = new Vector();
                    ComboBoxModel predicateComboBoxModel = reasonerPanel.predicateComboBox.getModel();
                    Vector predicateComboBoxItems = new Vector();
                    ComboBoxModel objectComboBoxModel = reasonerPanel.objectComboBox.getModel();
                    Vector objectComboBoxItems = new Vector();
                    HashSet<String> tempSub0 = new HashSet<>();
                    HashSet<String> tempPred = new HashSet<>();
                    HashSet<String> tempPred1 = new HashSet<>();
                    for(int i = 0; i< subjectComboBoxModel.getSize(); i++){
                        subjectComboBoxItems.add(subjectComboBoxModel.getElementAt(i));
                    }
                    for(int i = 0; i< predicateComboBoxModel.getSize(); i++){
                        predicateComboBoxItems.add(predicateComboBoxModel.getElementAt(i));
                    }
                    for(int i = 0; i< objectComboBoxModel.getSize(); i++){
                        objectComboBoxItems.add(objectComboBoxModel.getElementAt(i));
                    }
                    while (iterator.hasNext()) {
                        Statement stmt = iterator.nextStatement();  // get next statement
                        Resource subject = stmt.getSubject();     // get the subject
                        Property predicate = stmt.getPredicate();   // get the predicate
                        RDFNode object = stmt.getObject();      // get the object#


                        if (tempSub0.contains(subject.toString())) {
                            if(tempPred.contains(predicate.toString())) {
                                reasonerPanel.dtm.addRow(new String[]{"", "", object.toString()});
                            }else{
                                if(tempPred1.contains(predicate.toString())){
                                    objectComboBoxItems.add(object.toString());

                                }else{
                                    objectComboBoxItems.add(object.toString());
                                    predicateComboBoxItems.add(predicate.toString());
                                }
                                reasonerPanel.dtm.addRow(new String[]{"", predicate.toString(), object.toString()});
                            }

                        }else{
                            tempPred = new HashSet<>();
                            reasonerPanel.dtm.addRow(new String[]{subject.toString(), predicate.toString(), object.toString()});
                            subjectComboBoxItems.add(subject.toString());
                            if(tempPred1.contains(predicate.toString())){
                                objectComboBoxItems.add(object.toString());
                            }else {
                                predicateComboBoxItems.add(predicate.toString());
                                objectComboBoxItems.add(object.toString());
                            }

                        }
                        tempSub0.add(subject.toString());
                        tempPred.add(predicate.toString());
                        tempPred1.add(predicate.toString());
                    }
                    subjectComboBoxModel = new DefaultComboBoxModel(subjectComboBoxItems);
                    reasonerPanel.subjectComboBox.setModel(subjectComboBoxModel);
                    predicateComboBoxModel = new DefaultComboBoxModel(predicateComboBoxItems);
                    reasonerPanel.predicateComboBox.setModel(predicateComboBoxModel);
                    objectComboBoxModel = new DefaultComboBoxModel(objectComboBoxItems);
                    reasonerPanel.objectComboBox.setModel(objectComboBoxModel);
                    /*try {
                        ObjectMapper mapper = new ObjectMapper();
                        rdfFile = mapper.readValue(result, RdfFile.class);
                        reasonerPanel.rdfFiles.add(rdfFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
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
            }
            reasonerPanel.fileNames.add(rdfFile.fileName+"."+rdfFile.type);
        }else{
            JOptionPane.showMessageDialog(null, "Please select a row first.");

        }

    }
}
