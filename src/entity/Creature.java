package entity;

import map.Field;
import map.Node;
import map.PathFinder;

import java.util.List;

public abstract class Creature extends Entity {

    private int ConterMoves;
    private int speed;
    private int health;
    public final int HEAL = 3;

    Creature(Coordinates coordinates, int speed, int health) {
        super(coordinates);
        this.speed = speed;
        this.health = health;
    }

    protected void move(Field field, List<Node> path) {
        setHealth(-1);
        if (getHealth() <= 0) {
            field.deleteEntities(getCoordinate());
        } else {
            field.deleteEntities(getCoordinate());
            setCoordinate(path.get(getSpeed()).getCoordinates());
            field.installEntities(getCoordinate(), this);
        }
    }

    protected List<Node> showPath(Coordinates current, Class<? extends Entity> goalType, Field field) {

        Node currentNode = new Node(current, null);

        return new PathFinder().findPath(currentNode, goalType, field);
    }

    public abstract void makeMove(Field field);

    public int getConterMoves() {
        return ConterMoves;
    }

    void setMoveConter() {
        this.ConterMoves = ++ConterMoves;
    }

    int getSpeed() {
        return speed;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    public int getHealth() {
        return health;
    }
}
