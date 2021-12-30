package advert.day14;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14Task2 {

    private static Map<String, long[]> cache = new HashMap<>();

    private static long[] countElements(Map<Character, Map<Character, Character>> rules, Character left, Character right, int steps) {
        String cacheKey = String.format("%c%c%d", left, right, steps);

        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        if (steps == 0) {
            return new long[26];
        }

        Character middle = rules.get(left).get(right);

        long[] counter = mergeCounter(
                countElements(rules, left, middle, steps - 1),
                countElements(rules, middle, right, steps - 1)
        );

        counter[middle - 'A'] += 1L;

        cache.put(cacheKey, counter);

        return counter;
    }

    private static long[] mergeCounter(long[] counterA, long[] counterB) {
        long[] counter = new long[26];

        for (int i = 0; i < counterA.length; i++) {
            counter[i] = counterA[i] + counterB[i];
        }

        return counter;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day14/input1.txt"));

        String template = scanner.nextLine();

        scanner.nextLine();

        Map<Character, Map<Character, Character>> rules = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] rule = scanner.nextLine().split(" -> ");
            char left = rule[0].charAt(0);
            char right = rule[0].charAt(1);
            char middle = rule[1].charAt(0);

            if (!rules.containsKey(left)) {
                rules.put(left, new HashMap<>());
            }

            rules.get(left).put(right, middle);
        }

        long[] counter = new long[26];
        for (int i = 0; i < template.length() - 1; i++) {
            char left = template.charAt(i);
            char right = template.charAt(i + 1);

            if (i == 0) {
                counter[left - 'A'] += 1L;
            }
            counter[right - 'A'] += 1L;

            counter = mergeCounter(
                    counter,
                    countElements(rules, left, right, 40)
            );
        }

//        for (char c = 'A'; c <= 'Z'; c++) {
//            if (counter[c - 'A'] == 0) {
//                continue;
//            }
//            System.out.println(c + " " + counter[c - 'A']);
//        }

        long min = Arrays.stream(counter)
                .filter(v -> v > 0)
                .min()
                .orElse(0L);

        long max = Arrays.stream(counter)
                .max()
                .orElse(0L);

        System.out.println(max - min);
    }
}

/*

Template:     NNCB                                                 2xN 1xC 1xB
After step 1: NCNBCHB                                              2xN 2xC 2xB 1xH
After step 2: NBCCNBBBCBHCB                                        2xN 6xB 4xC 1xH
After step 3: NBBBCNCCNBBNBNBBCHBHHBCHB                            5xN 11xB 5xC 4xH
After step 4: NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB

*/