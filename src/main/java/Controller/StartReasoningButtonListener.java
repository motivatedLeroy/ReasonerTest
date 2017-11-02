package Controller;

import GUI.ReasonerPanel;
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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartReasoningButtonListener implements ActionListener {
    private ReasonerPanel reasonerPanel;

    public StartReasoningButtonListener(ReasonerPanel reasonerPanel){
        this.reasonerPanel = reasonerPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i< reasonerPanel.ruleSet.size(); i++){
            System.out.println(reasonerPanel.ruleSet.size());
        }
        Path path = Paths.get("src/main/resources/ruleSet.rules");
        try {
            Files.write(path, reasonerPanel.ruleSet, Charset.forName("UTF-8"));

            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("http://localhost:5434/reason");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            builder.addTextBody("fileName", reasonerPanel.fileNames.get(0));
            builder.addPart("file", new FileBody(new File("src/main/resources/ruleSet.rules")));
            httpPost.setEntity(builder.build());

            HttpResponse response = client.execute(httpPost);

            HttpEntity resEntity = response.getEntity();

            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                String result = EntityUtils.toString(resEntity);
                System.out.println(result);
            }
            if (resEntity != null) {
                EntityUtils.consume(resEntity);
            }
            client.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }


        reasonerPanel.ruleSet = new ArrayList<>();
    }
}
