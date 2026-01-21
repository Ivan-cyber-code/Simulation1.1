package action;

import entity.*;
import map.*;

import java.util.ArrayList;
import java.util.List;

public class MakeMoveCreaturesAction extends Action {

    private static final double MAXIMUMCARDLIMIT = 0.25;
    private final int END_HEALTH = 0;

    public void execute(Field field) {
        List<Entity> copyField = new ArrayList<>(field.getEntities().values());
        for (Entity entity : copyField) {
            if (isCreature(entity)) {
                if (isDeadAlive(entity)) {
                    continue;
                }
                ((Creature) entity).makeMove(field);
            }
        }
    }

    private boolean isDeadAlive(Entity entity) {
        return ((Creature)entity).getHealth()<=END_HEALTH;
    }

    boolean isCreature(Entity entity) {
        return entity instanceof Creature;
    }
}
