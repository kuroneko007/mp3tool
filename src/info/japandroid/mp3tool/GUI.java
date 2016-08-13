package info.japandroid.mp3tool;

import com.mpatric.mp3agic.ID3v2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;


/**
 * Created by sven on 31/07/16.
 */
public class GUI {
    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JFileChooser fileChooser;
    private Mp3List myMusic;
    private JTextField tfTitle, tfArtist, tfAlbArtist, tfAlbum;
    private JList songList;

    public GUI(){
        buildGUI();
    }

    private void buildGUI(){
        frame = new JFrame("mp3tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,280);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        tfTitle = new JTextField("Title");
        JButton bTitleConfirm = new JButton("Confirm");
        JButton bTitleRevert = new JButton("Revert");
        JButton bTitleAll = new JButton("Apply All");
        titlePanel.add(tfTitle);
        titlePanel.add(bTitleConfirm);
        titlePanel.add(bTitleRevert);
        titlePanel.add(bTitleAll);
        titlePanel.setBorder(new TitledBorder("Title"));
        tagPanel.add(titlePanel);

        JPanel artistPanel = new JPanel();
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.X_AXIS));
        tfArtist = new JTextField("Artist");
        JButton bArtistConfirm = new JButton("Confirm");
        JButton bArtistRevert = new JButton("Revert");
        JButton bArtistAll = new JButton("Apply All");
        artistPanel.add(tfArtist);
        artistPanel.add(bArtistConfirm);
        artistPanel.add(bArtistRevert);
        artistPanel.add(bArtistAll);
        artistPanel.setBorder(new TitledBorder("Artist"));
        tagPanel.add(artistPanel);

        JPanel albArtistPanel = new JPanel();
        albArtistPanel.setLayout(new BoxLayout(albArtistPanel, BoxLayout.X_AXIS));
        tfAlbArtist = new JTextField("Album Artist");
        JButton bAlbArtistConfirm = new JButton("Confirm");
        JButton bAlbArtistRevert = new JButton("Revert");
        JButton bAlbArtistAll = new JButton("Apply All");
        albArtistPanel.add(tfAlbArtist);
        albArtistPanel.add(bAlbArtistConfirm);
        albArtistPanel.add(bAlbArtistRevert);
        albArtistPanel.add(bAlbArtistAll);
        albArtistPanel.setBorder(new TitledBorder("Album Artist"));
        tagPanel.add(albArtistPanel);

        JPanel albumPanel = new JPanel();
        albumPanel.setLayout(new BoxLayout(albumPanel, BoxLayout.X_AXIS));
        tfAlbum = new JTextField("Album");
        JButton bAlbumConfirm = new JButton("Confirm");
        JButton bAlbumRevert = new JButton("Revert");
        JButton bAlbumAll = new JButton("Apply All");
        albumPanel.add(tfAlbum);
        albumPanel.add(bAlbumConfirm);
        albumPanel.add(bAlbumRevert);
        albumPanel.add(bAlbumAll);
        albumPanel.setBorder(new TitledBorder("Album"));
        tagPanel.add(albumPanel);

        JPanel songPanel = new JPanel();
        listModel = new DefaultListModel<>();

        songList = new JList(listModel);
        songList.setVisibleRowCount(12);

        JScrollPane songScroller = new JScrollPane(songList);
        songScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        songScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        songPanel.add(songScroller);
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        songList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    ID3v2 id3 = myMusic.get(songList.getSelectedIndex()).getId3v2Tag();
                    tfTitle.setText(id3.getTitle());
                    tfAlbum.setText(id3.getAlbum());
                    tfArtist.setText(id3.getArtist());
                    tfAlbArtist.setText(id3.getAlbumArtist());
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        JButton bLoad = new JButton("Load");
        buttonPanel.add(bLoad);
        bLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFiles();
            }
        });
        JButton bSave = new JButton("Save");
        buttonPanel.add(bSave);
        JButton bExit = new JButton("Exit");
        buttonPanel.add(bExit);

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        mainPanel.add(songPanel);
        mainPanel.add(tagPanel);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        //frame.pack();
        frame.setVisible(true);
    }

    private void loadFiles(){
        int result = fileChooser.showOpenDialog(frame);
        File[] filesList;
        if (result == JFileChooser.APPROVE_OPTION){
            try {
                filesList = fileChooser.getSelectedFile().listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith("mp3");
                    }
                });
                if (filesList.length != 0) {
                    listModel.clear();
                    myMusic = new Mp3List(filesList);
                    for (File file : filesList
                            ) {
                        listModel.addElement(file.getName());
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

}
