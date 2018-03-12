package Controller;

import GUI.AddDatasetJDialog;
import GUI.BrokerPanel;
import domain.RdfFile;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class FileChooserListener implements ActionListener {

    private AddDatasetJDialog parent;
    private BrokerPanel brokerPanel;
    public ArrayList<RdfFile> files = null;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");



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
            String reportDate = df.format(date);


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
                builder.addTextBody("dateOfUpload", reportDate);

                Random random = new Random();
                long entries = random.nextInt(5000);
                entries+=200;
                builder.addTextBody("entries", Long.toString(entries));
                builder.addTextBody("provider", String.valueOf(provider));
                builder.addTextBody("type", type);
                httppost.setEntity(builder.build());
                HttpResponse response = client.execute(httppost);
                HttpEntity entity = response.getEntity();

                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {


                    String result = EntityUtils.toString(resEntity);
                    RdfFile rdfFile = new RdfFile(fileName, tags,3.0, reportDate, entries, provider,false, type);
                    brokerPanel.addRdfFileRow(rdfFile);

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
