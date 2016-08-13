package info.japandroid.mp3tool;

public class Main {

    public static void main(String[] args) {
        Mp3ModelInterface model = new Mp3Model();
        GUI myGUI = new GUI(model);
    }
}
