package advert.day17;

import java.io.File;
import java.util.*;

public class Day17Task1 {

    private static int getMaxHeight(int y) {
        int pos = 0;
        int velocity = y;
        int max = 0;

        while (pos >= 0) {
            pos += velocity;
            velocity--;

            max = Math.max(max, pos);
        }

        return max;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day17/input1.txt"));

        String[] line = scanner.nextLine().split(", ");
        String[] x = line[0].split("=")[1].split("\\.\\.");
        String[] y = line[1].split("=")[1].split("\\.\\.");

        int y1 = Integer.parseInt(y[0]);
        System.out.println(getMaxHeight(- y1 - 1));
    }
}
