package info.japandroid.mp3tool;

import com.mpatric.mp3agic.Mp3File;

import java.io.File;
import java.util.List;

/**
 * Created by sven on 07/08/16.
 */
public class Mp3List {
    private Mp3File[] mp3FileList;
    private boolean[] changeList;
    private int nextSlot;

    public Mp3List(int size){
        mp3FileList = new Mp3File[size];
        changeList = new boolean[size];
        nextSlot = 0;
    }

    public Mp3List(List<Mp3File> mp3Files){
        mp3FileList = mp3Files.toArray(new Mp3File[0]);
        changeList = new boolean[mp3FileList.length];
        nextSlot = mp3FileList.length +1;
    }

    public Mp3List(Mp3File[] mp3files){
        mp3FileList = new Mp3File[mp3files.length];
        System.arraycopy(mp3files, 0, mp3FileList, 0, mp3files.length);
        changeList = new boolean[mp3files.length];
        nextSlot = mp3FileList.length +1;
    }

    public Mp3List(File[] fileList) throws java.io.IOException, com.mpatric.mp3agic.UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException{
        mp3FileList = new Mp3File[fileList.length];
        changeList = new boolean[fileList.length];
        for (File file:fileList) {
            add(new Mp3File(file));
        }
    }

    public Mp3File get(int index){
        return mp3FileList[index];
    }

    public boolean hasChanged(int index){
        return changeList[index];
    }

    public void setChanged (int index, boolean changed){
        changeList[index] = changed;
    }

    public boolean add(Mp3File mp3){
        if(nextSlot <= mp3FileList.length){
            mp3FileList[nextSlot] = mp3;
            nextSlot++;
            return true;
        } else {
            return false;
        }
    }
}
