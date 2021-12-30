package advert.day21;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day21Task1 {

    private static class DeterministicDice {
        public int counter = 0;

        public int next() {
            return counter++ % 100 + 1;
        }
    }

    private static class Player {
        private int position;
        private int score = 0;

        public static Player of(String position) {
            Player p = new Player();
            p.position = Integer.parseInt(position) - 1;

            return p;
        }

        public int move(DeterministicDice dice) {
            int step = dice.next() + dice.next() + dice.next();

//            System.out.printf("%4d %3d / %5d%n", position, step, score);

            position += step;

            score += position % 10 + 1;

//            System.out.printf("\t%4d / %5d%n", position % 10 + 1, score);

            return score;
        }

        @Override
        public String toString() {
            return "{p=" + position + ", s=" + score + "}";
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day21/input1.txt"));

        List<Player> players = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String[] playerLine = scanner.nextLine().split(": ");
            players.add(Player.of(playerLine[1]));
        }

        DeterministicDice dice = new DeterministicDice();

        boolean win = false;
        while (!win) {
            for (Player player : players) {
                int score = player.move(dice);

                System.out.println(score);

                if (score >= 1000) {
                    win = true;
                    break;
                }
            }
        }

        System.out.println(players);
        System.out.println(dice.counter);

        int result = players.stream()
                .filter((p) -> p.score < 1000)
                .findFirst()
                .map(p -> p.score * dice.counter)
                .orElse(0);

        System.out.println(result);
    }
}
