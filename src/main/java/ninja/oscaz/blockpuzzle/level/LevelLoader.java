package ninja.oscaz.blockpuzzle.level;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;
import ninja.oscaz.blockpuzzle.error.GameError;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class LevelLoader {

    public static void loadLevel(File file) {
        try {
            loadLevel(Files.readAllLines(Paths.get(file.getPath())));
        } catch (IOException e) {
            GameError.displayGameError("Error loading level!");
        }
    }

    public static void loadLevel(List<String> lines) {
        String name = lines.get(0);
        System.out.println(name);
        lines.remove(0);
        lines.forEach(System.out::println);
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
                System.out.println("exception caught in levelloader");
                e.printStackTrace();
            }
        });
        BlockPuzzle.getInstance().getLevels().add(new Level(name, blocks));
    }

    public static void loadLevel(InputStream inputStream) {
        try {
            String string = LevelLoader.convert(inputStream, Charset.defaultCharset());
            System.out.println(string);
            if (string == null) {
                System.out.println("stringnull");
                GameError.displayGameError("Error loading level!");
                return;
            }
            List<String> lines = new ArrayList<>(Arrays.asList(string.split(Pattern.quote("$"))));
            lines.forEach(System.out::println);
            LevelLoader.loadLevel(lines);
        } catch (Exception e) {
            System.out.println("exception caught in loadlevel");
            e.printStackTrace();
        }
    }

    private static String convert(InputStream inputStream, Charset charset) {
        try {

            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

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
            System.out.println("exception in convert");
            e.printStackTrace();
        }
        return null;
    }

}
