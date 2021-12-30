package advert.day24;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day24Task1a {

    private static final Map<String, BiConsumer<ALU, Integer>> UPDATERS = new HashMap<String, BiConsumer<ALU, Integer>>() {{
        put("x", (alu, value) -> { alu.x = value; });
        put("y", (alu, value) -> { alu.y = value; });
        put("z", (alu, value) -> { alu.z = value; });
        put("w", (alu, value) -> { alu.w = value; });
    }};

    private static ALU update(ALU alu, String reg, Integer value) {
        if (!UPDATERS.containsKey(reg)) {
            return alu;
        }

        UPDATERS.get(reg).accept(alu, value);

        return alu;
    }

    private static final Map<String, Function<ALU, Integer>> GETTERS = new HashMap<String, Function<ALU, Integer>>() {{
        put("x", (alu) -> alu.x );
        put("y", (alu) -> alu.y );
        put("z", (alu) -> alu.z );
        put("w", (alu) -> alu.w );
    }};

    private static Integer get(ALU alu, String reg) {
        if (!GETTERS.containsKey(reg)) {
            return Integer.parseInt(reg);
        }

        return GETTERS.get(reg).apply(alu);
    }

    private static class ALU {
        public int x = 0;
        public int y = 0;
        public int z = 0;
        public int w = 0;
        public ALU parent = null;

        public static ALU of(ALU alu) {
            ALU clonedAlu = new ALU();
            clonedAlu.x = alu.x;
            clonedAlu.y = alu.y;
            clonedAlu.z = alu.z;
            clonedAlu.w = alu.w;
            clonedAlu.parent = alu;

            return clonedAlu;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ALU alu = (ALU) o;
            return x == alu.x && y == alu.y && z == alu.z && w == alu.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, w);
        }

        @Override
        public String toString() {
            return "{ x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "}";
        }
    }

    private static final Map<String, BiFunction<ALU, String[], ALU>> COMMANDS = new HashMap<String, BiFunction<ALU, String[], ALU>>() {{
        put("inp", (alu, args) -> {
            return update(alu, args[0], Integer.parseInt(args[1]));
        });
        put("add", (alu, args) -> {
            int a = Day24Task1a.get(alu, args[0]);
            int b = Day24Task1a.get(alu, args[1]);

            return update(alu, args[0], a + b);
        });
        put("mul", (alu, args) -> {
            int a = Day24Task1a.get(alu, args[0]);
            int b = Day24Task1a.get(alu, args[1]);

            return update(alu, args[0], a * b);
        });
        put("div", (alu, args) -> {
            int a = Day24Task1a.get(alu, args[0]);
            int b = Day24Task1a.get(alu, args[1]);

            return update(alu, args[0], a / b);
        });
        put("mod", (alu, args) -> {
            int a = Day24Task1a.get(alu, args[0]);
            int b = Day24Task1a.get(alu, args[1]);

            return update(alu, args[0], a % b);
        });
        put("eql", (alu, args) -> {
            int a = Day24Task1a.get(alu, args[0]);
            int b = Day24Task1a.get(alu, args[1]);

            return update(alu, args[0], a == b ? 1 : 0);
        });
    }};

    private static ALU process(ALU alu, String cmd, String[] args) {
        if (!COMMANDS.containsKey(cmd)) {
            return alu;
        }

        return COMMANDS.get(cmd).apply(alu, args);
    }

    private static ALU process(List<String> cmds, LinkedList<String> input) {
        ALU alu = new ALU();

        for (String cmdLine : cmds) {
            String[] cmd = cmdLine.split(" ");

            if (cmd.length == 2) {
                // input command
                process(
                        alu,
                        cmd[0],
                        new String[] { cmd[1], input.pop() }
                );
            } else {
                String[] arguments = new String[] { cmd[1], cmd[2] };

                process(alu, cmd[0], arguments);
            }
        }

        return alu;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day24/input01.txt"));

        LinkedList<String> inputQueue = new LinkedList<>(Arrays.asList(
                "8", "3", "9", "4", "1", "2", "5", "3", "1", "2", "7", "1", "8", "9"
        ));

        List<String> cmds = new ArrayList<>();
        while (scanner.hasNextLine()) {
            cmds.add(scanner.nextLine());
        }

        System.out.println(process(cmds, inputQueue));
    }
}
