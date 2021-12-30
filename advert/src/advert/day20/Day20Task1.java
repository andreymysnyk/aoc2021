package advert.day20;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day20Task1 {

    private static char[][] getMap(List<String> lines) {
        int width = lines.size() + 500;
        int height = lines.get(0).length() + 500;

        char[][] map = new char[width][height];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = '.';
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                map[i + 250][j + 250] = lines.get(i).charAt(j);
            }
        }

        return map;
    }

    private static void print(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    private static char[][] enhance(char[][] map, String algorithm) {
        char[][] enhancedMap = new char[map.length - 2][map[0].length - 2];

        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[0].length - 1; j++) {
                String codeStr = String.format(
                        "%c%c%c%c%c%c%c%c%c",
                        map[i - 1][j - 1],
                        map[i - 1][j    ],
                        map[i - 1][j + 1],
                        map[i    ][j - 1],
                        map[i    ][j    ],
                        map[i    ][j + 1],
                        map[i + 1][j - 1],
                        map[i + 1][j    ],
                        map[i + 1][j + 1]
                );
                codeStr = codeStr.replace(".", "0").replace("#", "1");

                enhancedMap[i - 1][j - 1] = algorithm.charAt(
                        Integer.parseInt(codeStr, 2)
                );
            }
        }

        return enhancedMap;
    }

    private static int count(char[][] map, char pixel) {
        int cnt = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                cnt += map[i][j] == pixel ? 1 : 0;
            }
        }

        return cnt;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day20/input1.txt"));

        String algorithm = scanner.nextLine();
        scanner.nextLine();

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        char[][] map = getMap(lines);

        for (int i = 0; i < 50; i++) {
            map = enhance(map, algorithm);
            System.out.println(i);
        }

        print(map);

        System.out.println(count(map, '#'));
    }
}
