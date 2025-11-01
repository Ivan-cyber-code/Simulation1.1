package Gameplay;

import Entity.*;
import Map.*;

import java.util.ArrayList;
import java.util.List;

public class TurnActions extends Actions {
    public void makeAMoveWithAllCreatures(Field field) {


        List<Entity> copyOfTheField = new ArrayList<>(field.field.values());

        for (Entity entity : copyOfTheField) {
            if (entity instanceof Creature) {

                if (entity instanceof Herbivore) {
                    if (((Herbivore)entity).getHp()<=0){
                        continue;
                    }
                }
                ((Creature) entity).makeMove(field);
            }
        }
    }
}
