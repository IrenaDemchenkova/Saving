import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Main {
    public static final String BASE_PATH = "C:\\Games\\savegames\\";

    public static GameProgress createGameProgress() {
        GameProgress gameProgress = new GameProgress(
                new Random().nextInt(100), //health
                new Random().nextInt(50), //weapon
                new Random().nextInt(100), // level
                new Random().nextDouble(1000) // distance
        );
        return gameProgress;
    }

    public static void saveGameProgress(GameProgress gameProgress, String filename) {
        try (FileOutputStream fos = new FileOutputStream(BASE_PATH + (filename + ".dat"));
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

        public static void main(String[] args) {
        GameProgress gameProgress1 = createGameProgress();
        GameProgress gameProgress2 = createGameProgress();
        GameProgress gameProgress3 = createGameProgress();
        saveGameProgress(gameProgress1, "save1");
        saveGameProgress(gameProgress2, "save2");
        saveGameProgress(gameProgress3, "save3");
    }
}
