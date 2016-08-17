package info.japandroid.mp3tool;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;


public class Mp3List {
    private Mp3Object[] mp3s;

    public Mp3List(File[] fileList) throws java.io.IOException, com.mpatric.mp3agic.UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException{
        mp3s = new Mp3Object[fileList.length];
        for (int i = 0; i < fileList.length; i++) {
            mp3s[i] = new Mp3Object(fileList[i]);
        }
        sort();
    }

    public String getArtist(int index){
        return mp3s[index].getArtist();
    }

    public String getAlbumArtist(int index){
        return mp3s[index].getAlbumArtist();
    }

    public String getAlbum(int index){
        return mp3s[index].getAlbum();
    }

    public String getTitle(int index){
        return mp3s[index].getTitle();
    }

    public String getTrack(int index){
        return mp3s[index].getTrack();
    }

    public boolean hasChanged(int index){
        return mp3s[index].hasChanged();
    }

    public String getLength(int index){
        return mp3s[index].getLength();
    }

    public String getBitRate(int index){
        return mp3s[index].getBitRate();
    }

    public String getSimpleFilename(int index){
        return mp3s[index].getSimpleFilename();
    }

    public String getFilename(int index){
        return mp3s[index].getFilename();
    }

    public byte[] getAlbumArt(int index){
        return mp3s[index].getAlbumArt();
    }

    public void setArtist(int index, String artist){
        mp3s[index].setArtist(artist);
    }

    public void setAlbumArtist(int index, String albumArtist){
        mp3s[index].setAlbumArtist(albumArtist);
    }

    public void setTitle(int index, String title){
        mp3s[index].setTitle(title);
    }

    public void setAlbum(int index, String album){
        mp3s[index].setAlbum(album);
    }

    public void setTrack(int index, String track){
        mp3s[index].setTrack(track);
    }

    public void save() throws java.io.IOException, com.mpatric.mp3agic.NotSupportedException{
        for (Mp3Object mp3 : mp3s) {
            if (mp3.hasChanged()){
                mp3.save();
            }
        }
    }

    public void sort(){
        Arrays.sort(mp3s);
    }

}
