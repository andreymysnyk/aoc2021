package advert.day9;

import javax.xml.ws.Holder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Day9Task2 {

    private static void visit(int[][] map, boolean isFull, BiConsumer<Integer, Integer> visitor) {
        int d = isFull ? 0 : 1;

        for (int i = d; i < map.length - d; i++) {
            for (int j = d; j < map[0].length - d; j++) {
                visitor.accept(i, j);
            }
        }
    }

    private static int basin(int[][] map, int[][] v, int i, int j) {
        if (v[i][j] == 1) {
            return 0;
        }

        if (map[i][j] >= 9) {
            return 0;
        }

        v[i][j] = 1;

        return 1
                + basin(map, v, i - 1, j)
                + basin(map, v, i, j - 1)
                + basin(map, v, i + 1, j)
                + basin(map, v, i, j + 1);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day9/input1.txt"));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        int[][] map = new int[lines.size() + 2][lines.get(0).length() + 2];
        int[][] v = new int[lines.size() + 2][lines.get(0).length() + 2];
        visit(map, true, (i, j) -> map[i][j] = Integer.MAX_VALUE);
        visit(v, true, (i, j) -> v[i][j] = 0);

        for (int i = 0; i < lines.size(); i++) {
            String[] heights = lines.get(i).split("");

            for (int j = 0; j < heights.length; j++) {
                map[i + 1][j + 1] = Integer.parseInt(heights[j]);
            }
        }

        List<Integer> basins = new ArrayList<>();

        visit(map, false, (i, j) -> {
            if (map[i][j] >= map[i - 1][j]) return;
            if (map[i][j] >= map[i][j - 1]) return;
            if (map[i][j] >= map[i + 1][j]) return;
            if (map[i][j] >= map[i][j + 1]) return;

            basins.add(basin(map, v, i, j));
        });

        basins.sort(Integer::compareTo);

        int last = basins.size() - 1;
        int res = basins.get(last) * basins.get(last - 1) * basins.get(last - 2);

        System.out.println(res);
    }
}
