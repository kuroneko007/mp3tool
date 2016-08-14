package info.japandroid.mp3tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Mp3Model implements Mp3ModelInterface{

    private Mp3List mp3s;
    private List<Mp3Observer> observerList = new ArrayList<>();

    @Override
    public void addList(File[] mp3files) throws java.io.IOException, com.mpatric.mp3agic.UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException{
        mp3s = new Mp3List(mp3files);
    }

    @Override
    public String getTitle(int index){
        return mp3s.getTitle(index);
    }

    @Override
    public String getArtist(int index) {
        return mp3s.getArtist(index);
    }

    @Override
    public String getAlbum(int index) {
        return mp3s.getAlbum(index);
    }

    @Override
    public String getAlbumArtist(int index) {
        return mp3s.getAlbumArtist(index);
    }

    @Override
    public String getTrack(int index){
        return mp3s.getTrack(index);
    }

    @Override
    public String getFileName(int index) {
        return mp3s.getFilename(index);
    }

    @Override
    public String getSimpleFilename(int index) {
        return mp3s.getSimpleFilename(index);
    }

    @Override
    public String getBitRate(int index) {
        return mp3s.getBitRate(index);
    }

    @Override
    public String getLength(int index) {
        return mp3s.getLength(index);
    }

    @Override
    public void registerObserver(Mp3Observer observer){
        observerList.add(observer);
    }

    @Override
    public void setTitle(String title, int index) {
        mp3s.setTitle(index, title);
        notifyObservers();
    }

    @Override
    public void setArtist(String artist, int index) {
        mp3s.setArtist(index, artist);
        notifyObservers();
    }

    @Override
    public void setAlbumArtist(String artist, int index) {
        mp3s.setAlbumArtist(index, artist);
        notifyObservers();
    }

    @Override
    public void setAlbum(String album, int index) {
        mp3s.setAlbum(index, album);
        notifyObservers();
    }

    @Override
    public void setTrack(String track, int index) {
        mp3s.setTrack(index, track);
        notifyObservers();
    }

    @Override
    public boolean hasChanged(int index){
        return mp3s.hasChanged(index);
    }

    @Override
    public void sort() {
        mp3s.sort();
    }

    @Override
    public void save() throws java.io.IOException, com.mpatric.mp3agic.NotSupportedException{
        mp3s.save();
        notifyObservers();
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
