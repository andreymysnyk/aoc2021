package advert.day23;

import java.io.File;
import java.util.*;

public class Day23Task2 {

    public static class Point {
        public int x;
        public int y;

        public static Point of(int x, int y) {
            Point p = new Point();
            p.x = x;
            p.y = y;

            return p;
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + "}";
        }
    }

    private static class State {
        public char[][] map;
        public int score;
        public List<String> history;

        public static State of(char[][] map, int score, List<String> history) {
            State state = new State();
            state.map = map;
            state.score = score;
            state.history = history;

            return state;
        }
    }

    private static void print(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static final List<Point> HALLWAY = new ArrayList<Point>() {{
        add(Point.of(1, 1));
        add(Point.of(1, 2));
        add(Point.of(1, 4));
        add(Point.of(1, 6));
        add(Point.of(1, 8));
        add(Point.of(1, 10));
        add(Point.of(1, 11));
    }};

    private static boolean isReachable(char[][] map, Point a, Point b) {
        int xs = Math.min(a.x, b.x);
        int xe = Math.max(a.x, b.x);
        int ys = Math.min(a.y, b.y);
        int ye = Math.max(a.y, b.y);

        for (int y = ys; y <= ye; y++) {
            if (map[1][y] != '.') {
                return false;
            }
        }

        for (int x = xs; x <= xe; x++) {
            if (map[x][a.y] != '.') {
                return false;
            }
        }

        return true;
    }

    private static final int[] HOME = new int[] { 3, 5, 7, 9 };

    private static boolean isHomeAvailable(char[][] map, char home) {
        int y = HOME[home - 'A'];

        return (map[2][y] == '.' || map[2][y] == home)
                && (map[3][y] == '.' || map[3][y] == home)
                && (map[4][y] == '.' || map[4][y] == home)
                && (map[5][y] == '.' || map[5][y] == home);
    }

    private static boolean isHomeFull(char[][] map, char home) {
        int y = HOME[home - 'A'];

        return map[2][y] == home && map[3][y] == home
                && map[4][y] == home && map[5][y] == home;
    }

    private static Point getHomeAvailable(char[][] map, char home) {
        int y = HOME[home - 'A'];

        if (map[5][y] == '.') {
            return Point.of(5, y);
        }

        if (map[4][y] == '.') {
            return Point.of(4, y);
        }

        if (map[3][y] == '.') {
            return Point.of(3, y);
        }

        return Point.of(2, y);
    }

    private static List<Point> steps(char[][] map, int x, int y) {
        // go out of the room
        if (x > 1) {
            if (map[x - 1][y] != '.') {
                return new ArrayList<>();
            }

            // is at home already?
            if (isHomeAvailable(map, map[x][y]) && y == HOME[map[x][y] - 'A']) {
                return new ArrayList<>();
            }

            // go out
            List<Point> steps = new ArrayList<>();

            Point cur = Point.of(x - 1, y);

            // got out and straight home
            if (isHomeAvailable(map, map[x][y]) && isReachable(map, cur, Point.of(1, HOME[map[x][y] - 'A']))) {
                steps.add(getHomeAvailable(map, map[x][y]));
                return steps;
            }

            HALLWAY.forEach((move) -> {
                if (isReachable(map, cur, move)) {
                    steps.add(move);
                }
            });

            return steps;
        }

        // is home available?
        if (!isHomeAvailable(map, map[x][y])) {
            return new ArrayList<>();
        }

        int homeY = HOME[map[x][y] - 'A'];
        Point cur = y > homeY
                ? Point.of(x, y - 1)
                : Point.of(x, y + 1);
        if (!isReachable(map, cur, Point.of(1, homeY))) {
            return new ArrayList<>();
        }

        // come back home
        List<Point> steps = new ArrayList<>();

        steps.add(getHomeAvailable(map, map[x][y]));

        return steps;
    }

    private static int distance(int x1, int y1, int x2, int y2) {
        if (x1 == 1 || x2 == 1) {
            return Math.abs(x2 - x1) + Math.abs(y2 - y1);
        }

        return Math.abs(1 - x1) + Math.abs(1 - x2) + Math.abs(y2 - y1);
    }

    private static final int[] weight = new int[] { 1, 10, 100, 1000 };

    private static char[][] clone(char[][] map) {
        char[][] cloned = new char[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                cloned[i][j] = map[i][j];
            }
        }

        return cloned;
    }

    private static final Map<String, Integer> cache = new HashMap<>();

    private static final List<State> results = new ArrayList<>();

    private static List<State> step(State state) {
        char[][] map = state.map;
        int score = state.score;

        String key = String.format(
                "%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s-%d",
                map[1][1],map[1][2],map[1][4],map[1][6],map[1][8],map[1][10],map[1][11],
                map[2][3],map[3][3],map[4][3],map[5][3],
                map[2][5],map[3][5],map[4][5],map[5][5],
                map[2][7],map[3][7],map[4][7],map[5][7],
                map[2][9],map[3][9],map[4][9],map[5][9],
                score
        );

        if (cache.containsKey(key)) {
//            return cache.get(key);
            return new ArrayList<>();
        }

        boolean isHomeFull = isHomeFull(map, 'A') && isHomeFull(map, 'B')
                && isHomeFull(map, 'C') && isHomeFull(map, 'D');
        if (isHomeFull) {
            results.add(state);

            System.out.println(score);
//            print(map);

//            System.out.println(state.history);

            cache.put(key, 0);
            return new ArrayList<>();
        }

        if (score > 50000) {
            cache.put(key, 1);
            return new ArrayList<>();
        }

        int result = Integer.MAX_VALUE;

//        print(map);

        List<State> states = new ArrayList<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == '#' || map[i][j] == '.') {
                    continue;
                }

                List<Point> steps = steps(map, i, j);

                for (Point p : steps) {
                    char[][] newMap = clone(map);
                    int stepScore = weight[map[i][j] - 'A'] * distance(i, j, p.x, p.y);
                    int newScore = score + stepScore;

                    newMap[p.x][p.y] = map[i][j];
                    newMap[i][j] = '.';

                    List<String> history = new ArrayList<>(state.history);
                    history.add(map[i][j] + " " + p + " (" + stepScore + ")");

                    states.add(State.of(newMap, newScore, history));
                }
            }
        }

        cache.put(key, result);

        return states;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day23/input2.txt"));

        char[][] map = new char[7][13];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = '#';
            }
        }

        for (int i = 0; i < map.length; i++) {
            String line = scanner.nextLine();

            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                if (c == ' ') {
                    c = '#';
                }

                map[i][j] = c;
            }
        }

        LinkedList<State> queue = new LinkedList<>();
        queue.add(State.of(map, 0, new ArrayList<>()));

        while (!queue.isEmpty()) {
            queue.addAll(step(queue.pop()));
        }

        System.out.println(results.size());

        State min = results.get(0);
        for (State state : results) {
            if (state.score < min.score) {
                min = state;
            }
        }

        System.out.println(min.score);
        System.out.println(String.join("\n", min.history));
    }
}
