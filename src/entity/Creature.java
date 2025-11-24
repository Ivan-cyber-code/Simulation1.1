package entity;

//import java.util.Map;

import map.Field;
import map.Node;

import java.util.List;

public abstract class Creature extends Entity {
    Creature(Coordinates myCoordinates, int speed, int hp) {
        super(myCoordinates);
        this.speed = speed;
        this.hp = hp;
    }

    private int moveConter;

    public int getMoveConter() {
        return moveConter;
    }

    public void setMoveConter() {
        this.moveConter = ++moveConter;
    }

    private int speed, hp;

    public int getSpeed() {
        return speed;
    }

    public void setHp(int hp) {
        this.hp += hp;
    }

    public int getHp() {
        return hp;
    }

    protected void moveTheTarget(Field field, List<Node> path){
        setHp(-1);
        field.field.remove(coordinate);
        coordinate=path.get(getSpeed()).coordinates;
        field.field.put(coordinate,this);
        if (getHp() <= 0) {
            field.field.remove(coordinate);
        }
    }

    protected List<Node> findAWaytothegoal(Coordinates current, Coordinates goal, Field field) {

        Node currentNode = new Node(current, null);
        Node goalNode = new Node(goal, null);

        return new Node().bfs(currentNode, goalNode, field);
    }

    public abstract void makeMove(Field field);
}
