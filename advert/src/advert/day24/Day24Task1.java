package advert.day24;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day24Task1 {

    private static final Map<String, BiConsumer<long[], Long>> UPDATERS = new HashMap<String, BiConsumer<long[], Long>>() {{
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

    private static final Map<String, Function<long[], Long>> GETTERS = new HashMap<String, Function<long[], Long>>() {{
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

    private static class ALU2 {
        public int x = 0;
        public int y = 0;
        public int z = 0;
        public int w = 0;
        public int level = 0;

        public static ALU2 of(ALU2 alu) {
            ALU2 clonedAlu = new ALU2();
            clonedAlu.x = alu.x;
            clonedAlu.y = alu.y;
            clonedAlu.z = alu.z;
            clonedAlu.w = alu.w;
            clonedAlu.level = alu.level + 1;

            return clonedAlu;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ALU2 alu = (ALU2) o;
            return /*x == alu.x && y == alu.y && */z == alu.z && w == alu.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(/*x, y, */z, w);
        }

        @Override
        public String toString() {
            return "{ x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "}";
        }
    }

    private static final Map<String, BiFunction<long[], String[], long[]>> COMMANDS = new HashMap<String, BiFunction<long[], String[], long[]>>() {{
        put("inp", (alu, args) -> {
            return update(alu, args[0], Integer.parseInt(args[1]));
        });
        put("add", (alu, args) -> {
            long a = Day24Task1.get(alu, args[0]);
            long b = Day24Task1.get(alu, args[1]);

            return update(alu, args[0], a + b);
        });
        put("mul", (alu, args) -> {
            long a = Day24Task1.get(alu, args[0]);
            long b = Day24Task1.get(alu, args[1]);

            return update(alu, args[0], a * b);
        });
        put("div", (alu, args) -> {
            long a = Day24Task1.get(alu, args[0]);
            long b = Day24Task1.get(alu, args[1]);

            return update(alu, args[0], a / b);
        });
        put("mod", (alu, args) -> {
            long a = Day24Task1.get(alu, args[0]);
            long b = Day24Task1.get(alu, args[1]);

            return update(alu, args[0], a % b);
        });
        put("eql", (alu, args) -> {
            long a = Day24Task1.get(alu, args[0]);
            long b = Day24Task1.get(alu, args[1]);

            return update(alu, args[0], a == b ? 1 : 0);
        });
    }};

    private static long[] process(long[] alu, String cmd, String[] args) {
        if (!COMMANDS.containsKey(cmd)) {
            return alu;
        }

        return COMMANDS.get(cmd).apply(alu, args);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day24/input1.txt"));

        Set<long[]> aluList = new HashSet<>();
        aluList.add(new long[5]);

        while (scanner.hasNextLine()) {
            String[] cmd = scanner.nextLine().split(" ");

            if (cmd.length == 2) {
                // input command
                Set<long[]> newAluList = new HashSet<>();
                Set<Long> added = new HashSet<>();

                for (int i = 1; i <= 9; i++) {
                    for (long[] alu : aluList) {
                        long[] newAlu = new long[] {
                                alu[0],
                                alu[1],
                                alu[2],
                                alu[3],
                                alu[4] * 10 + i
                        };

                        newAlu = process(
                                newAlu,
                                cmd[0],
                                new String[] { cmd[1], Integer.toString(i) }
                        );

                        long key = newAlu[2] * 10L + i;

                        if (!added.contains(key)) {
                            newAluList.add(newAlu);
                            added.add(key);
                        }
                    }
                    System.out.println("\t" + i);
                }

                System.out.println(newAluList.size());

                aluList = newAluList;
            } else {
                String[] arguments = new String[] { cmd[1], cmd[2] };

                for (long[] alu : aluList) {
                    process(alu, cmd[0], arguments);
                }
            }
        }

        Set<Long> modelNumbers = new TreeSet<>();
        for (long[] alu : aluList) {
            if (alu[2] != 0) {
                continue;
            }

            modelNumbers.add(alu[4]);
        }

        System.out.println(modelNumbers);
    }
}
