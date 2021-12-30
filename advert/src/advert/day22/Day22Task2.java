package advert.day22;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Day22Task2 {

    private static class Shape {
        public int x1;
        public int x2;
        public int y1;
        public int y2;
        public int z1;
        public int z2;
        public boolean on;

        public static Shape of(String line) {
            String[] parts = line.split(",");
            String[] x = parts[0].split("=")[1].split("\\.\\.");
            String[] y = parts[1].split("=")[1].split("\\.\\.");
            String[] z = parts[2].split("=")[1].split("\\.\\.");

            Shape p = new Shape();
            p.x1 = Integer.parseInt(x[0]);
            p.x2 = Integer.parseInt(x[1]);
            p.y1 = Integer.parseInt(y[0]);
            p.y2 = Integer.parseInt(y[1]);
            p.z1 = Integer.parseInt(z[0]);
            p.z2 = Integer.parseInt(z[1]);
            p.on = "on".equals(parts[0].split(" ")[0]);

            return p;
        }
    }

    private static Map<Integer, Integer> setToMap(Set<Integer> set) {
        Map<Integer, Integer> grid = new HashMap<>();

        int index = 0;
        for (Integer value : set) {
            grid.put(value, index++);
        }

        return grid;
    }

    private static long weight(Map<Integer, Integer> reverseMap, int pos) {
        Integer valuePrev = reverseMap.get(pos - 1);
        Integer value = reverseMap.get(pos);
        Integer valueNext = reverseMap.get(pos + 1);

        if (valuePrev == null || valueNext == null) {
            return 0;
        }

        if ((value - 1 == valuePrev) && (value + 1 == valueNext)) {
            return 1;
        }

        if (value + 1 == valueNext) {
            return 0;
        }

        return valueNext - value + 1;
    }

    private static long[] getGridWeights(Map<Integer, Integer> map) {
        Map<Integer, Integer> reverseMap = map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        Map.Entry::getKey
                ));

        long[] weights = new long[2500];
        for (int value : map.values()) {
            weights[value] = weight(reverseMap, value);
        }

        return weights;
    }

    private static void add(Set<Integer> set, int a) {
        set.add(a - 1);
        set.add(a);
        set.add(a + 1);
    }

    private static long sum(Map<Integer, BitSet[]> xAxis, long[] xWeights, long[] yWeights, long[] zWeights) {
        System.out.println("Sum");

        long cnt = 0;

        int index = 0;
        for (int x : xAxis.keySet()) {
            BitSet[] yAxis = xAxis.get(x);
            long xM = xWeights[x];

            for (int y = 0; y < yAxis.length; y++) {
                BitSet zAxis = yAxis[y];

                if (zAxis == null) {
                    continue;
                }

                long yM = yWeights[y];

                for (int z = 0; z < 2500; z++) {
                    long zM = zWeights[z];

                    cnt += zAxis.get(z)
                            ? (xM * yM * zM)
                            : 0;
                }
            }

            if (++index % 10 == 0) {
                System.out.println("\t" + index + " / " + xAxis.size());
            }
        }

        return cnt;
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        Scanner scanner = new Scanner(new File("src/advert/day22/input1.txt"));

        List<Shape> shapes = new ArrayList<>();
        Set<Integer> xSet = new TreeSet<>();
        Set<Integer> ySet = new TreeSet<>();
        Set<Integer> zSet = new TreeSet<>();
        while (scanner.hasNextLine()) {
            Shape p = Shape.of(scanner.nextLine());
            shapes.add(p);

            add(xSet, p.x1);
            add(xSet, p.x2);
            add(ySet, p.y1);
            add(ySet, p.y2);
            add(zSet, p.z1);
            add(zSet, p.z2);
        }

        Map<Integer, Integer> xGrid = setToMap(xSet);
        Map<Integer, Integer> yGrid = setToMap(ySet);
        Map<Integer, Integer> zGrid = setToMap(zSet);

        System.out.println("Shapes");
        Map<Integer, BitSet[]> xAxis = new HashMap<>();
        int cnt = 0;
        for (Shape shape : shapes) {
            System.out.println("\t" + ++cnt + " / " + shapes.size());

            int xs = xGrid.get(shape.x1);
            int xe = xGrid.get(shape.x2);
            int ys = yGrid.get(shape.y1);
            int ye = yGrid.get(shape.y2);
            int zs = zGrid.get(shape.z1);
            int ze = zGrid.get(shape.z2);

            for (int i = xs; i <= xe; i++) {
                if (!xAxis.containsKey(i)) {
                    xAxis.put(i, new BitSet[2500]);
                }

                BitSet[] yAxis = xAxis.get(i);
                for (int j = ys; j <= ye; j++) {
                    if (yAxis[j] == null) {
                        yAxis[j] = new BitSet();
                    }

                    BitSet zAxis = yAxis[j];

                    for (int k = zs; k <= ze; k++) {
                        zAxis.set(k, shape.on);
                    }
                }
            }
        }

        long[] xWeights = getGridWeights(xGrid);
        long[] yWeights = getGridWeights(yGrid);
        long[] zWeights = getGridWeights(zGrid);
        System.out.println(sum(xAxis, xWeights, yWeights, zWeights));

        long end = System.currentTimeMillis();
        System.out.printf("time %ds%n", (end - start) / 1000);
    }
}
