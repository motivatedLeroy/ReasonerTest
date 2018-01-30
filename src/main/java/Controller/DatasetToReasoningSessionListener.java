package Controller;

import GUI.BrokerPanel;
import GUI.RDFTable;
import GUI.ReasonerPanel;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.LiveData;
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
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import utils.xml.xml2csv.XML2CSVGenericGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
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
        base.put("rj", "RJ");
        this.brokerPanel = brokerPanel;
        this.reasonerPanel = reasonerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(brokerPanel.getSelectedRow() != null){
            RdfFile rdfFile = brokerPanel.getSelectedRow();
            if(rdfFile.liveData){
                try {
                    CloseableHttpClient client = HttpClientBuilder.create().build();
                    HttpPost httpPost = new HttpPost("http://localhost:5434/loadSingleLive");

                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                    params.add(new BasicNameValuePair("sourceName", rdfFile.fileName));
                    httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                    HttpResponse response = client.execute(httpPost);

                    HttpEntity resEntity = response.getEntity();

                    if (resEntity != null) {
                        ObjectMapper mapper = new ObjectMapper();
                        LiveData liveData = mapper.readValue(EntityUtils.toString(resEntity), LiveData.class);

                        System.out.println(liveData.url);
                        URL urlObject = new URL(liveData.url);
                        URLConnection urlConnection = urlObject.openConnection();
                        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                        String s = toString(urlConnection.getInputStream());


                        File file = new File("src/main/resources/received/"+rdfFile.fileName+".xml");
                        FileWriter fileWriter = new FileWriter(file);
                        try{
                            fileWriter.write(s);
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }finally {
                            fileWriter.close();
                        }
                            File[] input = new File[]{file};
                            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/"+rdfFile.fileName+".csv");
                            try{
                                XML2CSVGenericGenerator converter = new XML2CSVGenericGenerator(fileOutputStream, ",", StandardCharsets.UTF_8, null);
                                converter.generate(input, true);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }finally {
                                fileOutputStream.close();
                            }

                            Model tempModel = ModelFactory.createDefaultModel();
                            tempModel.read("src/main/resources/"+rdfFile.fileName+".csv");
                            RDFTable rdfTable = new RDFTable(reasonerPanel, true);
                            rdfTable.readCSVTableModel(tempModel);
                            reasonerPanel.addRDFTable(rdfTable, rdfFile.fileName);
                            reasonerPanel.model.add(rdfTable.model);


                        reasonerPanel.vowlViewComponent.initialiseOWLView();
                        reasonerPanel.vowlControlViewComponent.initialiseOWLView();
                        reasonerPanel.vowlSideBarComponent.initialiseOWLView();

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
            }else {

                try {
                    CloseableHttpClient client = HttpClientBuilder.create().build();
                    HttpPost httpPost = new HttpPost("http://localhost:5434/loadSingle");

                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                    params.add(new BasicNameValuePair("fileName", rdfFile.fileName + "." + rdfFile.type));
                    httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                    HttpResponse response = client.execute(httpPost);

                    HttpEntity resEntity = response.getEntity();

                    if (resEntity != null) {

                        File file = new File("src/main/resources/received/" + rdfFile.fileName + "." + rdfFile.type);
                        FileWriter fileWriter = new FileWriter(file);
                        try {
                            fileWriter.write(EntityUtils.toString(resEntity));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } finally {
                            fileWriter.close();
                        }

                        if (rdfFile.type.equals("xml")) {
                            File[] input = new File[]{file};
                            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/" + rdfFile.fileName + ".csv");
                            try {
                                XML2CSVGenericGenerator converter = new XML2CSVGenericGenerator(fileOutputStream, ",", StandardCharsets.UTF_8, null);
                                converter.generate(input, true);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            } finally {
                                fileOutputStream.close();
                            }

                            Model tempModel = ModelFactory.createDefaultModel();
                            tempModel.read("src/main/resources/" + rdfFile.fileName + ".csv");
                            RDFTable rdfTable = new RDFTable(reasonerPanel, false);
                            rdfTable.readCSVTableModel(tempModel);
                            reasonerPanel.addRDFTable(rdfTable, rdfFile.fileName);
                            reasonerPanel.model.add(rdfTable.model);


                        } else if (rdfFile.type.equals("csv")) {
                            Model tempModel = ModelFactory.createDefaultModel();
                            tempModel.read("src/main/resources/received/" + rdfFile.fileName + ".csv");
                            RDFTable rdfTable = new RDFTable(reasonerPanel, false);
                            rdfTable.readRDFTableModel(tempModel);
                            reasonerPanel.addRDFTable(rdfTable, rdfFile.fileName);
                            reasonerPanel.model.add(rdfTable.model);
                        } else {
                            Model tempModel = ModelFactory.createDefaultModel();
                            tempModel.read("src/main/resources/received/" + rdfFile.fileName + "." + rdfFile.type);
                            RDFTable rdfTable = new RDFTable(reasonerPanel, false);
                            rdfTable.readRDFTableModel(tempModel);
                            reasonerPanel.addRDFTable(rdfTable, rdfFile.fileName);
                            reasonerPanel.model.add(rdfTable.model);
                        }

                        StmtIterator iterator = reasonerPanel.model.listStatements();
                        int i = 0;
                        while (iterator.hasNext()) {
                            iterator.nextStatement();
                            i++;
                        }
                        System.out.println(i);

                        reasonerPanel.vowlViewComponent.initialiseOWLView();
                        reasonerPanel.vowlControlViewComponent.initialiseOWLView();
                        reasonerPanel.vowlSideBarComponent.initialiseOWLView();

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
            }
            reasonerPanel.fileNames.add(rdfFile.fileName+"."+rdfFile.type);
        }else{
            JOptionPane.showMessageDialog(null, "Please select a row first.");
        }


    }


    private static String toString(InputStream inputStream) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }






}
