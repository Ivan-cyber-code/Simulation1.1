package map;

import entity.Coordinates;
import entity.Entity;

import java.util.*;

public class PathFinder {

    final int minimumFieldBoundaries = 0;

    public List<Node> findPath(Node start, Class<? extends Entity> goalType, Field field) {
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[field.getLines()][field.getColumns()];
        queue.add(start);
        visited[start.getCoordinates().getLine()][start.getCoordinates().getColumn()] = true;
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (isGoal(current, goalType, field)) {
                return extractPath(current);
            }
            for (int[] direction : Node.SET_MOVES) {
                int newLine = current.getCoordinates().getLine() + direction[0];
                int newCol = current.getCoordinates().getColumn() + direction[1];
                if (isValid(newLine, newCol, visited, field) && isPassable(newLine, newCol, field, goalType)) {
                    visited[newLine][newCol] = true;
                    Node nextNode = new Node(new Coordinates(newLine, newCol), current);
                    if (isGoal(nextNode, goalType, field)) {
                        return extractPath(nextNode);
                    }
                    queue.add(nextNode);
                }
            }
        }
        return List.of();
    }

    private boolean isPassable(int newLine, int newCol, Field field, Class<? extends Entity> goalType) {
        if(field.getEntities().get(new Coordinates(newLine,newCol))==null || goalType.isInstance(field.getEntities().get(new Coordinates(newLine,newCol)))) {
            return true;
        }
        return false;
    }

    private boolean isGoal(Node current, Class<? extends Entity> goalType, Field field) {
        if (goalType.isInstance((field.getEntities().get(current.getCoordinates())))) {
            return true;
        } else {
            return false;
        }
    }

    private List<Node> extractPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node node = goal;
        while (node != null) {
            path.add(node);
            node = node.getParent();
        }
        Collections.reverse(path);
        if(path.size()==0){
            System.out.println("Внимание сейчас создаться пустой путь");
        }
        return path;
    }

    private boolean isValid(int lines, int columns, boolean[][] visits, Field field) {
        int maxLine = field.getLines();
        int maxColumn = field.getColumns();
        return (lines >= minimumFieldBoundaries && lines < maxLine && columns >= minimumFieldBoundaries && columns < maxColumn && !visits[lines][columns]);
    }
}
