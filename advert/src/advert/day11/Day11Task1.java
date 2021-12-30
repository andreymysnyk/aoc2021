package advert.day11;

import java.io.File;
import java.util.*;

public class Day11Task1 {

    private static void increment(int[][] map, int x, int y) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            return;
        }

        if (map[x][y] < 0) {
            return;
        }

        map[x][y]++;

        if (map[x][y] <= 9) {
            return;
        }

        map[x][y] = -1;
//        System.out.println(x + " " + y + " " + map[x][y]);

        increment(map, x - 1, y - 1);
        increment(map, x, y - 1);
        increment(map, x + 1, y - 1);

        increment(map, x - 1, y);
        increment(map, x + 1, y);

        increment(map, x - 1, y + 1);
        increment(map, x, y + 1);
        increment(map, x + 1, y + 1);
    }

    private static int performStep(int[][] map) {
        int width = map.length;
        int height = map[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                increment(map, i, j);
            }
        }

        int flashCnt = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] < 0) {
                    map[i][j] = 0;
                    flashCnt++;
                }
            }
        }

        return flashCnt;
    }

    private static void print(int[][] map) {
        int width = map.length;
        int height = map[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day11/input1.txt"));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        int width = lines.size();
        int height = lines.get(0).length();
        int[][] map = new int[width][height];

        for (int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i).split("");

            for (int j = 0; j < line.length; j++) {
                map[i][j] = Integer.parseInt(line[j]);
            }
        }

        print(map);

        for (int i = 0; i < 1000; i++) {
            System.out.println(i + 1);
            int result = performStep(map);

            if (result == width * height) {
                print(map);
                return;
            }
        }
    }
}
