package advert.day16;

import java.io.File;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day16Task1 {

    private static class Packet {
        public int version;
        public int type;
        public long literal;
        public int sizeType;
        public int size;
        public List<Packet> subPackets;

        public static Packet of(int version, int type, long literal) {
            Packet p = new Packet();
            p.version = version;
            p.type = type;
            p.literal = literal;

            return p;
        }

        public static Packet of(int version, int type, int sizeType, int size, List<Packet> subPackets) {
            Packet p = new Packet();
            p.version = version;
            p.type = type;
            p.sizeType = sizeType;
            p.size = size;
            p.subPackets = subPackets;

            return p;
        }

        @Override
        public String toString() {
            return "{" +
                    "v=" + version +
                    ", t=" + type +
                    ", l=" + literal +
                    ", st=" + sizeType +
                    ", s=" + size +
                    ", subs=" + subPackets +
                    '}';
        }
    }

    private static int read(LinkedList<Integer> queue, int count) {
        StringBuilder sb = new StringBuilder(count);

        for (int i = 0; i < count; i++) {
            sb.append(queue.pop());
        }

        return Integer.parseInt(sb.toString(), 2);
    }

    private static long readLiteral(LinkedList<Integer> queue) {
        StringBuilder sb = new StringBuilder();

        int leadingBit;
        do {
            leadingBit = queue.pop();

            sb.append(queue.pop());
            sb.append(queue.pop());
            sb.append(queue.pop());
            sb.append(queue.pop());
        } while (leadingBit == 1);

        return Long.parseLong(sb.toString(), 2);
    }

    private static Packet parse(LinkedList<Integer> queue) {
        int version = read(queue, 3);
        int type = read(queue, 3);

        if (type == 4) {
            return Packet.of(version, type, readLiteral(queue));
        }

        List<Packet> packets = new ArrayList<>();
        int sizeType = read(queue, 1);

        if (sizeType == 0) {
            int size = read(queue, 15);

            LinkedList<Integer> subQueue = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                subQueue.add(queue.pop());
            }

            while (!subQueue.isEmpty()) {
                packets.add(parse(subQueue));
            }
        } else {
            int cnt = read(queue, 11);

            for (int i = 0; i < cnt; i++) {
                packets.add(parse(queue));
            }
        }

        return Packet.of(version, type, sizeType, -1, packets);
    }

    private static int sumVersions(Packet packet) {
        int sum = packet.version;

        if (packet.subPackets != null) {
            sum += packet.subPackets.stream()
                    .mapToInt(Day16Task1::sumVersions)
                    .sum();
        }

        return sum;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day16/input1.txt"));

        BigInteger messageHex = new BigInteger("1" + scanner.nextLine(), 16);
        LinkedList<Integer> queue = Arrays.stream(messageHex.toString(2).split(""))
                .map(value -> value.charAt(0) - '0')
                .collect(Collectors.toCollection(LinkedList::new));
        queue.pop(); // :)

        Packet packet = parse(queue);

//        System.out.println(packet);

        System.out.println(sumVersions(packet));
    }
}
