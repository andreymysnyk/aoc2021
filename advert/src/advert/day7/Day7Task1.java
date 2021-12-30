package advert.day7;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7Task1 {

    private static int asum(int n) {
        return ((1 + n) * n) / 2;
    }

    private static int fuel(List<Integer> heights, int height) {
        return heights.stream()
                .mapToInt(v -> v - height)
                .map(Math::abs)
                .map(Day7Task1::asum)
                .sum();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day7/input1.txt"));

        List<Integer> heights = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int lower = heights.stream().min(Integer::compare).get();
        int upper = heights.stream().max(Integer::compare).get();

        int min = Integer.MAX_VALUE;
        for (int h  = lower; h <= upper; h++) {
            min = Math.min(min, fuel(heights, h));
        }

        System.out.println(min);
    }
}
