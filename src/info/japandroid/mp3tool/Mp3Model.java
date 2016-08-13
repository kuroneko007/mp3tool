package info.japandroid.mp3tool;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sven on 13/08/16.
 */
public class Mp3Model implements Mp3ModelInterface{

    private Mp3List mp3s;
    private List<Mp3Observer> observerList = new ArrayList<>();

    @Override
    public void addList(File[] mp3files){
        try {
            mp3s = new Mp3List(mp3files);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle(int index){
        return mp3s.get(index).getId3v2Tag().getTitle();
    }

    @Override
    public String getArtist(int index) {
        return mp3s.get(index).getId3v2Tag().getArtist();
    }

    @Override
    public String getAlbum(int index) {
        return mp3s.get(index).getId3v2Tag().getAlbum();
    }

    @Override
    public String getAlbumArtist(int index) {
        return mp3s.get(index).getId3v2Tag().getAlbumArtist();
    }

    @Override
    public void registerObserver(Mp3Observer observer){
        observerList.add(observer);
    }

    @Override
    public void setTitle(String title, int index) {
        mp3s.get(index).getId3v2Tag().setTitle(title);
        mp3s.setChanged(index, true);
        notifyObservers();
    }

    @Override
    public void setAllTitles(String title) {
        notifyObservers();
    }

    @Override
    public void setArtist(String artist, int index) {
        mp3s.get(index).getId3v2Tag().setArtist(artist);
        mp3s.setChanged(index, true);
        notifyObservers();
    }

    @Override
    public void setAllArtists(String artist) {
        notifyObservers();
    }

    @Override
    public void setAlbumArtist(String artist, int index) {
        mp3s.get(index).getId3v2Tag().setAlbumArtist(artist);
        mp3s.setChanged(index, true);
        notifyObservers();
    }

    @Override
    public void setAllAlbumArtists(String artist) {
        notifyObservers();
    }

    @Override
    public void setAlbum(String album, int index) {
        mp3s.get(index).getId3v2Tag().setAlbum(album);
        mp3s.setChanged(index, true);
        notifyObservers();
    }

    @Override
    public void setAllAlbums(String album) {
        notifyObservers();
    }

    @Override
    public boolean hasChanged(int index){
        return mp3s.hasChanged(index);
    }

    @Override
    public void removeObserver(Mp3Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Mp3Observer observer:
             observerList) {
            observer.updateMP3();
        }
    }
}
