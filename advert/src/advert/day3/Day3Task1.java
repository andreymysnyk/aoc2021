package advert.day3;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3Task1 {

    private static class Counter {
        private Map<String, Integer> cnts = new HashMap<>();

        public void add(String code) {
            cnts.merge(code, 1, Integer::sum);
        }

        public String getGreater() {
            if (cnts.get("0") > cnts.get("1")) {
                return "0";
            }

            return "1";
        }

        public String getSmaller() {
            if (cnts.get("0") < cnts.get("1")) {
                return "0";
            }

            return "1";
        }

        public String toString() {
            return cnts.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        List<Counter> stats = new ArrayList<>();

        Scanner scanner = new Scanner(new File("src/advert/day3/input1.txt"));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] codes = line.split("");

            for (int i = 0; i < codes.length; i++) {
                String code = codes[i];

                if (i >= stats.size()) {
                    stats.add(new Counter());
                }

                stats.get(i).add(code);
            }
        }

        String gammaRateStr = stats.stream()
                .map(Counter::getGreater)
                .collect(Collectors.joining());
        int gammaRate = Integer.parseInt(gammaRateStr, 2);

        String epsilonRateStr = stats.stream()
                .map(Counter::getSmaller)
                .collect(Collectors.joining());
        int epsilonRate = Integer.parseInt(epsilonRateStr, 2);

        System.out.printf("%d %d %d%n", gammaRate, epsilonRate, gammaRate * epsilonRate);
    }
}
