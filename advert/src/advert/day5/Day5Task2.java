package advert.day5;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day5Task2 {

    private static class Point {
        public int x;
        public int y;

        public Point(String point) {
            String[] elements = point.split(",");

            this.x = Integer.parseInt(elements[0]);
            this.y = Integer.parseInt(elements[1]);
        }
    }

    private static class Line {
        public Point point1;
        public Point point2;

        public Line(Point point1, Point point2) {
            this.point1 = point1;
            this.point2 = point2;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day5/input1.txt"));

        List<Line> lines = new ArrayList<>();
        int width = 1;
        int height = 1;

        while (scanner.hasNextLine()) {
            String[] row = scanner.nextLine().split(" ");

            Line line = new Line(new Point(row[0]), new Point(row[2]));

            lines.add(line);

            width = Math.max(width, Math.max(line.point1.x, line.point2.x));
            height = Math.max(height, Math.max(line.point1.y, line.point2.y));
        }

        int[][] map = new int[width + 1][height + 1];

        for (Line line : lines) {
            int dx = Integer.compare(line.point2.x, line.point1.x);
            int dy = Integer.compare(line.point2.y, line.point1.y);
            int len = Math.max(Math.abs(line.point2.x - line.point1.x), Math.abs(line.point2.y - line.point1.y));

            int x = line.point1.x;
            int y = line.point1.y;

            for (int i = 0; i <= len; i++) {
                map[x][y] += 1;

                x += dx;
                y += dy;
            }
        }

        int cnt = 0;
        for (int j = 0; j < map[0].length; j++) {
            for (int i = 0; i < map.length; i++) {
                System.out.printf("%s", map[i][j] == 0 ? "." : map[i][j]);

                if (map[i][j] > 1) {
                    cnt++;
                }
            }

            System.out.println();
        }

        System.out.println(cnt);
    }
}
