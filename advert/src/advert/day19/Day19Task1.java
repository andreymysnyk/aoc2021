package advert.day19;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day19Task1 {

    private static class Point {
        public int x;
        public int y;
        public int z;

        public static Point of(String line) {
            String[] parts = line.split(",");

            Point p = new Point();
            p.x = Integer.parseInt(parts[0]);
            p.y = Integer.parseInt(parts[1]);
            p.z = Integer.parseInt(parts[2]);

            return p;
        }

        public static Point of(int x, int y, int z) {
            Point p = new Point();
            p.x = x;
            p.y = y;
            p.z = z;

            return p;
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + ", " + z + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    private static final List<Function<Point, Point>> rotations = new ArrayList<Function<Point, Point>>() {{
        add((p) -> Point.of( p.x,  p.y,  p.z));
        add((p) -> Point.of( p.x, -p.y,  p.z));
        add((p) -> Point.of( p.x, -p.y, -p.z));
        add((p) -> Point.of( p.x,  p.y, -p.z));
        add((p) -> Point.of(-p.x,  p.y,  p.z));
        add((p) -> Point.of(-p.x, -p.y,  p.z));
        add((p) -> Point.of(-p.x, -p.y, -p.z));
        add((p) -> Point.of(-p.x,  p.y, -p.z));

        add((p) -> Point.of( p.y,  p.x,  p.z));
        add((p) -> Point.of( p.y, -p.x,  p.z));
        add((p) -> Point.of( p.y, -p.x, -p.z));
        add((p) -> Point.of( p.y,  p.x, -p.z));
        add((p) -> Point.of(-p.y,  p.x,  p.z));
        add((p) -> Point.of(-p.y, -p.x,  p.z));
        add((p) -> Point.of(-p.y, -p.x, -p.z));
        add((p) -> Point.of(-p.y,  p.x, -p.z));

        add((p) -> Point.of( p.z,  p.x,  p.y));
        add((p) -> Point.of( p.z, -p.x,  p.y));
        add((p) -> Point.of( p.z, -p.x, -p.y));
        add((p) -> Point.of( p.z,  p.x, -p.y));
        add((p) -> Point.of(-p.z,  p.x,  p.y));
        add((p) -> Point.of(-p.z, -p.x,  p.y));
        add((p) -> Point.of(-p.z, -p.x, -p.y));
        add((p) -> Point.of(-p.z,  p.x, -p.y));

        add((p) -> Point.of( p.z,  p.y,  p.x));
        add((p) -> Point.of( p.z, -p.y,  p.x));
        add((p) -> Point.of( p.z, -p.y, -p.x));
        add((p) -> Point.of( p.z,  p.y, -p.x));
        add((p) -> Point.of(-p.z,  p.y,  p.x));
        add((p) -> Point.of(-p.z, -p.y,  p.x));
        add((p) -> Point.of(-p.z, -p.y, -p.x));
        add((p) -> Point.of(-p.z,  p.y, -p.x));

        add((p) -> Point.of( p.x,  p.z,  p.y));
        add((p) -> Point.of( p.x, -p.z,  p.y));
        add((p) -> Point.of( p.x, -p.z, -p.y));
        add((p) -> Point.of( p.x,  p.z, -p.y));
        add((p) -> Point.of(-p.x,  p.z,  p.y));
        add((p) -> Point.of(-p.x, -p.z,  p.y));
        add((p) -> Point.of(-p.x, -p.z, -p.y));
        add((p) -> Point.of(-p.x,  p.z, -p.y));

        add((p) -> Point.of( p.y,  p.z,  p.x));
        add((p) -> Point.of( p.y, -p.z,  p.x));
        add((p) -> Point.of( p.y, -p.z, -p.x));
        add((p) -> Point.of( p.y,  p.z, -p.x));
        add((p) -> Point.of(-p.y,  p.z,  p.x));
        add((p) -> Point.of(-p.y, -p.z,  p.x));
        add((p) -> Point.of(-p.y, -p.z, -p.x));
        add((p) -> Point.of(-p.y,  p.z, -p.x));
    }};

    private static Point translate(Point main, Point a) {
        return Point.of(
                a.x - main.x,
                a.y - main.y,
                a.z - main.z
        );
    }

    private static class Line {
        public Point a;
        public Point b;
        public String value;

        public static Line of(Point a, Point b) {
            int[] d = new int[] {
                    Math.abs(a.x - b.x),
                    Math.abs(a.y - b.y),
                    Math.abs(a.z - b.z)
            };

            Line line = new Line();
            line.a = a;
            line.b = b;
            line.value = String.format("%s-%s-%s", d[0], d[1], d[2]);

            return line;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return value.equals(line.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "{" + "" + a + " - " + b + ", " + value + "}";
        }
    }

    private static Set<Line> getLines(List<Point> points) {
        Set<Line> lines = new HashSet<>();

        for (int i = 0; i < points.size(); i++) {
            Point a = points.get(i);

            for (int j = i + 1; j < points.size(); j++) {
                Point b = points.get(j);

                Line l = Line.of(a, b);
                if (lines.contains(l)) {
                    System.out.println("shit.. :(");
                }

                lines.add(l);
            }
        }

        return lines;
    }

    private static class Match {
        public Function<Point, Point> rotation;
        public Point main;
        public Set<Point> uniquePoints;
        public int parent;

        public static Match of(Function<Point, Point> rotation, Point main, Set<Point> uniquePoints) {
            Match match = new Match();
            match.rotation = rotation;
            match.main = main;
            match.uniquePoints = uniquePoints;

            return match;
        }
    }

    private static Match getUniquePoints(List<Point> reportA, List<Point> reportB) {
        Set<Line> linesA = getLines(reportA);

        for (Function<Point, Point> rotation : rotations) {
            List<Point> points = reportB.stream()
                    .map(rotation)
                    .collect(Collectors.toList());
            Set<Line> linesB = getLines(points);

            linesB.retainAll(linesA);

            for (Line lineB : linesB) {
                Line lineA = linesA.stream()
                        .filter((l) -> l.equals(lineB))
                        .findFirst()
                        .get();

                Set<Point> pB = points.stream()
                        .map((p) -> translate(lineB.a, p))
                        .map((p) -> translate(p, lineA.a))
                        .collect(Collectors.toSet());

                Set<Point> pA = new HashSet<>(reportA);
                pA.retainAll(pB);

                if (pA.size() >= 12) {
                    pB.removeAll(pA);
                    return Match.of(rotation, lineA.a, pB);
                }
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day19/input1.txt"));

        List<List<Point>> reportList = new ArrayList<>();
        String line;
        while(scanner.hasNextLine()) {
            List<Point> report = new ArrayList<>();

            scanner.nextLine();
            while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
                report.add(Point.of(line));
            }
            reportList.add(report);
        }

        List<Point> uniquePoints = new ArrayList<>(reportList.get(0));
        Set<Integer> visited = new HashSet<>();
        while (visited.size() != reportList.size()) {
            for (int j = 0; j < reportList.size(); j++) {
                if (visited.contains(j)) {
                    continue;
                }

                Match reportJ = getUniquePoints(uniquePoints, reportList.get(j));

                System.out.println(j + " " + reportJ);

                if (reportJ == null) {
                    continue;
                }

                System.out.println(j + " " + reportJ.uniquePoints.size());

                uniquePoints.addAll(reportJ.uniquePoints);

                visited.add(j);
            }
        }

        System.out.println(visited.size() + " " + reportList.size());
        System.out.println(uniquePoints.size());
    }
}
