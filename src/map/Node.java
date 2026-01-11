package map;



import java.util.*;

import entity.*;


public class Node {

    private Coordinates coordinates;
    private Node parent;

    static final int[][] SET_MOVES={{0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}};

    public Node(Coordinates coordinates, Node parent) {
        this.coordinates = coordinates;
        this.parent = parent;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Node getParent() {
        return parent;
    }
}
