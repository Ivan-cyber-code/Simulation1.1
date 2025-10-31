import Entity.*;
import Map.*;

import java.util.ArrayList;
import java.util.List;

public class TurnActions extends Actions {
    public void makeAMoveWithAllCreatures(Field field) {

        System.out.println("Создаем копию нашей мапы");

        List<Entity> copyOfTheField = new ArrayList<>(field.field.values());

        System.out.println("Перебираем  существ из копии_мапы");
        for (Entity entity : copyOfTheField) {
            System.out.println("Выполняем проверку существо ли сущность?");
            if (entity instanceof Creature) {

                System.out.println("Выполняем проверку существо заяц ли");
                if (entity instanceof Herbivore) {
                    System.out.println("Да существо заяц. Тогда проверим его здоровье");
                    if (((Herbivore)entity).getHp()<=0){
                        System.out.println("Зайца убили в прошлом ходе поэтому в этот раз без него.");
                        continue;
                    }
                }
                System.out.println("У этого существа все впорядке со здоровьем. Начинаем им ходить");
                ((Creature) entity).makeMove(field);
            }
        }
    }
}
