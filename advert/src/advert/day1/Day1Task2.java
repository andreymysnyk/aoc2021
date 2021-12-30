package advert.day1;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class Day1Task2 {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/input2.txt"));

        int cnt = 0;

        LinkedList<Integer> window = new LinkedList<>();
        window.add(scanner.nextInt());
        window.add(scanner.nextInt());
        window.add(scanner.nextInt());

        while (scanner.hasNextInt()) {
            int cur = scanner.nextInt();

            int prev = window.pop();

            System.out.printf("%d %s %d %b%n", prev, window, cur, prev < cur);

            if (prev < cur) {
                cnt++;
            }

            window.add(cur);
        }

        System.out.println(cnt);
    }
}
