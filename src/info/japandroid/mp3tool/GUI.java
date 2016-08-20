package info.japandroid.mp3tool;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;

public class GUI implements Mp3Observer{

    private Mp3ModelInterface model;

    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JFileChooser fileChooser;
    private JButton bTitleConfirm, bTitleAll, bArtistConfirm, bArtistAll, bAlbArtistConfirm, bAlbArtistAll, bAlbumConfirm, bAlbumAll, bTrackConfirm, bImgLoad, bImgApply, bImgAll, bLoad, bSave, bExit;
    private JTextField tfTitle, tfArtist, tfAlbArtist, tfAlbum, tfTrack, tfTracks;
    private JLabel lLength, lBitRate, lAlbumArt, lArtInfo;
    private JList<String> songList;
    private SongListListener songListListener;
    private ImageIcon iBlank;
    private Image iAlbumArt;
    private String mimeType;

    public GUI(Mp3ModelInterface model){
        this.model = model;
        model.registerObserver(this);
        buildGUI();
    }

    private void buildGUI(){
        frame = new JFrame("mp3tool");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000,600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        tfTitle = new JTextField("Title", 30);
        bTitleConfirm = new JButton("Confirm");
        bTitleConfirm.setEnabled(false);
        bTitleAll = new JButton("Apply All");
        bTitleAll.setEnabled(false);
        titlePanel.add(tfTitle);
        titlePanel.add(bTitleConfirm);
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
        bArtistConfirm = new JButton("Confirm");
        bArtistConfirm.setEnabled(false);
        bArtistAll = new JButton("Apply All");
        bArtistAll.setEnabled(false);
        artistPanel.add(tfArtist);
        artistPanel.add(bArtistConfirm);
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
        bAlbArtistConfirm = new JButton("Confirm");
        bAlbArtistConfirm.setEnabled(false);
        bAlbArtistAll = new JButton("Apply All");
        bAlbArtistAll.setEnabled(false);
        albArtistPanel.add(tfAlbArtist);
        albArtistPanel.add(bAlbArtistConfirm);
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
        bAlbumConfirm = new JButton("Confirm");
        bAlbumConfirm.setEnabled(false);
        bAlbumAll = new JButton("Apply All");
        bAlbumAll.setEnabled(false);
        albumPanel.add(tfAlbum);
        albumPanel.add(bAlbumConfirm);
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

        JPanel infoNArt = new JPanel();
        JPanel trackNInfo = new JPanel();
        trackNInfo.setLayout(new BoxLayout(trackNInfo, BoxLayout.Y_AXIS));
        JPanel trackPanel = new JPanel();
        tfTrack = new JTextField("", 3);
        JLabel lTracks = new JLabel("of");
        tfTracks = new JTextField("", 3);
        bTrackConfirm = new JButton("Confirm");
        bTrackConfirm.setEnabled(false);
        trackPanel.add(tfTrack);
        trackPanel.add(lTracks);
        trackPanel.add(tfTracks);
        trackPanel.add(bTrackConfirm);
        bTrackConfirm.addActionListener(new TrackListener());
        trackNInfo.add(trackPanel);
        JPanel infoPanel = new JPanel();
        lLength = new JLabel("Length:       ");
        lBitRate = new JLabel("Bitrate:            ");
        infoPanel.add(lLength);
        infoPanel.add(lBitRate);
        trackNInfo.add(infoPanel);
        trackNInfo.setBorder(new TitledBorder("Track"));
        infoNArt.add(trackNInfo);
        JPanel artPanel = new JPanel();
        artPanel.setLayout(new BoxLayout(artPanel, BoxLayout.Y_AXIS));
        JPanel imgPanel = new JPanel();
        JPanel artButtonPanel = new JPanel();
        lAlbumArt = new JLabel();
        imgPanel.add(lAlbumArt);
        iBlank = new ImageIcon("./blank.png");
        lAlbumArt.setIcon(iBlank);
        artPanel.add(imgPanel);
        JPanel artInfoPanel = new JPanel();
        lArtInfo = new JLabel("No Album Art");
        artInfoPanel.add(lArtInfo);
        artPanel.add(artInfoPanel);

        bImgLoad = new JButton("Load Image");
        bImgLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadArt();
            }
        });
        bImgLoad.setEnabled(false);
        bImgApply = new JButton("Confirm");
        bImgApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAlbumArt(songList.getSelectedIndex());
            }
        });
        bImgApply.setEnabled(false);
        bImgAll = new JButton("Apply All");
        bImgAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < listModel.size(); i++) {
                    saveAlbumArt(i);
                }
            }
        });
        bImgAll.setEnabled(false);
        artButtonPanel.add(bImgLoad);
        artButtonPanel.add(bImgApply);
        artButtonPanel.add(bImgAll);
        artPanel.add(artButtonPanel);
        artPanel.setBorder(new TitledBorder("Cover Art"));
        infoNArt.add(artPanel);
        tagPanel.add(infoNArt);

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
        bLoad = new JButton("Load");
        buttonPanel.add(bLoad);
        bLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFiles();
            }
        });
        bSave = new JButton("Save");
        buttonPanel.add(bSave);
        bSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFiles();
            }
        });
        bSave.setEnabled(false);
        bExit = new JButton("Exit");
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
                enableButtons();
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

    private void loadArt(){
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        JFileChooser artChooser = new JFileChooser();
        artChooser.setFileFilter(imageFilter);
        int result = artChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION){
            File fArt = artChooser.getSelectedFile();
            try {
                InputStream is = new BufferedInputStream(new FileInputStream(fArt));
                mimeType = URLConnection.guessContentTypeFromStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            iAlbumArt = new ImageIcon(fArt.getPath()).getImage();
            Image scaledImage = iAlbumArt.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lAlbumArt.setIcon(new ImageIcon(scaledImage));
            int width = iAlbumArt.getWidth(null);
            int height = iAlbumArt.getHeight(null);
            lArtInfo.setText(width + " x " + height + "px, " + mimeType);
        }
    }

    private void saveAlbumArt(int index){
        BufferedImage buffArt = new BufferedImage(iAlbumArt.getWidth(null), iAlbumArt.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = buffArt.createGraphics();
        g2.drawImage(iAlbumArt, null, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(buffArt, mimeType.split("/")[1], baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] byteArt = baos.toByteArray();
        model.setAlbumArt(byteArt, mimeType, index);
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

    private boolean validateTracks(){
        boolean dataOK;
        try {
            if (tfTracks.getText().equals("")) {
                if (tfTrack.getText().equals("")) {
                    dataOK = true;
                } else {
                    Integer.parseInt(tfTrack.getText());
                    dataOK = true;
                }
            } else {
                if (tfTrack.getText().equals("")) {
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
        return dataOK;
    }

    private void enableButtons(){
        bAlbArtistAll.setEnabled(true);
        bAlbArtistConfirm.setEnabled(true);
        bAlbumAll.setEnabled(true);
        bAlbumConfirm.setEnabled(true);
        bArtistAll.setEnabled(true);
        bArtistConfirm.setEnabled(true);
        bTitleAll.setEnabled(true);
        bTitleConfirm.setEnabled(true);
        bTrackConfirm.setEnabled(true);
        bImgLoad.setEnabled(true);
        bImgAll.setEnabled(true);
        bImgApply.setEnabled(true);
        bSave.setEnabled(true);
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
                lLength.setText("Length: " + model.getLength(selected) + " ");
                lBitRate.setText("Bitrate: " + model.getBitRate(selected));
                byte[] imageData = model.getAlbumArt(selected);
                if (imageData != null){
                    iAlbumArt = new ImageIcon(imageData).getImage();
                    Image scaledImage = iAlbumArt.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    lAlbumArt.setIcon(new ImageIcon(scaledImage));
                    int width = iAlbumArt.getWidth(null);
                    int height = iAlbumArt.getHeight(null);
                    String type = model.getMimeType(selected);
                    lArtInfo.setText(width + " x " + height + "px, " + type);
                } else {
                    lAlbumArt.setIcon(iBlank);
                    iAlbumArt = null;
                    lArtInfo.setText("No Album Art");
                }
            }
        }

    }

    private class TrackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validateTracks()) {
                StringBuilder builder = new StringBuilder(tfTrack.getText());
                if (!tfTracks.getText().equals("")) {
                    builder.append("/");
                    builder.append(tfTracks.getText());
                }
                model.setTrack(builder.toString(), songList.getSelectedIndex());
            }
        }
    }
}
