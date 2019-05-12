package ninja.oscaz.blockpuzzle.file;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;

public class FileChooser {

    public static File chooseFile() {
        String osName = System.getProperty("os.name");
        String homeDir = System.getProperty("user.home");
        File selectedPath = null;
        if (osName.equals("Mac OS X")) {
            System.setProperty("apple.awt.fileDialogForDirectories", "false");
            FileDialog fd = new FileDialog(new Frame(), "Choose a file", FileDialog.LOAD);
            fd.setFilenameFilter((dir, name) -> name.endsWith(".level"));
            fd.setDirectory(homeDir);
            fd.setVisible(true);
            String filename = fd.getDirectory() + fd.getFile();
            selectedPath = new File(filename);
            System.setProperty("apple.awt.fileDialogForDirectories", "true");
        } else {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setCurrentDirectory(new File(homeDir));
            fc.setAcceptAllFileFilterUsed(false);
            fc.showOpenDialog(null);
            selectedPath = fc.getSelectedFile();
        }
        return selectedPath;
    }

}
