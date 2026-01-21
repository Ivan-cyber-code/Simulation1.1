package action;

import entity.Creature;
import entity.*;
import map.Field;

public class SpawnCreatureAction {
    private final static int MOVE_REPRODUCTION = 5;
    private final static int END_CREATURE = 0;
    private MakeMoveCreaturesAction makeMoveCreaturesAction = new MakeMoveCreaturesAction();
    private SpawnEntityAction spawnEntityAction = new SpawnEntityAction();

    public void execute(Field field){
        for (Entity entity : field.getAllEntities()){
            if (makeMoveCreaturesAction.isCreature(entity)){
                if (((Creature) entity).getConterMoves() % MOVE_REPRODUCTION == 0 && ((Creature) entity).getConterMoves() != END_CREATURE) {
                    createCreature(entity, field);
                }
            }
        }
    }

    private void createCreature(Entity entity, Field field) {
        if (field.countSizeEntities()< spawnEntityAction.countMaximumCardLimit(field)) {
            if (entity instanceof Herbivore) {
                spawnEntityAction.createHerbivore(field);
            } else {
                spawnEntityAction.createPredator(field);
            }
        }
    }
    private boolean isSpawnCreature(Field field){
        return field.countSizeMap()/spawnEntityAction.CONSTLIMITCARD<spawnEntityAction.countMaximumCardLimit(field)
    }
}
