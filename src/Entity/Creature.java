package Entity;

//import java.util.Map;

import Map.Field;
import Map.Node;

import java.util.List;

public abstract class Creature extends Entity {
    Creature(Coordinates myCoordinates, int speed, int hp) {
        super(myCoordinates);
        this.speed = speed;
        this.hp = hp;
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
        System.out.println("Приступить к удалению текущих координат с карты");
        field.field.remove(coordinate);
        System.out.println("Присвоить координатам существа кооординаты из пути");
        coordinate=path.get(getSpeed()).coordinates;
        System.out.printf("Теперь координаты существа равны [%s,%s]",coordinate.x+1,coordinate.y+1);
        System.out.println("");
        System.out.println("Поместить по данным координатам существо.");
        field.field.put(coordinate,this);
    }

    protected List<Node> findAWaytothegoal(Coordinates current, Coordinates goal, Field field) {

        Node currentNode = new Node(current, null);
        Node goalNode = new Node(goal, null);

        return new Node().bfs(currentNode, goalNode, field);
    }

    public abstract void makeMove(Field field);
}
