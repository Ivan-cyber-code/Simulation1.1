package Entity;

//import java.util.Map;

import Map.Field;
import Map.Node;

import java.util.List;

public class Herbivore extends Creature {


//    Herbivore herbivore = new Herbivore(new Coordinates(0,0), 2, 5);


    public Herbivore(Coordinates coordinates, int speed, int hp) {
        super(coordinates, speed, hp);
    }

    private Grass getGrass(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Grass) {
                return (Grass) entity;
            }
        }
        return null;
    }

//    private void putACreatureOnTheField(Field field) {
//        field.field.put(coordinate, this);
//    }

    private void eatGrass(Field field, Entity grass) {
        System.out.println("Удалить с карты координаты травы");
        field.field.remove(grass.coordinate);
        System.out.println("Удалить с карты координаты существа");
        field.field.remove(coordinate);

        coordinate = grass.coordinate;
        System.out.printf("Определить новые координаты в соответсвии с путем, теперь они равны [%s,%s]",coordinate.x+1,coordinate.y+1);
        System.out.println();
        System.out.println("Прибавить здоровье зайцу");
        setHp(1);
        System.out.println("Поместить заца на карту");
        field.field.put(coordinate, this);
    }

    @Override
    public void makeMove(Field field) {
        System.out.printf("""
                Итак, делает ход заяц:
                текущие координаты: [%s,%s]
                здоровье: %s
                """,coordinate.x+1,coordinate.y+1,getHp());
        Grass grass = getGrass(field);
        System.out.printf("Трава найдена и находится по координатам [%s,%s]", grass.coordinate.x+1, grass.coordinate.y+1);
        System.out.println();
        System.out.println("Приступаем  к поиску пути от зайца до травы");
        List<Node> path = findAWaytothegoal(coordinate, grass.coordinate, field);
        System.out.printf("Путь содержит %s узлов",path.size());
        System.out.println();
        if (path.size() - 1 > getSpeed()) {
            System.out.println("Расстояние до цели больше 1ой клетки");
            moveTheTarget(field, path);
        } else {
            System.out.println("Расстояние до цели не более 1 клетки");
            eatGrass(field, grass);
        }
        System.out.printf("""
                Итак, состояние заяца:
                здоровье: %s
                """,getHp());
        System.out.println();

        if(field.field.containsKey(coordinate)){
            System.out.printf("Текущие координаты зайа [%s,%s]",coordinate.x+1,coordinate.y+1);
            System.out.println("");
        }
    }
}

