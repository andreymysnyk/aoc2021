package advert.day22;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day22Task1 {

    private static int[] range(String range) {
        String[] parts = range.split("\\.\\.");
        int start = Math.max(Integer.parseInt(parts[0]) + 50, 0);
        int end = Math.min(Integer.parseInt(parts[1]) + 50, 100);

        return IntStream.rangeClosed(start, end).toArray();
    }

    private static void set(boolean[][][] map, String line) {
        String[] parts = line.split(",");

        boolean on = "on".equals(parts[0].split(" ")[0]);

        int[] xs = range(parts[0].split("=")[1]);
        int[] ys = range(parts[1].split("=")[1]);
        int[] zs = range(parts[2].split("=")[1]);

        for (int x : xs) {
            for (int y : ys) {
                for (int z : zs) {
                    map[x][y][z] = on;
                }
            }
        }

//        System.out.println(line);
//        System.out.println("\t" + on + " " + Arrays.toString(xs) + " " + Arrays.toString(ys) + " " + Arrays.toString(zs));
    }

    private static int count(boolean[][][] map) {
        int cnt = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                for (int k = 0; k < map[0][0].length; k++) {
                    cnt += map[i][j][k] ? 1 : 0;
                }
            }
        }

        return cnt;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day22/input1.txt"));

        boolean[][][] map = new boolean[101][101][101];

        while (scanner.hasNextLine()) {
            set(map, scanner.nextLine());
        }

        System.out.println(count(map));
    }
}
