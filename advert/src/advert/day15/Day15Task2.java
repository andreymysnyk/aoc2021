package advert.day15;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day15Task2 {

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

    private static void fillValue(int[][] map, int[][] risks, int width, int height, int x, int y, int value) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int xi = x + width * i;
                int yj = y + height * j;

                map[xi][yj] = (value + (i + j - 1)) % 9 + 1;
                risks[xi][yj] = Integer.MAX_VALUE;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day15/input1.txt"));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        int width = lines.size();
        int height = lines.get(0).length();

        int[][] map = new int[width * 5][height * 5];
        int[][] risks = new int[width * 5][height * 5];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fillValue(map, risks, width, height, i, j, lines.get(i).charAt(j) - '0');
            }
        }

        LinkedList<Point> queue = new LinkedList<>();
        queue.add(Point.of(0, 0, -map[0][0]));

        while (!queue.isEmpty()) {
            queue.addAll(visit(map, risks, queue.pop()));
        }

        System.out.println(risks[risks.length - 1][risks[0].length - 1]);
    }
}
