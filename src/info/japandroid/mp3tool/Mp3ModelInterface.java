package info.japandroid.mp3tool;

import java.io.File;

/**
 * Created by sven on 13/08/16.
 */
public interface Mp3ModelInterface {

    void registerObserver(Mp3Observer observer);
    void removeObserver(Mp3Observer observer);
    void notifyObservers();
    void addList(File[] mp3files);
    String getTitle(int index);
    String getArtist(int index);
    String getAlbum(int index);
    String getAlbumArtist(int index);
    void setTitle(String title, int index);
    void setAllTitles(String title);
    void setArtist(String artist, int index);
    void setAllArtists(String artist);
    void setAlbumArtist(String artist, int index);
    void setAllAlbumArtists(String artist);
    void setAlbum(String album, int index);
    void setAllAlbums(String album);
    boolean hasChanged(int index);
}
