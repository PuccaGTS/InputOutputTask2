import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        List<String> listOfSaveFiles = new ArrayList<>();

        GameProgress gameProgress1 = new GameProgress(200, 4, 10, 10.0);
        GameProgress gameProgress2 = new GameProgress(150, 3, 8, 7.9);
        GameProgress gameProgress3 = new GameProgress(120, 2, 6, 4.5);


        saveGame("D:/Games/Games/savegames/save1.dat", gameProgress1, listOfSaveFiles);
        saveGame("D:/Games/Games/savegames/save2.dat", gameProgress2, listOfSaveFiles);
        saveGame("D:/Games/Games/savegames/save3.dat", gameProgress3, listOfSaveFiles);


        zipFiles("D:/Games/Games/savegames/saveFiles.zip", listOfSaveFiles);

        deleteFileAfterZiping(listOfSaveFiles);
    }


    public static void saveGame(String path, GameProgress gameProgress, List<String> files) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
            files.add(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf("/") + 1));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFileAfterZiping(List<String> files) {
        for (String filePath : files) {
            File file = new File(filePath);
            file.delete();
        }
    }
}
