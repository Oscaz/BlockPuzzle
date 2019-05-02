package ninja.oscaz.blockpuzzle.level;

import java.io.*;
import java.util.List;

public class LevelSaver {

    public void saveLevel(List<String> levelText, File file) {
        FileWriter fileWriter = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                throw new RuntimeException("IOException in save level!", e);
            }
        } else {
            file.delete();
            this.saveLevel(levelText, file);
            return;
        }
        FileWriter finalFileWriter = fileWriter;
        levelText.forEach(line -> {
            try {
                finalFileWriter.write(line + "\n");
            } catch (IOException e) {
                throw new RuntimeException("IOException in save level!", e);
            }
        });
    }

}
