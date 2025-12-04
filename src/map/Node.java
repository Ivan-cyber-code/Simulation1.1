package map;



import java.util.*;

import entity.*;


public class Node {
    public Coordinates coordinates;
    private Node parent;

    private final int[][] SET_MOVES={{0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}};


    public Node(Coordinates coordinates, Node parent) {
        this.coordinates = coordinates;
        this.parent = parent;
    }
    public Node() {
    }

    public List<Node> findPath(Node start, Node goal, Field field) {
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visits = new boolean[field.lines][field.columns];
        queue.add(start);
        visits[start.coordinates.line][start.coordinates.column] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (isGoal(current, goal)) {
                return extractPath(current);
            }

            boolean goalFound = isNextNodeGoal(current, goal);


            for (int[] direction : SET_MOVES) {
                int lines = current.coordinates.line + direction[0];
                int columns = current.coordinates.column + direction[1];

                if (goalFound) {
                    Node nextNode = new Node(new Coordinates(lines,columns), current);
                    if (isGoal(nextNode, goal)) {
                        return extractPath(nextNode);
                    }
                } else {
                    if (isValid(lines, columns, visits, field, goal)) {
                        visits[lines][columns] = true;
                        queue.add(new Node(new Coordinates(lines, columns), current));
                    }
                }

            }
        }
        return List.of();
    }

    private boolean isGoal(Node current, Node goal) {
        if (current.coordinates.line == goal.coordinates.line && current.coordinates.column == goal.coordinates.column) {
            return true;
        } else {
            return false;
        }
    }
    private List<Node> extractPath(Node goal) {
        List<Node> path = new ArrayList<>();
        for (Node node = goal; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }
    private boolean isNextNodeGoal(Node current, Node goal) {
        for (int[] direction : SET_MOVES) {
            int newLine = current.coordinates.line + direction[0];
            int newColumn = current.coordinates.column + direction[1];
            boolean flag = isGoal(new Node(new Coordinates(newLine, newColumn), current), goal);
            if (flag) {
                return flag;
            }
        }
        return false;
    }
    private boolean isValid(int lines, int columns, boolean[][] visits, Field field, Node goal) {
        return (lines >= 0 && lines < field.lines && columns >= 0 && columns < field.columns && !field.entities.containsKey(new Coordinates(lines, columns)) && !visits[lines][columns]);
    }




}
