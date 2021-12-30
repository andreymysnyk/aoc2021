package advert.day4;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Day4Task2 {

    private static class Board {

        private int[][] cells;
        private boolean[][] marked;

        public Board(int[][] cells) {
            this.cells = cells;
            this.marked = new boolean[cells.length][cells[0].length];
        }

        public boolean markCell(int number) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (cells[i][j] == number) {
                        marked[i][j] = true;
                    }
                }
            }

            return isWin();
        }

        public int sumUnmarked() {
            int sum = 0;

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!marked[i][j]) {
                        sum += cells[i][j];
                    }
                }
            }

            return sum;
        }

        public boolean isWin() {
            int width = cells.length;
            int height = cells[0].length;

            // rows
            for (int i = 0; i < width; i++) {
                boolean areAllMarked = true;

                for (int j = 0; j < height; j++) {
                    areAllMarked &= marked[i][j];
                }

                if (areAllMarked) {
                    return true;
                }
            }

            // columns
            for (int j = 0; j < height; j++) {
                boolean areAllMarked = true;

                for (int i = 0; i < width; i++) {
                    areAllMarked &= marked[i][j];
                }

                if (areAllMarked) {
                    return true;
                }
            }

            return false;
        }

        public static Board read(Scanner scanner) {
            int[][] cells = new int[5][5];

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    cells[i][j] = scanner.nextInt();
                }
            }

            return new Board(cells);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day4/input1.txt"));

        List<Integer> numbers = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Board> boards = new ArrayList<>();

        while (scanner.hasNextInt()) {
            boards.add(Board.read(scanner));
        }

        Set<Board> wonBoards = new HashSet<>();

        for (int number : numbers) {
            for (Board board : boards) {
                boolean isWin = board.markCell(number);

                if (isWin && !wonBoards.contains(board)) {
                    wonBoards.add(board);

                    if (wonBoards.size() == boards.size()) {
                        System.out.printf("%d %d %d%n", number, board.sumUnmarked(), number * board.sumUnmarked());
                        return;
                    }
                }
            }
        }
    }
}
