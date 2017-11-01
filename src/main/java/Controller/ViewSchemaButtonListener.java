package Controller;

import GUI.BrokerPanel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.RdfFile;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.jena.vocabulary.RDF;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ViewSchemaButtonListener implements ActionListener {

    private BrokerPanel brokerPanel;

    public ViewSchemaButtonListener(BrokerPanel brokerPanel){
        this.brokerPanel = brokerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(brokerPanel.getSelectedRow() != null){
            RdfFile rdfFile = brokerPanel.getSelectedRow();
            try {ArrayList<String[]> files;

                CloseableHttpClient client = HttpClientBuilder.create().build();
                HttpPost httpPost = new HttpPost("http://localhost:5434/showSchema");

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("fileName", rdfFile.fileName+"."+rdfFile.type));
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                HttpResponse response = client.execute(httpPost);

                System.out.println("executing request " + httpPost.getRequestLine());
                HttpEntity resEntity = response.getEntity();

                System.out.println(response.getStatusLine());
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity);
                    try {
                        String dialogMessage="";
                        ObjectMapper mapper = new ObjectMapper();
                        files = mapper.readValue(result, new TypeReference<ArrayList<String[]>>(){});
                        for(int i = 0; i < files.size(); i++){
                            for(int j = 0; j < files.get(i).length; j++){
                                    dialogMessage += "\t"+files.get(i)[j];
                            }
                            dialogMessage+= "\n \t";
                        }
                        System.out.println(dialogMessage);
                        JOptionPane.showMessageDialog(null, dialogMessage);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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
        }else{
            JOptionPane.showMessageDialog(null, "Please select a row first.");

        }
    }
}
