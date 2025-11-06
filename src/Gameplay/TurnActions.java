package Gameplay;

import Entity.*;
import Map.*;

import java.util.ArrayList;
import java.util.List;

public class TurnActions extends Actions {
    InitActions initActions = new InitActions();

    public void makeAMoveWithAllCreatures(Field field) {


        List<Entity> copyOfTheField = new ArrayList<>(field.field.values());

        for (Entity entity : copyOfTheField) {
            if (entity instanceof Creature) {
                if (((Creature) entity).getHp() <= 0) {
                    continue;
                }
                if (((Creature) entity).getMoveConter() >= 4) {
                    createCreature(entity, field);
                    System.out.println("появилось потомство " + ((entity instanceof Predator) ? "у волков" : "у зайцев"));
                }

                ((Creature) entity).makeMove(field);
            }
        }
    }

    private void createCreature(Entity entity, Field field) {
        if(field.field.size()<=(field.lines*field.columns)/2){
            if (entity instanceof Herbivore) {
                initActions.createHerbivore(field);
            } else {
                initActions.createPredator(field);
            }
        }
    }
}
