package map;

import entity.Coordinates;
import entity.Entity;

import java.util.*;

public class PathFinder {

    final int minimumFieldBoundaries=0;

    public List<Node> findPath(Node start, Class<? extends Entity> goalType, Field field) {
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[field.getLines()][field.getColumns()];

        queue.add(start);
        visited[start.getCoordinates().getLine()][start.getCoordinates().getColumn()] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Проверяем: а вдруг текущая нода — цель?
            if (isGoal(current, goalType, field)) {
                return extractPath(current);
            }

            // Расширяем поиск во все стороны
            for (int[] direction : Node.SET_MOVES) {
                int newLine = current.getCoordinates().getLine() + direction[0];
                int newCol = current.getCoordinates().getColumn() + direction[1];

                // Проверяем, можно ли идти в эту клетку
                if (isValid(newLine, newCol, visited, field)) {
                    visited[newLine][newCol] = true;

                    Node nextNode = new Node(new Coordinates(newLine, newCol), current);

                    // Сразу проверяем: а не цель ли это?
                    if (isGoal(nextNode, goalType, field)) {
                        return extractPath(nextNode);
                    }

                    // Если нет — добавляем в очередь для дальнейшего поиска
                    queue.add(nextNode);
                }
            }
        }

        // Путь не найден — возвращаем пустой список (это нормально)
        return List.of();
    }

    private boolean isGoal(Node current, Class<? extends Entity> goalType, Field field) {
        if (goalType.isInstance((field.getEntities().get(current.getCoordinates())))){
            return true;
        } else {
            return false;
        }
    }
    private List<Node> extractPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node node= goal;
        while (node!= null) {
            path.add(node);
            node=node.getParent();
        }
        Collections.reverse(path);
        return path;
    }
    private boolean isNextNodeGoal(Node current, Class<? extends Entity> goalType, Field field) {
        for (int[] direction : Node.SET_MOVES) {
            int newLine = current.getCoordinates().getLine() + direction[0];
            int newColumn = current.getCoordinates().getColumn() + direction[1];
            boolean flag = isGoal(new Node(new Coordinates(newLine, newColumn),  current), goalType, field);
            if (flag) {
                return flag;
            }
        }
        return false;
    }
    private boolean isValid(int lines, int columns, boolean[][] visits, Field field) {
        int maxLine = field.getLines();
        int maxColumn = field.getColumns();
        return (lines >= minimumFieldBoundaries && lines < maxLine && columns >= minimumFieldBoundaries && columns < maxColumn && !field.getEntities().containsKey(new Coordinates(lines, columns)) && !visits[lines][columns]);
    }
}
