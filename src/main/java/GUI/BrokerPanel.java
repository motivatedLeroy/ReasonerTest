package GUI;

import Controller.AddDatasetListener;
import Controller.AddLiveDataButtonListener;
import Controller.DatasetToReasoningSessionListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.LiveData;
import domain.RdfFile;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.jena.base.Sys;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BrokerPanel extends JPanel {

    private JPanel brokerTopLine = new JPanel();
    private JPanel brokerBottomLine = new JPanel();
    private JPanel topLeftPanel = new JPanel();
    private JPanel topMidPanel = new JPanel();
    private JPanel topRightPanel = new JPanel();
    public JTable rdfInformationTable = new JTable();
    private JScrollPane brokerMidLine = new JScrollPane(rdfInformationTable);
    private JPanel bottomRightPanel = new JPanel();
    private JPanel filterPanel = new JPanel();
    private final String[] filters = new String[]{"Filename","Tags","Rating","Date of Upload", "Number of Entries", "Provider","Type"};
    private JComboBox filterBox = new JComboBox(filters);
    private JSearchTextField searchTextField = new JSearchTextField();
    private JButton newDatasetButton = new JButton("Upload new Dataset");
    private JButton addLiveDataButton = new JButton("Add Live - Data");
    private JButton addDatasetToSessionButton = new JButton("Add Dataset to Reasoning Session");
    private JButton purchaseButton = new JButton("Purchase Dataset");
    private ReasonerPanel reasonerPanel;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    public DefaultTableModel dtm = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
        }
    };


    /*
    * Allgemeine Layouts zum Anordnen untergeordneter JPanels
    */
    private BoxLayout brokerPanelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
    private BoxLayout topLineLayout = new BoxLayout(brokerTopLine, BoxLayout.X_AXIS);
    private BoxLayout bottomLineLayout = new BoxLayout(brokerBottomLine, BoxLayout.X_AXIS);

    /*
     *  Layouts für die internen JPanels die auf das untere bzw. obere JPanel gelegt werden
     */
    private BorderLayout topLeftLayout = new BorderLayout();
    private BorderLayout topRightLayout = new BorderLayout();
    private BorderLayout bottomLeftLayout = new BorderLayout();
    private BoxLayout filterPanelLayout = new BoxLayout(filterPanel, BoxLayout.X_AXIS);

    //private BoxLayout midRightLayout = new BoxLayout(midRightPanel, BoxLayout.Y_AXIS);

    private BoxLayout bottomRightLayout = new BoxLayout(bottomRightPanel, BoxLayout.Y_AXIS);

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public BrokerPanel(ReasonerPanel reasonerPanel){
        this.reasonerPanel = reasonerPanel;
        /*
        * Komponenten der TopLine werden zusammengefügt, ein Label, eine Combobox, sowie der Button für die Bonusaufgabe
        */

        topLeftPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.6), (int)(screenSize.height*0.1)));
        topLeftPanel.setLayout(topLeftLayout);


        topMidPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.25), (int)(screenSize.height*0.05)));

        filterPanel.setLayout(filterPanelLayout);
        filterPanel.setBorder(new EmptyBorder(20, 0, 0, 10));
        //searchTextField.setBorder(new EmptyBorder(0,0,0,20));
        filterPanel.setPreferredSize(new Dimension((int)(screenSize.width*0.5), (int)(screenSize.height*0.09)));
        filterPanel.add(searchTextField);
        filterBox.setBorder(new EmptyBorder(0,20,0,10));
        filterPanel.add(filterBox);
        topRightPanel.setLayout(topRightLayout);
        topRightPanel.add(filterPanel, BorderLayout.CENTER);
        topRightPanel.setMaximumSize(new Dimension((int)(screenSize.width*0.2), (int)(screenSize.height*0.05)));


        brokerTopLine.setLayout(topLineLayout);
        brokerTopLine.add(topLeftPanel);
        brokerTopLine.add(topMidPanel);
        brokerTopLine.add(topRightPanel);
        brokerTopLine.setBorder(new EmptyBorder(10, 10, 30, 0));

       /*
        * Komponenten der MidLine werden zusammengefügt (Tabelle in JScrollPane + Modell für die Tabelle
        */

        //ruleTextArea.setLineWrap(true);
        /*brokerMidLine.setMaximumSize(new Dimension((int)(screenSize.width*0.8), (int)(screenSize.height*0.8)));
        fileName.setHeaderValue(new String("Filename"));
        tags.setHeaderValue(new String("Tags"));
        rating.setHeaderValue(new String("Rating"));
        dateOfUpload.setHeaderValue(new String("Date of Upload"));
        numberOfEntries.setHeaderValue(new String("Number of entries"));
        provider.setHeaderValue(new String("Provider"));
        type.setHeaderValue(new String("Type"));*/

        dtm.addColumn(new String("Filename"));
        dtm.addColumn(new String("Tags"));
        dtm.addColumn(new String("Rating"));
        dtm.addColumn(new String("Date of Upload"));
        dtm.addColumn(new String("Number of entries"));
        dtm.addColumn(new String("Provider"));
        dtm.addColumn(new String("Live - Support"));
        dtm.addColumn(new String("Type"));
        rdfInformationTable.setModel(dtm);
        Font f = new Font("Arial", Font.BOLD, 25);
        JTableHeader header = rdfInformationTable.getTableHeader();
        header.setFont(f);

        rdfInformationTable.setFont(new Font(rdfInformationTable.getFont().getName(), Font.BOLD, 20));





        brokerMidLine.setBorder(new EmptyBorder(20,20,0,20));
        brokerMidLine.setPreferredSize(new Dimension((int)(screenSize.width*0.66), (int)(screenSize.height)));


       /*
        * Komponenten für die BottomLine werden zusammengefügt. 2 JPanels, die jeweils eine Tabelle
        * und ein Label enthalten sowie ein Panel, dass 1 Label und 2 Buttons enthält
        */




        bottomRightPanel.setMaximumSize(new Dimension((int)(screenSize.width), (int)(screenSize.height*0.2)));
        bottomRightPanel.setBorder(new EmptyBorder(0,0,40,0));
        newDatasetButton.addActionListener(new AddDatasetListener(this));
        bottomRightPanel.add(newDatasetButton);

        addLiveDataButton.addActionListener(new AddLiveDataButtonListener(this));
        bottomRightPanel.add(addLiveDataButton);


        addDatasetToSessionButton.addActionListener(new DatasetToReasoningSessionListener(this, reasonerPanel));
        bottomRightPanel.add(addDatasetToSessionButton);
        bottomRightPanel.add(purchaseButton);


        brokerBottomLine.setLayout(bottomLineLayout);



        brokerBottomLine.add(bottomRightPanel);



       /*
        * Die 3 Lines werden zunächst auf dem MainPanel (JPanel) und dann zusammen mit dem JMenu auf dem MainWindow (JFrame) verankert
        */
        this.setLayout(brokerPanelLayout);

        this.add(brokerTopLine);
        this.add(brokerMidLine);
        this.add(brokerBottomLine);

        //addRdfFileRow(new RdfFile[7]);
        refreshTable();
    }

    public void addRdfFileRow(RdfFile data) {

        String[] row = new String[]{data.fileName, data.tags, Double.toString(data.rating), data.dateOfUpload, Long.toString(data.numberOfEntries), data.provider, String.valueOf(false), data.type};
        dtm.addRow(row);

        try {
            for (int i=0; i<rdfInformationTable.getRowCount(); i++) {
                int rowHeight = rdfInformationTable.getRowHeight();

                for (int column=0; column<rdfInformationTable.getColumnCount(); column++) {
                    Component comp = rdfInformationTable.prepareRenderer(rdfInformationTable.getCellRenderer(i, column), i, column);
                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                }

                rdfInformationTable.setRowHeight(i, rowHeight);
            }
        } catch(ClassCastException e) {

        }
    }

    public void addLiveDataRow(LiveData data) {


        String[] row = new String[]{data.sourceName, data.tags, Double.toString(data.rating), data.dateOfUpload, Long.toString(data.numberOfEntries), data.provider, String.valueOf(true), data.type};
        dtm.addRow(row);
        try {
            for (int i=0; i<rdfInformationTable.getRowCount(); i++) {
                int rowHeight = rdfInformationTable.getRowHeight();

                for (int column=0; column<rdfInformationTable.getColumnCount(); column++) {
                    Component comp = rdfInformationTable.prepareRenderer(rdfInformationTable.getCellRenderer(i, column), i, column);
                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                }

                rdfInformationTable.setRowHeight(i, rowHeight);
            }
        } catch(ClassCastException e) {

        }

    }

    public RdfFile getSelectedRow(){
        if(rdfInformationTable.getSelectedRow() !=-1){
            return new RdfFile(dtm.getValueAt(rdfInformationTable.getSelectedRow(),0).toString(),
                    dtm.getValueAt(rdfInformationTable.getSelectedRow(),1).toString(),
                    Double.valueOf(dtm.getValueAt(rdfInformationTable.getSelectedRow(),2).toString()),
                    dtm.getValueAt(rdfInformationTable.getSelectedRow(),3).toString(),
                    Long.valueOf(dtm.getValueAt(rdfInformationTable.getSelectedRow(),4).toString()),
                    dtm.getValueAt(rdfInformationTable.getSelectedRow(),5).toString(),
                    Boolean.valueOf(dtm.getValueAt(rdfInformationTable.getSelectedRow(),6).toString()),
                    dtm.getValueAt(rdfInformationTable.getSelectedRow(),7).toString()
                        );
        }else {
            return null;
        }
    }

    public void refreshTable(){

        try {ArrayList<RdfFile>files;

            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet("http://localhost:5434/loadAll");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            HttpResponse response = client.execute(httpGet);

            System.out.println("executing request " + httpGet.getRequestLine());
            HttpEntity resEntity = response.getEntity();

            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                String result = EntityUtils.toString(resEntity);
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    files = mapper.readValue(result, new TypeReference<ArrayList<RdfFile>>(){});
                    for(int i = 0; i < files.size(); i++){
                        this.addRdfFileRow(files.get(i));
                    }
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

        try {ArrayList<LiveData>files;

            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet("http://localhost:5434/loadAllLive");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            HttpResponse response = client.execute(httpGet);

            System.out.println("executing request " + httpGet.getRequestLine());
            HttpEntity resEntity = response.getEntity();

            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                String result = EntityUtils.toString(resEntity);
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    files = mapper.readValue(result, new TypeReference<ArrayList<LiveData>>(){});
                    for(int i = 0; i < files.size(); i++){
                        this.addLiveDataRow(files.get(i));
                    }
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

    }

}
