package advert.day24;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Day24Task2 {

    private static final Map<String, BiConsumer<long[], Long>> UPDATERS = new ConcurrentHashMap<String, BiConsumer<long[], Long>>() {{
        put("x", (alu, value) -> { alu[0] = value; });
        put("y", (alu, value) -> { alu[1] = value; });
        put("z", (alu, value) -> { alu[2] = value; });
        put("w", (alu, value) -> { alu[3] = value; });
    }};

    private static long[] update(long[] alu, String reg, long value) {
        if (!UPDATERS.containsKey(reg)) {
            return alu;
        }

        UPDATERS.get(reg).accept(alu, value);

        return alu;
    }

    private static final Map<String, Function<long[], Long>> GETTERS = new ConcurrentHashMap<String, Function<long[], Long>>() {{
        put("x", (alu) -> alu[0] );
        put("y", (alu) -> alu[1] );
        put("z", (alu) -> alu[2] );
        put("w", (alu) -> alu[3] );
    }};

    private static Long get(long[] alu, String reg) {
        if (!GETTERS.containsKey(reg)) {
            return Long.parseLong(reg);
        }

        return GETTERS.get(reg).apply(alu);
    }

    private static final Map<String, BiFunction<long[], String[], long[]>> COMMANDS = new ConcurrentHashMap<String, BiFunction<long[], String[], long[]>>() {{
        put("inp", (alu, args) -> {
            return update(alu, args[0], Integer.parseInt(args[1]));
        });
        put("add", (alu, args) -> {
            long a = Day24Task2.get(alu, args[0]);
            long b = Day24Task2.get(alu, args[1]);

            return update(alu, args[0], a + b);
        });
        put("mul", (alu, args) -> {
            long a = Day24Task2.get(alu, args[0]);
            long b = Day24Task2.get(alu, args[1]);

            return update(alu, args[0], a * b);
        });
        put("div", (alu, args) -> {
            long a = Day24Task2.get(alu, args[0]);
            long b = Day24Task2.get(alu, args[1]);

            return update(alu, args[0], a / b);
        });
        put("mod", (alu, args) -> {
            long a = Day24Task2.get(alu, args[0]);
            long b = Day24Task2.get(alu, args[1]);

            return update(alu, args[0], a % b);
        });
        put("eql", (alu, args) -> {
            long a = Day24Task2.get(alu, args[0]);
            long b = Day24Task2.get(alu, args[1]);

            return update(alu, args[0], a == b ? 1 : 0);
        });
    }};

    private static long[] process(long[] alu, String cmd, String[] args) {
        if (!COMMANDS.containsKey(cmd)) {
            return alu;
        }

        return COMMANDS.get(cmd).apply(alu, args);
    }

    private static long[] clone(long[] alu, int digit) {
        return new long[] {
                alu[0],
                alu[1],
                alu[2],
                digit,
                alu[4] * 10 + digit
        };
    }

    private static void run(long[] alu, List<String[]> cmds) {
        for (String[] cmd : cmds) {
            process(alu, cmd[0], new String[] { cmd[1], cmd[2] });
        }
    }

    private static final Set<Long> visited = ConcurrentHashMap.newKeySet();

    private static boolean find(long[] alu, int level, List<List<String[]>> cmdsList) {
        long key = alu[2] * 100 + level;

        if (level == 14) {
            if (alu[2] == 0) {
                System.out.println(Arrays.toString(alu));
                return true;
            }

            return false;
        }

        if (visited.contains(key)) {
            return false;
        }

        boolean result = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .anyMatch((digit) -> {
                    long[] a = clone(alu, digit);

                    run(a, cmdsList.get(level));

                    return find(a, level + 1, cmdsList);
                });

        visited.add(key);

        return result;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day24/input1.txt"));

        List<List<String[]>> cmdsList = new ArrayList<>();

        List<String[]> cmds = null;
        while (scanner.hasNextLine()) {
            String[] cmd = scanner.nextLine().split(" ");

            if ("inp".equals(cmd[0])) {
                if (cmds != null) {
                    cmdsList.add(cmds);
                }

                cmds = new ArrayList<>();
            } else {
                cmds.add(cmd);
            }
        }
        cmdsList.add(cmds);

        System.out.println(find(new long[5], 0, cmdsList));
    }
}
