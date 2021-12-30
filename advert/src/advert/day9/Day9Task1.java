package advert.day9;

import javax.xml.ws.Holder;
import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day9Task1 {

    private static void visit(int[][] map, boolean isFull, BiConsumer<Integer, Integer> visitor) {
        int d = isFull ? 0 : 1;

        for (int i = d; i < map.length - d; i++) {
            for (int j = d; j < map[0].length - d; j++) {
                visitor.accept(i, j);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day9/input1.txt"));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        int[][] map = new int[lines.size() + 2][lines.get(0).length() + 2];
        visit(map, true, (i, j) -> map[i][j] = Integer.MAX_VALUE);

        for (int i = 0; i < lines.size(); i++) {
            String[] heights = lines.get(i).split("");

            for (int j = 0; j < heights.length; j++) {
                map[i + 1][j + 1] = Integer.parseInt(heights[j]);
            }
        }

        Holder<Integer> cnt = new Holder<>(0);

        visit(map, false, (i, j) -> {
            if (map[i][j] >= map[i - 1][j]) return;
            if (map[i][j] >= map[i][j - 1]) return;
            if (map[i][j] >= map[i + 1][j]) return;
            if (map[i][j] >= map[i][j + 1]) return;

            cnt.value += 1 + map[i][j];
        });

        System.out.println(cnt.value);
    }
}
