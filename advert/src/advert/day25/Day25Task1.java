package advert.day25;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day25Task1 {

    private static boolean step(char[][] map) {
        boolean moved = false;

        int width = map.length;
        int height = map[0].length;

        for (int y = 0; y < height; y++) {
            boolean[] a = new boolean[width];

            for (int x = 0; x < width; x++) {
                if (map[x][y] != '>') {
                    continue;
                }

                int nextX = (x + 1) % width;
                if (map[nextX][y] != '.') {
                    continue;
                }

                a[x] = true;
            }

            for (int x = 0; x < width; x++) {
                if (!a[x]) {
                    continue;
                }

                int nextX = (x + 1) % width;
                map[nextX][y] = '>';
                map[x][y] = '.';
                moved = true;
            }
        }

        for (int x = 0; x < width; x++) {
            boolean[] a = new boolean[height];

            for (int y = 0; y < height; y++) {
                if (map[x][y] != 'v') {
                    continue;
                }

                int nextY = (y + 1) % height;
                if (map[x][nextY] != '.') {
                    continue;
                }

                a[y] = true;
            }

            for (int y = 0; y < height; y++) {
                if (!a[y]) {
                    continue;
                }

                int nextY = (y + 1) % height;
                map[x][nextY] = 'v';
                map[x][y] = '.';
                moved = true;
            }
        }

        return moved;
    }

    private static void print(char[][] map) {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                System.out.print(map[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day25/input1.txt"));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        int width = lines.get(0).length();
        int height = lines.size();
        char[][] map = new char[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[x][y] = lines.get(y).charAt(x);
            }
        }

        int cnt = 0;
        boolean moved = true;

        while (moved) {
            cnt++;
            moved = step(map);
        }

//        print(map);
        System.out.println(cnt);
    }
}
