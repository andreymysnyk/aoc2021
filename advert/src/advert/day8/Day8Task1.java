package advert.day8;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day8Task1 {

    private static class Row {
        private List<Set<String>> input;
        private List<Set<String>> output;

        public Row(String line) {
            String[] segments = line.split("\\|");

            input = Arrays.stream(segments[0].trim().split(" "))
                    .map((signals) -> new HashSet<>(Arrays.asList(signals.split(""))))
                    .collect(Collectors.toList());
            output = Arrays.stream(segments[1].trim().split(" "))
                    .map((signals) -> new HashSet<>(Arrays.asList(signals.split(""))))
                    .collect(Collectors.toList());
        }

        public List<Set<String>> getInput() {
            return input;
        }

        public Set<String> findPattern(int size, Predicate<Set<String>> predicate) {
            return getInput().stream()
                    .filter((signals) -> signals.size() == size)
                    .filter(predicate)
                    .findAny()
                    .orElse(null);
        }

        public List<Set<String>> getOutput() {
            return output;
        }

        public String toString() {
            return "{" + input + ", " + output + "}";
        }
    }

    private static int decodeRow(Row row) {
        Set<String> s1 = row.findPattern(2, (signals) -> true);
        Set<String> s7 = row.findPattern(3, (signals) -> true);
        Set<String> s4 = row.findPattern(4, (signals) -> true);
        Set<String> s8 = row.findPattern(7, (signals) -> true);

        Set<String> s3 = row.findPattern(5, (signals) -> signals.containsAll(s7));
        Set<String> s9 = row.findPattern(6, (signals) -> signals.containsAll(s3));

        Set<String> sb = new HashSet<>(s9);
        sb.removeAll(s3);
        String b = sb.iterator().next();

        Set<String> s0 = row.findPattern(6, (signals) -> signals.containsAll(s7) && !signals.containsAll(s3));
        Set<String> s6 = row.findPattern(6, (signals) -> !signals.containsAll(s7));
        Set<String> s2 = row.findPattern(5, (signals) -> !signals.containsAll(s7) && !signals.contains(b));
        Set<String> s5 = row.findPattern(5, (signals) -> !signals.containsAll(s7) && signals.contains(b));

        Map<Set<String>, String> mapper = new HashMap<>();
        mapper.put(s0, "0");
        mapper.put(s1, "1");
        mapper.put(s2, "2");
        mapper.put(s3, "3");
        mapper.put(s4, "4");
        mapper.put(s5, "5");
        mapper.put(s6, "6");
        mapper.put(s7, "7");
        mapper.put(s8, "8");
        mapper.put(s9, "9");

        String answer = row.getOutput().stream()
                .map(mapper::get)
                .collect(Collectors.joining(""));

        return Integer.parseInt(answer);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day8/input1.txt"));

        List<Row> rows = new ArrayList<>();
        while (scanner.hasNextLine()) {
            rows.add(new Row(scanner.nextLine()));
        }

        long cnt = rows.stream()
                .mapToInt(Day8Task1::decodeRow)
                .sum();

        System.out.println(cnt);
    }
}
