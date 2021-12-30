package advert.day6;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day6Task1 {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day6/input1.txt"));

        List<Integer> states = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int days = 0; days < 80; days++) {
            int newborns = 0;

            for (int i = 0; i < states.size(); i++) {
                int timer = states.get(i);

                if (timer == 0) {
                    timer = 6;
                    newborns++;
                } else {
                    timer--;
                }

                states.set(i, timer);
            }

            for (int j = 0; j < newborns; j++) {
                states.add(8);
            }

//            System.out.println(states);
        }

        System.out.println(states.size());
    }
}
