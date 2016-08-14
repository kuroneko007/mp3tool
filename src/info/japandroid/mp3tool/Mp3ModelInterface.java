package info.japandroid.mp3tool;

import java.io.File;

public interface Mp3ModelInterface {

    void registerObserver(Mp3Observer observer);
    void removeObserver(Mp3Observer observer);
    void notifyObservers();
    void addList(File[] mp3files) throws java.io.IOException, com.mpatric.mp3agic.InvalidDataException, com.mpatric.mp3agic.UnsupportedTagException;
    String getTitle(int index);
    String getArtist(int index);
    String getAlbum(int index);
    String getAlbumArtist(int index);
    String getTrack(int index);
    String getFileName(int index);
    String getSimpleFilename(int index);
    String getBitRate(int index);
    String getLength(int index);
    void setTitle(String title, int index);
    void setArtist(String artist, int index);
    void setAlbumArtist(String artist, int index);
    void setAlbum(String album, int index);
    void setTrack(String track, int index);
    void sort();
    void save() throws java.io.IOException, com.mpatric.mp3agic.NotSupportedException;
    boolean hasChanged(int index);
}
