package info.japandroid.mp3tool;

import java.io.File;

public interface Mp3ModelInterface {

    void registerObserver(Mp3Observer observer);
    void removeObserver(Mp3Observer observer);
    void notifyObservers();
    void addList(File[] mp3files);
    String getTitle(int index);
    String getArtist(int index);
    String getAlbum(int index);
    String getAlbumArtist(int index);
    String getTrack(int index);
    String getFileName(int index);
    void setTitle(String title, int index);
    void setArtist(String artist, int index);
    void setAlbumArtist(String artist, int index);
    void setAlbum(String album, int index);
    void sort();
    boolean hasChanged(int index);
}
