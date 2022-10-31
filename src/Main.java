import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static final String BASE_PATH = "C:\\Games\\savegames\\";

    public static GameProgress createGameProgress() {
        return new GameProgress(
                new Random().nextInt(100), // health
                new Random().nextInt(50), // weapon
                new Random().nextInt(100), // lBASE_PATHevel
                new Random().nextDouble(1000) // distance
        );
    }

    public static void saveGameProgress(GameProgress gameProgress, String filename) {
        try (FileOutputStream fos = new FileOutputStream(BASE_PATH + (filename + ".dat"));
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static File[] listDirectoryFiles(String directoryPath) {
        File saveGamesFolder = new File(directoryPath);
        File[] saveGamesFiles = saveGamesFolder.listFiles();
        if (saveGamesFiles == null) {
            System.out.println("Directory with saved games doesn't exist.");
            return null;
        }
        return saveGamesFiles;
    }

    public static void zipFiles(String zipFilePath, List<String> filesPaths) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : filesPaths) {
                // put file into the archive
                ZipEntry zipEntry = new ZipEntry(filePath);
                zout.putNextEntry(zipEntry);

                // write some data into current file
                try (FileInputStream currentFile = new FileInputStream(BASE_PATH + filePath)) {
                    zout.write(currentFile.readAllBytes());
                } catch (IOException e) {
                    System.out.println("Unable to write data: " + e.getMessage());
                }
            }
            zout.closeEntry();
        } catch (Exception e) {
            System.out.println("Unable to ZIP: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GameProgress gameProgress1 = createGameProgress();
        GameProgress gameProgress2 = createGameProgress();
        GameProgress gameProgress3 = createGameProgress();
        saveGameProgress(gameProgress1, "save1");
        saveGameProgress(gameProgress2, "save2");
        saveGameProgress(gameProgress3, "save3");

        File[] saveGamesFiles = listDirectoryFiles(BASE_PATH);
        if (saveGamesFiles == null) return;

        ArrayList<String> saveGamesFilesPaths = new ArrayList<>();
        for (File file : saveGamesFiles) saveGamesFilesPaths.add(file.getName());

        zipFiles(BASE_PATH + "\\zipped.zip", saveGamesFilesPaths);

        for (String saveGameFilePath : saveGamesFilesPaths) {
            File saveGameFile = new File(BASE_PATH + saveGameFilePath);
            if (saveGameFile.delete())
                System.out.println("Deleted " + saveGameFilePath);
            else
                System.out.println("Unable to delete " + saveGameFilePath);
        }
    }
}
