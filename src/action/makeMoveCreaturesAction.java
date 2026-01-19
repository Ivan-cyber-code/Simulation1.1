package action;

import entity.*;
import map.*;

import java.util.ArrayList;
import java.util.List;

public class makeMoveCreaturesAction extends Action {

    private static final double MAXIMUMCARDLIMIT = 0.25;
    private final int END_HEALTH = 0;
    private final static int MOVE_REPRODUCTION = 5;
    private final static int END_CREATURE = 0;

    SpawnEntityAction spawnEntityActions = new SpawnEntityAction();

    public void execute(Field field) {
        List<Entity> copyField = new ArrayList<>(field.getEntities().values());
        for (Entity entity : copyField) {
            if (isCreature(entity)){
                if (isDeadAlive(entity)) {
                    continue;
                }
            }

            if (entity instanceof Creature) {
//                if (((Creature) entity).getHealth() <= END_HEALTH) {
//                    continue;
//                }
                if (((Creature) entity).getConterMoves() % MOVE_REPRODUCTION == 0 && ((Creature) entity).getConterMoves() != END_CREATURE) {
                    createCreature(entity, field);
                }

                ((Creature) entity).makeMove(field);
            }
        }
    }

    private boolean isDeadAlive(Entity entity) {
        return ((Creature)entity).getHealth()<=END_HEALTH;
    }

    private boolean isCreature(Entity entity) {
        return entity instanceof Creature;
    }

    private void createCreature(Entity entity, Field field) {
        if (field.getEntities().size()<field.getColumns()*field.getLines()*MAXIMUMCARDLIMIT) {
            if (entity instanceof Herbivore) {
                spawnEntityActions.createHerbivore(field);
            } else {
                spawnEntityActions.createPredator(field);
            }
        }
    }
}
