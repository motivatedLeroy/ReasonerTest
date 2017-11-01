package Controller;

import GUI.AddDatasetJDialog;
import GUI.BrokerPanel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.RdfFile;
import javafx.stage.FileChooser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.jena.base.Sys;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class FileChooserListener implements ActionListener {

    private AddDatasetJDialog parent;
    private BrokerPanel brokerPanel;
    public ArrayList<RdfFile> files = null;


    public FileChooserListener(AddDatasetJDialog parent, BrokerPanel brokerPanel){
        this.parent = parent;
        this.brokerPanel = brokerPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
            Object source = e.getSource();
            JFileChooser fileChooser = (JFileChooser)source;
            File file = fileChooser.getSelectedFile();
            String fileName= "";
            String[] parts = file.getName().split("\\.");
            for(int i = 0; i < parts.length-1; i++){
                fileName+= parts[i];
            }
            String tags = this.parent.tagsField.getText();
            String provider = this.parent.providerField.getText();
            Date date = new Date();
            long time = date.getTime();


            String type = parts[parts.length-1];


            try {

                CloseableHttpClient client = HttpClientBuilder.create().build();
                HttpPost httppost = new HttpPost("http://localhost:5434/saveRdfFile");

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                builder.addPart("file", new FileBody(file));
                builder.addTextBody("fileName", fileName);
                builder.addTextBody("tags", tags);
                builder.addTextBody("rating", Double.toString(5.0));
                builder.addTextBody("dateOfUpload", Long.toString(time));
                builder.addTextBody("entries", Long.toString(200L));
                builder.addTextBody("provider", provider);
                builder.addTextBody("type", type);
                httppost.setEntity(builder.build());
                HttpResponse response = client.execute(httppost);
                HttpEntity entity = response.getEntity();

                System.out.println("executing request " + httppost.getRequestLine());
                HttpEntity resEntity = response.getEntity();

                System.out.println(response.getStatusLine());
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity);
                    RdfFile rdfFile = new RdfFile(fileName, tags,5.0,Long.toString(time), 200L, provider, type);
                    brokerPanel.addRow(rdfFile);
                   /*try {
                        ObjectMapper mapper = new ObjectMapper();
                        files = mapper.readValue(result, new TypeReference<ArrayList<RdfFile>>(){});
                        for(int i = 0; i < files.size(); i++){
                        }
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

            parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));

        } else if (e.getActionCommand().equals(javax.swing.JFileChooser.CANCEL_SELECTION)) {
            parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
        }
    }
}
