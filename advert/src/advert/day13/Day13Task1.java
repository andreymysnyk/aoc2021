package advert.day13;

import java.io.File;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day13Task1 {

    private static class Point {
        public int x;
        public int y;

        public String toString() {
            return String.format("{%d, %d}", x, y);
        }

        public static Point of(int x, int y) {
            Point p = new Point();
            p.x = x;
            p.y = y;

            return p;
        }

        public static Point of(String point) {
            String[] parts = point.split(",");

            return of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Fold {
        private int pos;
        private boolean isX;

        public String toString() {
            return String.format("%s%d", isX ? "x" : "y", pos);
        }

        public static Fold of(String fold) {
            String[] parts = fold.split("=");

            Fold f = new Fold();
            f.pos = Integer.parseInt(parts[1]);
            f.isX = fold.contains("x=");

            return f;
        }
    }

    private static Point foldPoint(Fold f, Point p) {
        if (f.isX && p.x > f.pos) {
            return Point.of(f.pos - (p.x - f.pos), p.y);
        }

        if (!f.isX && p.y > f.pos) {
            return Point.of(p.x, f.pos - (p.y - f.pos));
        }

        return p;
    }

    private static void print(Set<Point> points) {
        int width = 0;
        int height = 0;

        for (Point p : points) {
            width = Math.max(p.x, width);
            height = Math.max(p.y, height);
        }

        String[][] map = new String[width + 1][height + 1];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = " ";
            }
        }

        for (Point p : points) {
            map[p.x][p.y] = "#";
        }

        for (int j = 0; j < map[0].length; j++) {
            for (int i = 0; i < map.length; i++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day13/input1.txt"));

        Set<Point> points = new HashSet<>();
        String line = scanner.nextLine();
        while (!line.isEmpty()) {
            points.add(Point.of(line));

            line = scanner.nextLine();
        }

        List<Fold> folds = new ArrayList<>();
        while (scanner.hasNextLine()) {
            folds.add(Fold.of(scanner.nextLine()));
        }

        System.out.println(points.size());

        for (Fold fold : folds) {
            Function<Point, Point> folder = (p) -> foldPoint(fold, p);

            points = points.stream()
                    .map(folder)
                    .collect(Collectors.toSet());

            System.out.println(points.size());
        }

        print(points);
    }
}
