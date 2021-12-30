package advert.day14;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14Task1 {

    private static class Node {
        public String value;
        public Node next;

        public static Node of(String value) {
            Node n = new Node();
            n.value = value;

            return n;
        }
    }

    private static class LinkedList {
        public Node first;

        public void add(String... elements) {
            first = Node.of(elements[0]);

            Node prev = first;

            for (int i = 1; i < elements.length; i++) {
                Node curr = Node.of(elements[i]);
                prev.next = curr;

                prev = curr;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            Node curr = first;
            while (curr != null) {
                sb.append(curr.value);

                curr = curr.next;
            }
            return sb.toString();
        }

        public List<String> toList() {
            List<String> list = new ArrayList<>();

            Node curr = first;
            while (curr != null) {
                list.add(curr.value);

                curr = curr.next;
            }

            return list;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day14/input01.txt"));

        LinkedList template = new LinkedList();
        template.add(scanner.nextLine().split(""));

        scanner.nextLine();

        Map<String, String> rules = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] rule = scanner.nextLine().split(" -> ");
            rules.put(rule[0], rule[1]);
        }

        System.out.println(template);

        for (int i = 0; i < 10; i++) {
            Node curr = template.first;

            while (curr.next != null) {
                String left = curr.value;
                String right = curr.next.value;
                String key = left + right;

                if (rules.containsKey(key)) {
                    Node s = Node.of(rules.get(key));
                    s.next = curr.next;
                    curr.next = s;

                    curr = curr.next.next;
                } else {
                    curr = curr.next;
                }
            }

            System.out.println(template);
        }

        Map<String, Long> map = template.toList().stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        long min = map.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)
                .orElse(0L);

        long max = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)
                .orElse(0L);

        System.out.println(max - min);
    }
}
