package advert.day15;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day15Task1 {

    private static class Point {
        public int x;
        public int y;
        public int risk;

        public static Point of(int x, int y, int risk) {
            Point p = new Point();
            p.x = x;
            p.y = y;
            p.risk = risk;

            return p;
        }
    }

    private static List<Point> visit(int[][] map, int[][] risks, Point p) {
        if (p.x < 0 || p.x >= map.length) {
            return new ArrayList<>();
        }

        if (p.y < 0 || p.y >= map[0].length) {
            return new ArrayList<>();
        }

        p.risk += map[p.x][p.y];

        if (p.risk >= risks[p.x][p.y]) {
            return new ArrayList<>();
        }

//        System.out.println(p.x + " " + p.y + " " + p.risk + " < " + risks[p.x][p.y]);

        risks[p.x][p.y] = p.risk;

        List<Point> points = new ArrayList<>();
        points.add(Point.of(p.x - 1, p.y, p.risk));
        points.add(Point.of(p.x, p.y - 1, p.risk));
        points.add(Point.of(p.x + 1, p.y, p.risk));
        points.add(Point.of(p.x, p.y + 1, p.risk));

        return points;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day15/input1.txt"));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        int width = lines.size();
        int height = lines.get(0).length();

        int[][] map = new int[width][height];
        int[][] risks = new int[width][height];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = lines.get(i).charAt(j) - '0';
                risks[i][j] = Integer.MAX_VALUE;
            }
        }

        LinkedList<Point> queue = new LinkedList<>();
        queue.add(Point.of(0, 0, -map[0][0]));

        while (!queue.isEmpty()) {
            queue.addAll(visit(map, risks, queue.pop()));
        }

        System.out.println(risks[width - 1][height - 1]);
    }
}
