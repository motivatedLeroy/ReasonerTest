package GUI;

import Controller.FileChooserListener;
import domain.RdfFile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;

public class AddDatasetJDialog extends JDialog{

    public JTextField tagsField = new JTextField();
    public JTextField providerField = new JTextField();



    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public AddDatasetJDialog(Container parent){
        super((JFrame)parent.getParent().getParent().getParent().getParent().getParent());
        this.setModal(true);
        this.setBounds((int)(screenSize.width*0.2),(int)(screenSize.height*0.2),(int)(screenSize.width*0.3), (int)(screenSize.height*0.5));

        JPanel datasetInformation = new JPanel();
        JPanel datasetInformationTags = new JPanel();
        JPanel datasetInformationProvider = new JPanel();
        JLabel fileName = new JLabel("Filename: ");
        JLabel tags = new JLabel("Tags: ");
        JLabel provider = new JLabel("Provider: ");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addActionListener(new FileChooserListener(this, (BrokerPanel) parent));
        fileChooser.setFileFilter(new FileNameExtensionFilter("RDF / TTL / OWL Dateien","rdf", "ttl", "owl"));

        Component verticalGlue0 = Box.createVerticalGlue();
        verticalGlue0.setPreferredSize(new Dimension(0, 10));
        Component verticalGlue1 = Box.createVerticalGlue();
        verticalGlue1.setPreferredSize(new Dimension(0, 15));
        Component verticalGlue2 = Box.createVerticalGlue();
        verticalGlue2.setPreferredSize(new Dimension(0, 15));
        Component verticalGlue3 = Box.createVerticalGlue();
        verticalGlue3.setPreferredSize(new Dimension(0, 15));
        Component horizontalGlue0 = Box.createHorizontalGlue();
        horizontalGlue0.setPreferredSize(new Dimension(30, 0));
        Component horizontalGlue1 = Box.createHorizontalGlue();
        horizontalGlue1.setPreferredSize(new Dimension(30, 0));
        Component horizontalGlue2 = Box.createHorizontalGlue();
        horizontalGlue2.setPreferredSize(new Dimension(30, 0));


        datasetInformation.setLayout(new BoxLayout(datasetInformation, BoxLayout.Y_AXIS));
        datasetInformationTags.setLayout(new BoxLayout(datasetInformationTags, BoxLayout.X_AXIS));
        datasetInformationProvider.setLayout(new BoxLayout(datasetInformationProvider, BoxLayout.X_AXIS));

        fileName.setBorder(new EmptyBorder(20,40,30,15));
        fileName.setFont(new Font(fileName.getFont().getName(), Font.BOLD, 15));
        tags.setBorder(new EmptyBorder(20,68,30,15));
        tags.setFont(new Font(tags.getFont().getName(), Font.BOLD, 15));
        provider.setBorder(new EmptyBorder(20,42,30,15));
        provider.setFont(new Font(provider.getFont().getName(), Font.BOLD, 15));

        tagsField.setFont(new Font(tagsField.getFont().getName(), Font.BOLD, 20));
        providerField.setFont(new Font(providerField.getFont().getName(), Font.BOLD, 20));


        datasetInformationTags.add(tags);
        datasetInformationTags.add(tagsField);
        datasetInformationTags.add(horizontalGlue1);

        datasetInformationProvider.add(provider);
        datasetInformationProvider.add(providerField);
        datasetInformationProvider.add(horizontalGlue2);

        datasetInformation.add(fileChooser);
        datasetInformation.add(verticalGlue0);
        datasetInformation.add(verticalGlue1);
        datasetInformation.add(datasetInformationTags);
        datasetInformation.add(verticalGlue2);
        datasetInformation.add(datasetInformationProvider);
        datasetInformation.add(verticalGlue3);
        datasetInformation.setVisible(true);
        this.add(datasetInformation);
        this.setVisible(true);

    }

}
