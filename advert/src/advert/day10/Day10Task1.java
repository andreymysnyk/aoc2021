package advert.day10;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;

public class Day10Task1 {

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
        put(']', 57);
        put('}', 1197);
        put(')', 3);
        put('>', 25137);
    }};

    private static Optional<Character> getInvalidCharacter(String line) {
        LinkedList<Character> stack = new LinkedList<>();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (OPEN.contains(c)) {
                stack.push(c);
            } else {
                char top = stack.pop();
                if (c != OPEN_CLOSE_MAP.get(top)) {
//                    System.out.println(c);
                    return Optional.of(c);
                }
            }
        }

        return Optional.empty();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day10/input1.txt"));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        long result = lines.stream()
                .map(Day10Task1::getInvalidCharacter)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToInt(SCORE_MAP::get)
                .sum();

        System.out.println(result);
    }
}
