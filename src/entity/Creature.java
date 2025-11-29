package entity;

//import java.util.Map;

import map.Field;
import map.Node;

import java.util.List;

public abstract class Creature extends Entity {
    Creature(Coordinates coordinates, int speed, int health) {
        super(coordinates);
        this.speed = speed;
        this.health = health;
    }

    private int ConterMoves;

    public int getConterMoves() {
        return ConterMoves;
    }

    public void setMoveConter() {
        this.ConterMoves = ++ConterMoves;
    }

    private int speed, health;

    public int getSpeed() {
        return speed;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    public int getHealth() {
        return health;
    }

    protected void move(Field field, List<Node> path){
        setHealth(-1);
        field.entities.remove(coordinate);
        coordinate=path.get(getSpeed()).coordinates;
        field.entities.put(coordinate,this);
        if (getHealth() <= 0) {
            field.entities.remove(coordinate);
        }
    }

    protected List<Node> showPath(Coordinates current, Coordinates goal, Field field) {

        Node currentNode = new Node(current, null);
        Node goalNode = new Node(goal, null);

        return new Node().findPath(currentNode, goalNode, field);
    }

    public abstract void makeMove(Field field);
}
