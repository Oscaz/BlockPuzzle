package ninja.oscaz.blockpuzzle.level;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;
import ninja.oscaz.blockpuzzle.error.GameError;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class LevelLoader {

    public static Level returnLevel(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            String string = LevelLoader.convert(inputStream, Charset.defaultCharset());
            if (string == null) {
                return null;
            }
            List<String> lines = new ArrayList<>(Arrays.asList(string.split(Pattern.quote("$"))));
            return returnLevel(lines);
        } catch (IOException e) {
            return null;
        }
    }

    public static void loadLevel(File file) {
        try {
            loadLevel(new FileInputStream(file));
        } catch (IOException e) {
            GameError.displayGameError("Error loading level!");
        }
    }

    private static Level returnLevel(List<String> lines) {
        String name = lines.get(0);
        lines.remove(0);
        List<Block> blocks = new ArrayList<>();
        lines.forEach(line -> {
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return new Level(name, blocks);
    }

    public static void loadLevel(List<String> lines) {
        BlockPuzzle.getInstance().getLevels().add(returnLevel(lines));
    }

    public static void loadLevel(InputStream inputStream) {
        List<String> lines = new ArrayList<>();
        lines.addAll(Arrays.asList(convert(inputStream, Charset.defaultCharset()).split(Pattern.quote("$"))));
        loadLevel(lines);
    }

    private static String convert(InputStream inputStream, Charset charset) {
        try {

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            try {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                }
            } catch (IOException e) {
                return null;
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
