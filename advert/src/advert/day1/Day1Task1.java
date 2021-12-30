package advert.day1;

import java.io.File;
import java.util.Scanner;

public class Day1Task1 {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/input1.txt"));

        int prev = scanner.nextInt();
        int cnt = 0;
        while (scanner.hasNextInt()) {
            int cur = scanner.nextInt();

            if (prev < cur) {
                cnt++;
            }

            prev = cur;
        }

        System.out.println(cnt);
    }
}
