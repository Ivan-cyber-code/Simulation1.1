package action;

import entity.Creature;
import entity.*;
import map.Field;

import java.util.HashMap;
import java.util.Map;

public class SpawnCreatureAction extends Action {
    private final static int MOVE_REPRODUCTION = 5;
    private final static int END_CREATURE = 0;
    private MakeMoveCreaturesAction makeMoveCreaturesAction = new MakeMoveCreaturesAction();
    private SpawnEntityAction spawnEntityAction = new SpawnEntityAction();

    public void execute(Field field) {
        Map <Coordinates, Entity> copyField = new HashMap<>(field.getEntities());
        for (Entity entity : copyField.values()) {
            if (makeMoveCreaturesAction.isCreature(entity)) {
                if (isSpawnCreature(field, entity)) {
                    spawnCreature(entity, field);
                }
            }
        }
    }

    private void spawnCreature(Entity entity, Field field) {
        if (field.countSizeEntities() < spawnEntityAction.countMaximumCardLimit(field)) {
            if (entity instanceof Herbivore) {
                spawnEntityAction.createHerbivore(field);
            } else {
                spawnEntityAction.createPredator(field);
            }
        }
    }
    private boolean isSpawnCreature(Field field, Entity entity) {
        return ((Creature) entity).getConterMoves() % MOVE_REPRODUCTION == 0 && ((Creature) entity).getConterMoves() >= END_CREATURE && field.countSizeEntities() < field.countSizeMap() * spawnEntityAction.CONSTLIMITCARD;
    }
}
