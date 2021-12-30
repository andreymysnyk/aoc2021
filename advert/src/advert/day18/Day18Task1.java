package advert.day18;

import java.io.File;
import java.util.*;

public class Day18Task1 {

    private static class Expression {
        private Expression left;
        private Expression right;
        private Expression parent;
        private int value;

        public static Expression of(Expression parent, int value) {
            Expression exp = new Expression();
            exp.parent = parent;
            exp.value = value;

            return exp;
        }

        public static Expression of(Expression parent, Expression left, Expression right) {
            Expression exp = new Expression();
            exp.parent = parent;
            exp.left = left;
            exp.right = right;

            return exp;
        }

        @Override
        public String toString() {
            if (left == null) {
                return value + "";
            }

            return "[" + left + ", " + right+ "]";
        }
    }

    private static Expression parse(LinkedList<String> queue, Expression parent) {
        if (queue.getFirst().charAt(0) != '[') {
            int value = Integer.parseInt(queue.pop());
            return Expression.of(parent, value);
        }

        queue.pop(); // [

        Expression exp = new Expression();
        exp.parent = parent;
        exp.left = parse(queue, exp);

        queue.pop(); // ,

        exp.right = parse(queue, exp);

        queue.pop(); // ]

        return exp;
    }

    private static Expression parse(String line) {
        LinkedList<String> queue = new LinkedList<>(Arrays.asList(line.split("")));
        return parse(queue, null);
    }

    private static void incrementNext(Expression exp, int value) {
        Expression current = exp;
        while (current.parent != null && current.parent.right == current) {
            current = current.parent;
        }

        if (current.parent != null) {
            current = current.parent.right;

            while (current.left != null) {
                current = current.left;
            }

            current.value += value;
        }
    }

    private static void incrementPrev(Expression exp, int value) {
        Expression current = exp;
        while (current.parent != null && current.parent.left == current) {
            current = current.parent;
        }

        if (current.parent != null) {
            current = current.parent.left;

            while (current.left != null) {
                current = current.right;
            }

            current.value += value;
        }
    }

    private static Expression explode(Expression exp, int level) {
        if (exp.left == null) {
            return null;
        }

        if (exp.left.left == null && exp.right.left == null && level >= 4) {
            incrementPrev(exp, exp.left.value);
            incrementNext(exp, exp.right.value);

            return Expression.of(exp.parent, 0);
        }

        Expression left = explode(exp.left, level + 1);
        if (left != null) {
            exp.left = left;
            return exp;
        }

        Expression right = explode(exp.right, level + 1);
        if (right != null) {
            exp.right = right;
            return exp;
        }

        return null;
    }

    private static Expression split(Expression exp) {
        if (exp.left == null) {
            if (exp.value > 9) {
                Expression splitExp = new Expression();
                splitExp.parent = exp.parent;
                splitExp.left = Expression.of(splitExp, exp.value / 2);
                splitExp.right = Expression.of(splitExp, exp.value - (exp.value / 2));

                return splitExp;
            }

            return null;
        }

        Expression left = split(exp.left);
        if (left != null) {
            exp.left = left;
            return exp;
        }

        Expression right = split(exp.right);
        if (right != null) {
            exp.right = right;
            return exp;
        }

        return null;
    }

    private static Expression reduce(Expression exp) {
        boolean reduced = true;

        while (reduced) {
            boolean exploded = true;
            while (exploded) {
                exploded = explode(exp, 0) != null;
            }

            reduced = split(exp) != null;
        }

        return exp;
    }

    private static Expression sum(Expression a, Expression b) {
        Expression sum = Expression.of(null, a, b);
        a.parent = sum;
        b.parent = sum;

        return reduce(sum);
    }

    private static int magnitude(Expression exp) {
        if (exp.left == null) {
            return exp.value;
        }

        return 3 * magnitude(exp.left) + 2 * magnitude(exp.right);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/advert/day18/input01.txt"));

        List<Expression> expressions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            expressions.add(parse(scanner.nextLine()));
        }

        Integer result = expressions.stream()
                .reduce(Day18Task1::sum)
                .map(Day18Task1::magnitude)
                .orElse(null);

        System.out.println(result);
    }
}
