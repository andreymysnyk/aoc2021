package advert.day12;

import java.io.File;
import java.util.*;

public class Day12Task2 {

    private static List<String> visit(Map<String, List<String>> graph, String v, String path, Map<String, Integer> visited) {
        List<String> paths = new ArrayList<>();

        if ("end".equals(v)) {
            paths.add(path);
            return paths;
        }

        for (String v2 : graph.get(v)) {
            if (visited.containsKey(v2)) {
                long countTwiceVisited = visited.values()
                        .stream()
                        .filter(cnt -> cnt > 1)
                        .count();
                if (countTwiceVisited > 1 || visited.get(v2) > 1) {
                    continue;
                }
            }

            Map<String, Integer> visited2 = new HashMap<>(visited);

            if (v2.charAt(0) >= 'a') {
                if (visited2.containsKey(v2)) {
                    visited2.put(v2, visited2.get(v2) + 1);
                } else {
                    visited2.put(v2, 1);
                }
            }

            paths.addAll(visit(graph, v2, path + ", " + v2, visited2));
        }

        return paths;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day12/input1.txt"));

        Map<String, List<String>> graph = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] edge = scanner.nextLine().split("-");

            if (!graph.containsKey(edge[0])) {
                graph.put(edge[0], new ArrayList<>());
            }

            if (!graph.containsKey(edge[1])) {
                graph.put(edge[1], new ArrayList<>());
            }

            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        Map<String, Integer> visited = new HashMap<>();
        visited.put("start", 2);
        List<String> paths = visit(graph, "start", "start", visited);

//        System.out.println(graph);
        System.out.println(paths.size());
//        System.out.println(String.join("\n", paths));
    }
}
