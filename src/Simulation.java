import Entity.*;
import Map.*;

public class Simulation {

    Field field;
    int moveCounter;
    Renderer renderer = new Renderer();
    InitActions initActions = new InitActions();
    TurnActions turnActions = new TurnActions();



    public Simulation(Field field) {
        this.field = field;
    }

    public void nextTurn(){
        turnActions.makeAMoveWithAllCreatures(field);
        renderer.showMap(field);
    }

    public void startSimulation(){
        initActions.putEntitiesInTheirDefoultPositions(field);
        renderer.showMap(field);
        boolean flag = true;
        while (flag){
            nextTurn();
            if(!thereIsGrass(field)){
                initActions.plantGrass(field);
                renderer.showMap(field);
            }
            if(!thereIsHerbivore(field)){
                flag=false;
                renderer.showMap(field);
            }
        }
    }

    private boolean thereIsHerbivore(Field field) {
        for (Entity entity: field.field.values()){
            if (entity instanceof Herbivore){
                return true;
            }
        }
        return false;
    }


    private boolean thereIsGrass(Field field) {
        for (Entity entity : field.field.values()){
            if (entity instanceof Grass){
                return true;
            }
        }
        return false;
    }


    Actions[] gameLoop;

}
