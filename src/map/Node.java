package map;



import java.util.*;

import entity.*;


public class Node {
    public Coordinates coordinates;
    Node parent;

    public Node(Coordinates coordinates, Node parent) {
        this.coordinates = coordinates;
        this.parent = parent;
    }


    public Node() {
    }

    private boolean isGoal(Node current, Node goal) {
        if (current.coordinates.line == goal.coordinates.line && current.coordinates.column == goal.coordinates.column) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValid(int x, int y, boolean[][] visited, Field field, Node goal) {
        return (x >= 0 && x < field.lines && y >= 0 && y < field.columns && !field.entities.containsKey(new Coordinates(x, y)) && !visited[x][y]);
    }

    public List<Node> showWay(Node start, Node goal, Field field) {
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[field.lines][field.columns];
        queue.add(start);
        visited[start.coordinates.line][start.coordinates.column] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (isGoal(current, goal)) {
                return constructPath(current);
            }

            boolean flag = isNextNodeGoal(current, goal);

            for (int[] direction : new int[][]{{0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}}) {
                int newX = current.coordinates.line + direction[0];
                int newY = current.coordinates.column + direction[1];

                if (flag) {
                    Node next = new Node(new Coordinates(newX,newY), current);
                    if (isGoal(next, goal)) {
                        return constructPath(next);
                    }
                } else {
                    if (isValid(newX, newY, visited, field, goal)) {
                        visited[newX][newY] = true;
                        queue.add(new Node(new Coordinates(newX, newY), current));
                    }
                }

            }
        }
        return List.of();
    }

    private boolean isNextNodeGoal(Node current, Node goal) {
        for (int[] direction : new int[][]{{0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}}) {
            int newX = current.coordinates.line + direction[0];
            int newY = current.coordinates.column + direction[1];
            boolean flag = isGoal(new Node(new Coordinates(newX, newY), current), goal);
            if (flag) {
                return flag;
            }
        }
        return false;
    }


    private List<Node> constructPath(Node goal) {
        List<Node> path = new ArrayList<>();
        for (Node node = goal; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }
}
