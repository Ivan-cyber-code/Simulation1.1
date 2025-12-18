package gameplay;

import entity.*;
import map.*;

import java.util.ArrayList;
import java.util.List;

public class TurnAction extends Action {

    private final int END_HEALTH = 0;
    private final static int MOVE_REPRODUCTION = 5;
    private final static int END_CREATURE = 0;

    InitAction initActions = new InitAction();

    public void makeAMoveWithAllCreatures(Field field) {


        List<Entity> copyField = new ArrayList<>(field.getEntities().values());


        for (Entity entity : copyField) {
            if (entity instanceof Creature) {
                if (((Creature) entity).getHealth() <= END_HEALTH) {
                    continue;
                }
                if (((Creature) entity).getConterMoves() % MOVE_REPRODUCTION == 0 && ((Creature) entity).getConterMoves() != END_CREATURE) {
                    createCreature(entity, field);
                }

                ((Creature) entity).makeMove(field);
            }
        }
    }

    private void createCreature(Entity entity, Field field) {
        if (field.getEntities().size() <= (field.getLines() * field.getColumns()) / 2) {
            if (entity instanceof Herbivore) {
                initActions.createHerbivore(field);
            } else {
                initActions.createPredator(field);
            }
        }
    }
}
