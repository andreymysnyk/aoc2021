package advert.day3;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3Task2 {

    private static class Counter {
        private Map<String, Integer> cnts = new HashMap<>();

        public void add(String code) {
            cnts.merge(code, 1, Integer::sum);
        }

        public String getGreater() {
            if (cnts.get("1") >= cnts.get("0")) {
                return "1";
            }

            return "0";
        }

        public String getSmaller() {
            if (cnts.get("0") <= cnts.get("1")) {
                return "0";
            }

            return "1";
        }

        public String toString() {
            return cnts.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        List<String[]> stats = new ArrayList<>();

        Scanner scanner = new Scanner(new File("src/advert/day3/input1.txt"));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            stats.add(line.split(""));
        }

        int oxygenRate = findRate(stats, Counter::getGreater);
        int co2Rate = findRate(stats, Counter::getSmaller);

        System.out.printf("%d %d %d%n", oxygenRate, co2Rate, oxygenRate * co2Rate);
    }

    private static int findRate(List<String[]> stats, Function<Counter, String> counterMapper) {
        int level = 0;

        while (stats.size() > 1) {
            final int levelIn = level;
            Counter counter = new Counter();

            stats.forEach((codes) -> {
                counter.add(codes[levelIn]);
            });

            stats = stats.stream()
                    .filter((codes) -> counterMapper.apply(counter).equals(codes[levelIn]))
                    .collect(Collectors.toList());

            level++;

//            stats.stream()
//                    .map((codes) -> String.join("", codes))
//                    .forEach((row) -> System.out.printf("%s ", row));
//            System.out.printf("%n");
        }

        return Integer.parseInt(String.join("", stats.get(0)), 2);
    }
}
