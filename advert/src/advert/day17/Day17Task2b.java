package advert.day17;

public class Day17Task2b {

    private static int[] stats;
    private static boolean[][] visited;

    private static int getSteps(int y) {
        int steps = 0;
        int step = 0;
        int pos = 0;

        while (pos > y) {
            steps++;
            pos += step;
            step -= 1;
        }

        return pos == y ? steps : -1;
    }

    private static int getFinalPosition(int velocity, int steps) {
        int pos = 0;
        int step = velocity;

        for (int i = 0; i < steps; i++) {
            pos += step;
            step--;
        }

        return pos;
    }

    private static int countForY(int xStep, int steps, int xVelocity, int y) {
        int cnt = 0;

        // positive scenarios - ???
/*
        int belowGroundSteps = getSteps(y);
        if (belowGroundSteps >= 0) {
            int aboveGroupSteps = steps - belowGroundSteps;

//            if (xVelocity == 0) {
//                cnt += 1;
//            }
        }
*/

        // straight scenarios
        int yPos = y;
        int yVelocity = y;

        while (yPos <= y && yVelocity <= 0) {
            yPos = getFinalPosition(yVelocity, steps);

            if (yPos == y) {
                if (xStep == 8 || xStep == 9) {
                    System.out.println(xStep + " straight jump " + yVelocity + " in " + steps + " steps " + yPos);
                }
                cnt++;
            }

            yVelocity++;
        }

        return cnt;
    }

    private static int countForXByStep(int xStep, int x, int y1, int y2) {
        int xPos = 0;
        int xVelocity = xStep;
        int steps = 0;

        while (xPos < x && xVelocity != 0) {
            xPos += xVelocity;
            xVelocity--;
            steps++;
        }

        if (xPos == x) {
            int cnt = 0;

//            System.out.println("x" + l + ": " + dd);

            for (int y = y1; y <= y2; y++) {
                cnt += countForY(xStep, steps, xVelocity, y);
            }

//            System.out.println("\t" + cntY);

            stats[xStep] += cnt;

            return cnt;
        }

        if (xVelocity == 0) {
            return -1; // xStep is to small - stuck on same position and doesn't reach target area
        }

        return 0; // xStep is too big
    }

    private static int countForX(int x, int y1, int y2) {
        int cnt = 0;

        for (int step = x; step >= 0; step--) {
            int result = countForXByStep(step, x, y1, y2);

            if (result < 0) {
                break; // target area is no longer reachable
            }

            cnt += result;
        }

        return cnt;
    }

    public static void main(String[] args) throws Exception {
        stats = new int[31];
        visited = new boolean[31][];

        int cnt = 0;
        for (int i = 20; i <= 30; i++) {
            cnt += countForX(i, -10, -5);
        }

        System.out.println(cnt);

        for (int i = 0; i < stats.length; i++) {
            System.out.printf(" %2d |", i);
        }
        System.out.println();
        for (int stat : stats) {
            System.out.printf(" %2d |", stat);
        }
        System.out.println();

//        for (int i = 0; i < 10; i++) {
//            System.out.print(" " + simY(i, 4));
//        }
//
//        System.out.println("-------");
//
//        simY(1, 4);
    }
}
