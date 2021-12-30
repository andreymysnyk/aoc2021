package advert.day17;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day17Task2 {

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

    private static boolean isInTargetArea(int xStep, int yStep, int x1, int x2, int y1, int y2) {
        int xPos = 0;
        int yPos = 0;
        int xVelocity = xStep;
        int yVelocity = yStep;

        while (xPos <= x2 && yPos >= y2) {
            xPos += xVelocity;
            yPos += yVelocity;

            if ((xPos >= x1 && xPos <= x2) && (yPos <= y1 && yPos >= y2)) {
                return true;
            }

            xVelocity = Math.max(0, xVelocity - 1);
            yVelocity--;
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day17/input1.txt"));

        String[] line = scanner.nextLine().split(", ");
        String[] xLine = line[0].split("=")[1].split("\\.\\.");
        String[] yLine = line[1].split("=")[1].split("\\.\\.");

        int x1 = Integer.parseInt(xLine[0]);
        int x2 = Integer.parseInt(xLine[1]);
        int y1 = Integer.parseInt(yLine[0]);
        int y2 = Integer.parseInt(yLine[1]);
        int maxHeight = getMaxHeight(- y1 - 1);

        Set<Integer> xVelocities = new HashSet<>();

        for (int x = x1; x <= x2; x++) {
            for (int step = x; step >= 0; step--) {
                int xPos = 0;
                int xVelocity = step;

                while (xPos < x && xVelocity != 0) {
                    xPos += xVelocity;
                    xVelocity--;
                }

                if (xPos == x) {
                    xVelocities.add(step);
                }

                if (xVelocity == 0) {
                    break;
                }
            }
        }

        int cnt = 0;
        for (int xStep : xVelocities) {
            for (int yStep = y1; yStep <= maxHeight; yStep++) {
                if (isInTargetArea(xStep, yStep, x1, x2, y2, y1)) {
                    cnt++;
                }
            }
        }

        System.out.println(cnt);
    }
}
