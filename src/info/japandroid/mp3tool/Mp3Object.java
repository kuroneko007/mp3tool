package info.japandroid.mp3tool;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Mp3Object implements Comparable<Mp3Object>{

    private boolean changed;
    private String simpleFilename;
    private String ogTitle, ogAlbum, ogAlbArtist, ogArtist, ogTrack;
    private Mp3File mp3;

    public Mp3Object(String filename) throws IOException, UnsupportedTagException, InvalidDataException {
        mp3 = new Mp3File(filename, false);
        changed = false;
    }

    public Mp3Object(File file) throws IOException, UnsupportedTagException, InvalidDataException {
        mp3 = new Mp3File(file, 65536, true); //65536 corresponds to DEFAULT_BUFFER_LENGTH in Mp3File
        changed = false;
        simpleFilename = file.getName();
    }

    public String getArtist(){
        if (mp3.hasId3v2Tag()){
            return mp3.getId3v2Tag().getArtist();
        } else if (mp3.hasId3v1Tag()){
            return mp3.getId3v1Tag().getArtist();
        }
        return "";
    }

    public String getAlbumArtist(){
        if (mp3.hasId3v2Tag()){
            return mp3.getId3v2Tag().getAlbumArtist();
        }
        return "";
    }

    public String getAlbum(){
        if (mp3.hasId3v2Tag()){
            return mp3.getId3v2Tag().getAlbum();
        } else if (mp3.hasId3v1Tag()){
            return mp3.getId3v1Tag().getAlbum();
        }
        return "";
    }

    public String getTitle(){
        if (mp3.hasId3v2Tag()){
            return mp3.getId3v2Tag().getTitle();
        } else if (mp3.hasId3v1Tag()){
            return mp3.getId3v1Tag().getTitle();
        }
        return "";
    }

    public String getTrack(){
        if (mp3.hasId3v2Tag()){
            return mp3.getId3v2Tag().getTrack();
        } else if (mp3.hasId3v1Tag()){
            return mp3.getId3v1Tag().getTrack();
        }
        return "";
    }

    public boolean hasChanged(){
        return changed;
    }

    public String getLength(){
        int seconds = (int)mp3.getLengthInSeconds();
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return minutes + ":" + seconds;
    }

    public String getBitRate(){
        return mp3.getBitrate() + " kbps " + (mp3.isVbr() ? "(VBR)" : "(CBR)");
    }

    public String getSimpleFilename(){
        return simpleFilename;
    }

    public String getFilename(){
        return mp3.getFilename();
    }

    public void setArtist(String artist){
        if (!Objects.equals(artist, getArtist())) {
            ogArtist = getArtist();
            mp3.getId3v2Tag().setArtist(artist);
            changed = true;
        }
    }

    public void setAlbumArtist(String albumArtist){
        if (!Objects.equals(albumArtist, getAlbumArtist())) {
            ogAlbArtist = getAlbumArtist();
            mp3.getId3v2Tag().setAlbumArtist(albumArtist);
            changed = true;
        }
    }

    public void setTitle(String title){
        if (!Objects.equals(title, getTitle())) {
            ogTitle = getTitle();
            mp3.getId3v2Tag().setTitle(title);
            changed = true;
        }
    }

    public void setAlbum(String album){
        if (!Objects.equals(album, getAlbum())) {
            ogAlbum = getAlbum();
            mp3.getId3v2Tag().setAlbum(album);
            changed = true;
        }
    }

    public void setTrack(String track){
        if (!Objects.equals(track, getTrack())) {
            ogTrack = getTrack();
            mp3.getId3v2Tag().setTrack(track);
            changed = true;
        }
    }

    public void save() throws java.io.IOException, com.mpatric.mp3agic.NotSupportedException{
        String fileName = mp3.getFilename();
        mp3.save(fileName + ".new");
        File oldFile = new File(fileName);
        File newFile = new File(fileName + ".new");
        oldFile.delete();
        newFile.renameTo(oldFile);
        changed = false;
    }

    @Override
    public int compareTo(Mp3Object o) {
        try {
            //order by track number
            int thisTrack = Integer.parseInt(getTrack().split("/")[0]);
            int thatTrack = Integer.parseInt(o.getTrack().split("/")[0]);
            return (thisTrack > thatTrack) ? 1 : (thatTrack == thisTrack) ? 0 : -1;
        } catch (NumberFormatException e) {
            // one or other doesn't have a readable track number; compare on filename instead
            return getSimpleFilename().compareTo(o.getSimpleFilename());
        }
    }
}
