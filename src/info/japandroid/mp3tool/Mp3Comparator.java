package info.japandroid.mp3tool;

import com.mpatric.mp3agic.Mp3File;

import java.util.Comparator;

public class Mp3Comparator implements Comparator<Mp3File> {
    @Override
    public int compare(Mp3File o1, Mp3File o2) {
        int track1, track2;
        try {
            track1 = Integer.parseInt(o1.getId3v2Tag().getTrack());
        } catch (NumberFormatException e) {
            track1 = 0;
        }
        try {
            track2 = Integer.parseInt(o2.getId3v2Tag().getTrack());
        } catch (NumberFormatException e) {
            track2 = 0;
        }
        if (track1 < track2) {
            return -1;
        } else if (track2 > track1) {
            return 1;
        }
        return 0;
    }
}
