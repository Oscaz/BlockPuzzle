package ninja.oscaz.blockpuzzle.file;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;

public class FileSaver {

    public static File chooseFile() {
        String osName = System.getProperty("os.name");
        String homeDir = System.getProperty("user.home");
        File selectedPath = null;
        if (osName.equals("Mac OS X")) {
            FileDialog fileDialog = new FileDialog(new Frame(), "Save", FileDialog.SAVE);
            fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".level"));
            fileDialog.setFile("EditedLevel.level");
            fileDialog.setDirectory(homeDir);
            fileDialog.setVisible(true);
            String filename = fileDialog.getDirectory() + fileDialog.getFile();
            selectedPath = new File(filename);
        } else {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(homeDir));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile();
            }
        }
        return selectedPath;
    }

}
