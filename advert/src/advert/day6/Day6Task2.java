package advert.day6;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day6Task2 {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day6/input1.txt"));

        Map<Integer, Long> states = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        for (int days = 0; days < 256; days++) {
            Map<Integer, Long> newStates = new HashMap<>();
            long newborns = 0;

            for (int timer : states.keySet()) {
                long cnt = states.get(timer);

                if (timer == 0) {
                    timer = 6;
                    newborns += cnt;
                } else {
                    timer--;
                }

                newStates.merge(timer, cnt, Long::sum);
            }

            newStates.put(8, newborns);

            states = newStates;
//            System.out.println(states);
        }

        long total = states.values().stream()
                .mapToLong(v -> v)
                .sum();
        System.out.println(total);
    }
}
