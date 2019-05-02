package ninja.oscaz.blockpuzzle.level;

import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LevelLoader {

    public Level loadLevel(File file) {
        try {
            return this.loadLevel(Files.readAllLines(Paths.get(file.getPath())));
        } catch (IOException e) {
            return null;
        }
    }

    public Level loadLevel(List<String> lines) {
        String name = lines.get(0);
        lines.remove(0);
        List<Block> blocks = new ArrayList<>();
        lines.forEach(line -> {
            if (line.equalsIgnoreCase("")) return;
            String[] splitLine = line.split(Pattern.quote("|"));
            if (splitLine.length != 3) return;
            int x, y;
            try {
                x = Integer.parseInt(splitLine[0]);
                y = Integer.parseInt(splitLine[1]);
            } catch (NumberFormatException e) {
                return;
            }
            blocks.add(new Block(BlockType.valueOf(splitLine[2]) , x, y));
        });
        return new Level(blocks);
    }

}
