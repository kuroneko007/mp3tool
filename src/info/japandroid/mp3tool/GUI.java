package info.japandroid.mp3tool;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

public class GUI implements Mp3Observer{

    private Mp3ModelInterface model;

    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JFileChooser fileChooser;
    private JTextField tfTitle, tfArtist, tfAlbArtist, tfAlbum, tfTrack, tfTracks;
    private JList<String> songList;
    private SongListListener songListListener;

    public GUI(Mp3ModelInterface model){
        this.model = model;
        model.registerObserver(this);
        buildGUI();
    }

    private void buildGUI(){
        frame = new JFrame("mp3tool");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000,400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        tfTitle = new JTextField("Title", 30);
        JButton bTitleConfirm = new JButton("Confirm");
        JButton bTitleRevert = new JButton("Revert");
        JButton bTitleAll = new JButton("Apply All");
        titlePanel.add(tfTitle);
        titlePanel.add(bTitleConfirm);
        titlePanel.add(bTitleRevert);
        titlePanel.add(bTitleAll);
        bTitleConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setTitle(tfTitle.getText(), songList.getSelectedIndex());
            }
        });
        bTitleAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < listModel.size(); i++) {
                    model.setTitle(tfTitle.getText(), i);
                }
            }
        });
        titlePanel.setBorder(new TitledBorder("Title"));
        tagPanel.add(titlePanel);

        JPanel artistPanel = new JPanel();
        tfArtist = new JTextField("Artist", 30);
        JButton bArtistConfirm = new JButton("Confirm");
        JButton bArtistRevert = new JButton("Revert");
        JButton bArtistAll = new JButton("Apply All");
        artistPanel.add(tfArtist);
        artistPanel.add(bArtistConfirm);
        artistPanel.add(bArtistRevert);
        artistPanel.add(bArtistAll);
        bArtistConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setArtist(tfArtist.getText(), songList.getSelectedIndex());
            }
        });
        bArtistAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < listModel.size(); i++) {
                    model.setArtist(tfArtist.getText(), i);
                }
            }
        });
        artistPanel.setBorder(new TitledBorder("Artist"));
        tagPanel.add(artistPanel);

        JPanel albArtistPanel = new JPanel();
        tfAlbArtist = new JTextField("Album Artist", 30);
        JButton bAlbArtistConfirm = new JButton("Confirm");
        JButton bAlbArtistRevert = new JButton("Revert");
        JButton bAlbArtistAll = new JButton("Apply All");
        albArtistPanel.add(tfAlbArtist);
        albArtistPanel.add(bAlbArtistConfirm);
        albArtistPanel.add(bAlbArtistRevert);
        albArtistPanel.add(bAlbArtistAll);
        bAlbArtistConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setAlbumArtist(tfAlbArtist.getText(), songList.getSelectedIndex());
            }
        });
        bAlbArtistAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < listModel.size(); i++) {
                    model.setAlbumArtist(tfAlbArtist.getText(), i);
                }
            }
        });
        albArtistPanel.setBorder(new TitledBorder("Album Artist"));
        tagPanel.add(albArtistPanel);

        JPanel albumPanel = new JPanel();
        tfAlbum = new JTextField("Album", 30);
        JButton bAlbumConfirm = new JButton("Confirm");
        JButton bAlbumRevert = new JButton("Revert");
        JButton bAlbumAll = new JButton("Apply All");
        albumPanel.add(tfAlbum);
        albumPanel.add(bAlbumConfirm);
        albumPanel.add(bAlbumRevert);
        albumPanel.add(bAlbumAll);
        bAlbumConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setAlbum(tfAlbum.getText(), songList.getSelectedIndex());
            }
        });
        bAlbumAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < listModel.size(); i++) {
                    model.setAlbum(tfAlbum.getText(), i);
                }
            }
        });
        albumPanel.setBorder(new TitledBorder("Album"));
        tagPanel.add(albumPanel);

        JPanel trackPanel = new JPanel();
        tfTrack = new JTextField("", 3);
        JLabel lTracks = new JLabel("of");
        tfTracks = new JTextField("", 3);
        JButton bTrackConfirm = new JButton("Confirm");
        JButton bTrackRevert = new JButton("Revert");
        trackPanel.add(tfTrack);
        trackPanel.add(lTracks);
        trackPanel.add(tfTracks);
        trackPanel.add(bTrackConfirm);
        trackPanel.add(bTrackRevert);
        bTrackConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean dataOK;
                try {
                    if (tfTracks.getText().equals("")){
                        if(tfTrack.getText().equals("")){
                            dataOK = true;
                        } else {
                            Integer.parseInt(tfTrack.getText());
                            dataOK = true;
                        }
                    } else {
                        if (tfTrack.getText().equals("")){
                            JOptionPane.showMessageDialog(frame, "Track number missing");
                           dataOK = false;
                        } else {
                            Integer.parseInt(tfTrack.getText());
                            Integer.parseInt(tfTracks.getText());
                            dataOK = true;
                        }
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(frame, "Track number is not an integer");
                    dataOK = false;
                }
                if (dataOK){
                    StringBuilder builder = new StringBuilder(tfTrack.getText());
                    if (!tfTracks.getText().equals("")){
                        builder.append("/");
                        builder.append(tfTracks.getText());
                    }
                    model.setTrack(builder.toString(), songList.getSelectedIndex());
                }
            }
        });
        trackPanel.setBorder(new TitledBorder("Track"));
        tagPanel.add(trackPanel);

        JPanel songPanel = new JPanel();
        songPanel.setLayout(new BoxLayout(songPanel, BoxLayout.Y_AXIS));
        listModel = new DefaultListModel<>();
        songList = new JList<>(listModel);
        songList.setVisibleRowCount(12);
        songList.setPrototypeCellValue("Long named Artist - What a Long Track Name");
        JScrollPane songScroller = new JScrollPane(songList);
        songScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        songScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        songPanel.add(songScroller);
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        songListListener = new SongListListener();
        songList.addListSelectionListener(songListListener);

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
        bSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFiles();
            }
        });
        JButton bExit = new JButton("Exit");
        buttonPanel.add(bExit);
        bExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        mainPanel.add(songPanel);
        mainPanel.add(tagPanel);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.setVisible(true);
    }

    private void loadFiles(){
        int result = fileChooser.showOpenDialog(frame);
        File[] filesList;
        if (result == JFileChooser.APPROVE_OPTION){
            filesList = fileChooser.getSelectedFile().listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith("mp3");
                }
            });
            if ((filesList != null) && (filesList.length > 0)) {
                try {
                    model.addList(filesList);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                songList.removeListSelectionListener(songListListener);
                listModel.clear();
                for (int i = 0; i < filesList.length; i++) {
                    listModel.addElement(model.getSimpleFilename(i));
                }
                songList.addListSelectionListener(songListListener);
            }
        }
    }

    private void saveFiles(){
        int n = JOptionPane.showConfirmDialog(frame, "Save all changes: Are you sure?", "Confirm Save Action", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            try {
                model.save();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateMP3() {
        for (int i = 0; i < listModel.size(); i++) {
            String currentVal = listModel.get(i);
            boolean tagged = currentVal.contains("<html>");
            if (model.hasChanged(i)){
                if (!tagged) {
                    listModel.set(i, "<html><i>" + currentVal + "</i></html>");
                }
            } else {
                if (tagged){
                    currentVal = currentVal.replace("<html><i>", "");
                    currentVal = currentVal.replace("</i></html>", "");
                    listModel.set(i, currentVal);
                }
            }
        }
    }

    private class SongListListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                int selected = songList.getSelectedIndex();
                tfTitle.setText(model.getTitle(selected));
                tfAlbum.setText(model.getAlbum(selected));
                tfArtist.setText(model.getArtist(selected));
                tfAlbArtist.setText(model.getAlbumArtist(selected));
                String[] track = model.getTrack(selected).split("/");
                tfTrack.setText(track[0]);
                if (track.length > 1) {
                    tfTracks.setText(track[1]);
                } else {
                    tfTracks.setText("");
                }
            }
        }

    }
}
