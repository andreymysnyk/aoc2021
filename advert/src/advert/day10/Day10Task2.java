package advert.day10;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Day10Task2 {

    private static final Set<Character> OPEN = new HashSet<Character>() {{
        add('[');
        add('{');
        add('(');
        add('<');
    }};

    private static final Map<Character, Character> OPEN_CLOSE_MAP = new HashMap<Character, Character>() {{
        put('[', ']');
        put('{', '}');
        put('(', ')');
        put('<', '>');
    }};

    private static final Map<Character, Integer> SCORE_MAP = new HashMap<Character, Integer>() {{
        put('[', 2);
        put('{', 3);
        put('(', 1);
        put('<', 4);
    }};

    private static LinkedList<Character> getIncompleteCharacters(String line) {
        LinkedList<Character> stack = new LinkedList<>();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (OPEN.contains(c)) {
                stack.push(c);
            } else {
                char top = stack.pop();
                if (c != OPEN_CLOSE_MAP.get(top)) {
                    return new LinkedList<>();
                }
            }
        }

        return stack;
    }

    private static long getScore(LinkedList<Character> stack) {
        long sum = 0;

        while (!stack.isEmpty()) {
            char top = stack.pop();
            sum = sum * 5 + SCORE_MAP.get(top);
        }

        return sum;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day10/input1.txt"));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        List<Long> results = lines.stream()
                .map(Day10Task2::getIncompleteCharacters)
                .map(Day10Task2::getScore)
                .filter(scores -> scores > 0)
                .sorted()
                .collect(Collectors.toList());

        System.out.println(results.get(results.size() / 2));
    }
}
