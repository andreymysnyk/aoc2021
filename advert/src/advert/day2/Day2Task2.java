package advert.day2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Day2Task2 {

    private static class Position {
        public int aim = 0;
        public int horizontal = 0;
        public int depth = 0;

        public int multiple() {
            return horizontal * depth;
        }
    }

    private static void forward(Position pos, int step) {
        pos.horizontal += step;
        pos.depth += pos.aim * step;
    }

    private static void down(Position pos, int step) {
        pos.aim += step;
    }

    private static void up(Position pos, int step) {
        pos.aim -= step;
    }

    public static void main(String[] args) throws Exception {
        Map<String, BiConsumer<Position, Integer>> control = new HashMap<String, BiConsumer<Position, Integer>>() {{
            put("forward", Day2Task2::forward);
            put("up", Day2Task2::up);
            put("down", Day2Task2::down);
        }};

        Scanner scanner = new Scanner(new File("src/advert/input2-2.txt"));

        Position pos = new Position();
        while (scanner.hasNext()) {
            String command = scanner.next();
            int step = scanner.nextInt();

            control.get(command).accept(pos, step);
        }

        System.out.println(pos.multiple());
    }
}
