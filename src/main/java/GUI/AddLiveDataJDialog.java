package GUI;

import domain.LiveData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

public class AddLiveDataJDialog extends JDialog {

    public JTextField sourceNameField = new JTextField();
    public JTextField tagsField = new JTextField();
    public JTextField providerField = new JTextField();
    public JTextField liveDataURLField = new JTextField();
    public JButton acceptButton = new JButton("Accept");
    public JButton cancelButton = new JButton("Cancel");
    private BrokerPanel brokerPanel;



    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public AddLiveDataJDialog(Container parent){
        super((JFrame)parent.getParent().getParent().getParent().getParent().getParent());
        this.setModal(true);
        this.setBounds((int)(screenSize.width*0.3),(int)(screenSize.height*0.2),(int)(screenSize.width*0.3), (int)(screenSize.height*0.5));
        this.brokerPanel = (BrokerPanel)parent;
        JPanel datasetInformation = new JPanel();
        JPanel sourceNamePanel = new JPanel();
        JPanel datasetInformationTags = new JPanel();
        JPanel datasetInformationProvider = new JPanel();
        JPanel datasetInformationURL = new JPanel();
        JPanel buttonPanel = new JPanel();

        JLabel sourceName = new JLabel("Source - Name: ");
        JLabel tags = new JLabel("Tags: ");
        JLabel provider = new JLabel("Provider: ");
        JLabel url = new JLabel("Live - Data URL: ");



        Component verticalGlue0 = Box.createVerticalGlue();
        verticalGlue0.setPreferredSize(new Dimension(0, 10));
        Component verticalGlue1 = Box.createVerticalGlue();
        verticalGlue1.setPreferredSize(new Dimension(0, 15));
        Component verticalGlue2 = Box.createVerticalGlue();
        verticalGlue2.setPreferredSize(new Dimension(0, 15));
        Component verticalGlue3 = Box.createVerticalGlue();
        verticalGlue3.setPreferredSize(new Dimension(0, 15));
        Component verticalGlue4 = Box.createVerticalGlue();
        verticalGlue4.setPreferredSize(new Dimension(0, 15));
        Component verticalGlue5 = Box.createVerticalGlue();
        verticalGlue5.setPreferredSize(new Dimension(0, 15));
        Component verticalGlue6 = Box.createVerticalGlue();
        verticalGlue6.setPreferredSize(new Dimension(0, 15));

        Component horizontalGlue0 = Box.createHorizontalGlue();
        horizontalGlue0.setPreferredSize(new Dimension(30, 0));
        Component horizontalGlue1 = Box.createHorizontalGlue();
        horizontalGlue1.setPreferredSize(new Dimension(30, 0));
        Component horizontalGlue2 = Box.createHorizontalGlue();
        horizontalGlue2.setPreferredSize(new Dimension(30, 0));
        Component horizontalGlue3 = Box.createHorizontalGlue();
        horizontalGlue3.setPreferredSize(new Dimension(30, 0));
        Component horizontalGlue4 = Box.createHorizontalGlue();
        horizontalGlue4.setPreferredSize(new Dimension(30, 0));
        Component horizontalGlue5 = Box.createHorizontalGlue();
        horizontalGlue5.setPreferredSize(new Dimension(30, 0));

        datasetInformation.setLayout(new BoxLayout(datasetInformation, BoxLayout.Y_AXIS));
        datasetInformationTags.setLayout(new BoxLayout(datasetInformationTags, BoxLayout.X_AXIS));
        datasetInformationProvider.setLayout(new BoxLayout(datasetInformationProvider, BoxLayout.X_AXIS));
        datasetInformationURL.setLayout(new BoxLayout(datasetInformationURL, BoxLayout.X_AXIS));
        sourceNamePanel.setLayout(new BoxLayout(sourceNamePanel, BoxLayout.X_AXIS));


        sourceName.setBorder(new EmptyBorder(20,40,30,15));
        sourceName.setFont(new Font(sourceName.getFont().getName(), Font.BOLD, 15));
        tags.setBorder(new EmptyBorder(20,68,30,15));
        tags.setFont(new Font(tags.getFont().getName(), Font.BOLD, 15));
        provider.setBorder(new EmptyBorder(20,42,30,15));
        provider.setFont(new Font(provider.getFont().getName(), Font.BOLD, 15));
        url.setBorder(new EmptyBorder(20,42,30,15));
        url.setFont(new Font(provider.getFont().getName(), Font.BOLD, 15));

        sourceNameField.setFont(new Font(tagsField.getFont().getName(), Font.BOLD, 20));
        tagsField.setFont(new Font(tagsField.getFont().getName(), Font.BOLD, 20));
        providerField.setFont(new Font(providerField.getFont().getName(), Font.BOLD, 20));
        liveDataURLField.setFont(new Font(liveDataURLField.getFont().getName(), Font.BOLD, 20));

        sourceNamePanel.add(sourceName);
        sourceNamePanel.add(sourceNameField);
        sourceNamePanel.add(horizontalGlue0);

        datasetInformationTags.add(tags);
        datasetInformationTags.add(tagsField);
        datasetInformationTags.add(horizontalGlue1);

        datasetInformationProvider.add(provider);
        datasetInformationProvider.add(providerField);
        datasetInformationProvider.add(horizontalGlue2);

        datasetInformationURL.add(url);
        datasetInformationURL.add(liveDataURLField);
        datasetInformationURL.add(horizontalGlue3);

        buttonPanel.add(horizontalGlue4);
        buttonPanel.add(acceptButton);
        buttonPanel.add(horizontalGlue5);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (liveDataURLField.getText().equals("") || sourceNameField.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Enter valid source name / URI please.");
                }else{
                    String sourceName = sourceNameField.getText();
                    String tags = tagsField.getText();
                    String url = liveDataURLField.getText();
                    Date date = new Date();
                    long time = date.getTime();
                    String provider = providerField.getText();
                    try {
                        CloseableHttpClient client = HttpClientBuilder.create().build();
                        HttpPost httppost = new HttpPost("http://localhost:5434/saveLiveData");

                        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                        builder.addTextBody("sourceName", sourceName);
                        builder.addTextBody("tags", tags);
                        builder.addTextBody("rating", Double.toString(5.0));
                        builder.addTextBody("dateOfUpload", Long.toString(time));
                        builder.addTextBody("entries", Long.toString(200L));
                        builder.addTextBody("provider", provider);
                        builder.addTextBody("type", "live");
                        builder.addTextBody("url", url);
                        httppost.setEntity(builder.build());
                        HttpResponse response = client.execute(httppost);
                        HttpEntity entity = response.getEntity();

                        System.out.println("executing request " + httppost.getRequestLine());
                        HttpEntity resEntity = response.getEntity();

                        if (resEntity != null) {
                            String result = EntityUtils.toString(resEntity);
                            LiveData liveData = new LiveData(sourceName, tags,5.0,Long.toString(time), 200L, provider,true, "live", url);
                            brokerPanel.addLiveDataRow(liveData);
                        }
                        if (resEntity != null) {
                            EntityUtils.consume(resEntity);
                        }
                        client.close();
                    } catch (MalformedURLException ex) {

                        ex.printStackTrace();

                    } catch (ClientProtocolException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }
            }
        });


        datasetInformation.add(verticalGlue0);
        datasetInformation.add(verticalGlue1);
        datasetInformation.add(sourceNamePanel);
        datasetInformation.add(verticalGlue6);
        datasetInformation.add(datasetInformationTags);
        datasetInformation.add(verticalGlue2);
        datasetInformation.add(datasetInformationProvider);
        datasetInformation.add(verticalGlue3);
        datasetInformation.add(datasetInformationURL);
        datasetInformation.add(verticalGlue4);
        datasetInformation.add(buttonPanel);
        datasetInformation.add(verticalGlue5);

        datasetInformation.setVisible(true);
        this.add(datasetInformation);
        this.setVisible(true);

    }


}
