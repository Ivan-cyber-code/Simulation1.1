package Entity;

//import java.util.Map;

import Map.Field;
import Map.Node;

import java.util.List;

public class Predator extends Creature {
    public Predator(Coordinates coordinates, int speed, int hp, int attsckPower) {
        super(coordinates, speed, hp);
        this.attsckPower = attsckPower;
    }

    private int attsckPower;

    public int getAttsckPower() {
        return attsckPower;
    }

    @Override
    public void makeMove(Field field) {
        System.out.printf("""
                Итак, делает ход волк:
                текущие координаты: [%s,%s]
                """, coordinate.x + 1, coordinate.y + 1);
        System.out.println("Найти зайца");
        Herbivore herbivore = getHerbifore(field);
        System.out.printf("Его координаты [%s,%s]", herbivore.coordinate.x + 1, herbivore.coordinate.y + 1);
        System.out.println();
        System.out.println("Найти путь от цели до  зайца");
        List<Node> path = findAWaytothegoal(coordinate, herbivore.coordinate, field);

        System.out.printf("Путь содержит %s узлов", path.size());

        if (path.size() - 1 > getSpeed()) {
            System.out.println("Расстояние до цели больше 1ой клетки");
            moveTheTarget(field, path);
        } else {
            System.out.println("Расстояние до цели не более одной клетки и находится в зоне атаки");
            attack(field, herbivore);
        }
    }

//    private void putACreatureOnTheField(Field field) {
//        field.field.put(coordinate, this);
//    }

    private void attack(Field field, Herbivore herbivore) {
        System.out.println("Понизить здоровье у зайца");
        herbivore.setHp(-1 * getAttsckPower());
        System.out.printf("""
                Теперь состояние здоровья зайца находящегося по координатам [%s,%s] = %s ед.
                """, herbivore.coordinate.x + 1, herbivore.coordinate.y + 1, herbivore.getHp());
        if (herbivore.getHp() <= 0) {
            System.out.println("Поэтому нужно удалить зайца с  карты");
            field.field.remove(herbivore.coordinate);
            System.out.println("Также нужно удалить предыдущие координаты волка");
            field.field.remove(coordinate);
            System.out.println("Присвоить координатам хищника координаты зайца");
            coordinate = herbivore.coordinate;
            System.out.println("Поместить волка на координаты зайца");
            field.field.put(coordinate,this);
        }
    }

    private Herbivore getHerbifore(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Herbivore) {
                return ((Herbivore) entity);
            }
        }
        return null;
    }


}
