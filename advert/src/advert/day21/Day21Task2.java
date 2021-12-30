package advert.day21;

import java.io.File;
import java.util.*;

public class Day21Task2 {

    private static final Map<Integer, Integer> recursions = new HashMap<Integer, Integer>() {{
        put(3, 1);
        put(4, 3);
        put(5, 6);
        put(6, 7);
        put(7, 6);
        put(8, 3);
        put(9, 1);
    }};

    private static final Map<String, long[]> cache = new HashMap<>();

    private static int getPosition(int position) {
        if (position > 10) {
            position -= 10;
        }

        return position;
    }

    private static long[] move(int posA, int scoreA, int posB, int scoreB) {
        if (scoreB >= 21) {
            return new long[]{ 0L, 1L };
        }

        String key = String.format("%d-%d-%d-%d", posA, scoreA, posB, scoreB);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        long[] result = { 0L, 0L };
        for (Map.Entry<Integer, Integer> entry : recursions.entrySet()) {
            int step = entry.getKey();
            int multiplier = entry.getValue();

            int pos = getPosition(posA + step);
            long[] a = move(posB, scoreB, pos, scoreA + pos);

            result[0] += multiplier * a[1];
            result[1] += multiplier * a[0];
        }

        cache.put(key, result);

        return result;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day21/input1.txt"));

        List<Integer> positions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] playerLine = scanner.nextLine().split(": ");
            positions.add(Integer.parseInt(playerLine[1]));
        }

        long[] result = move(
                positions.get(0),
                0,
                positions.get(1),
                0
        );

        System.out.println(result[0] + " " + result[1]);
    }
}
