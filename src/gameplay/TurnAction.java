package gameplay;

import entity.*;
import map.*;

import java.util.ArrayList;
import java.util.List;

public class TurnAction extends Action {
    InitAction initActions = new InitAction();

    public void makeAMoveWithAllCreatures(Field field) {


        List<Entity> copyField = new ArrayList<>(field.entities.values());

        for (Entity entity : copyField) {
            if (entity instanceof Creature) {
                if (((Creature) entity).getHealth() <= 0) {
                    continue;
                }
                if (((Creature) entity).getConterMoves() % 5 == 0 && ((Creature) entity).getConterMoves() != 0) {
                    createCreature(entity, field);
                }

                ((Creature) entity).makeMove(field);
            }
        }
    }

    private void createCreature(Entity entity, Field field) {
        if (field.entities.size() <= (field.lines * field.columns) / 2) {
            if (entity instanceof Herbivore) {
                initActions.createHerbivore(field);
            } else {
                initActions.createPredator(field);
            }
        }
    }
}
