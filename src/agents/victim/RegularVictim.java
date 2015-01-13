package agents.victim;


import agents.Agent;
import knowledgeBase.building.model.EmergencyState;
import knowledgeBase.building.model.Room;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class RegularVictim implements Agent {
    private VictimAction nextAction;
    private VictimAction lastAction;
    private Room currentRoom;
    private boolean isDeath;

    public RegularVictim(Room currentRoom) {
        this.currentRoom = currentRoom;
        nextAction = VictimAction.DO_NOTHING;
        lastAction = VictimAction.DO_NOTHING;
        isDeath = false;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    private VictimAction getNextAction() {
        VictimAction value = nextAction;
        lastAction = nextAction;
        nextAction = VictimAction.DO_NOTHING;
        return value;
    }

    public void die() {
        isDeath = true;
    }
    public VictimAction ask(ArrayList<EmergencyState> states) {
        decide(states);
        return getNextAction();
    }

    private void decide(ArrayList<EmergencyState> states) {
        ArrayList<EmergencyState> leftEmergencies = currentRoom.getLeftNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> rightEmergencies = currentRoom.getRightNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> upEmergencies = currentRoom.getUpNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> downEmergencies = currentRoom.getDownNeighborRoom().getEmergencyStates();
        if (states.contains(EmergencyState.HIGH_DENSITY) || states.contains(EmergencyState.HIGH_HEAT))
            revertAction();
        else if (!leftEmergencies.contains(EmergencyState.HIGH_HEAT) &&
                !leftEmergencies.contains(EmergencyState.MEDIUM_HEAT) &&
                !leftEmergencies.contains(EmergencyState.HIGH_DENSITY) &&
                !leftEmergencies.contains(EmergencyState.MEDIUM_DENSITY) &&
                lastAction != VictimAction.GO_RIGHT)
            nextAction = VictimAction.GO_LEFT;
        else if (!rightEmergencies.contains(EmergencyState.HIGH_HEAT) &&
                !rightEmergencies.contains(EmergencyState.MEDIUM_HEAT) &&
                !rightEmergencies.contains(EmergencyState.HIGH_DENSITY) &&
                !rightEmergencies.contains(EmergencyState.MEDIUM_DENSITY) &&
                lastAction != VictimAction.GO_LEFT)
            nextAction = VictimAction.GO_RIGHT;
        else if (!upEmergencies.contains(EmergencyState.HIGH_HEAT) &&
                !upEmergencies.contains(EmergencyState.MEDIUM_HEAT) &&
                !upEmergencies.contains(EmergencyState.HIGH_DENSITY) &&
                !upEmergencies.contains(EmergencyState.MEDIUM_DENSITY) &&
                lastAction != VictimAction.GO_DOWN)
            nextAction = VictimAction.GO_UP;
        else if (!downEmergencies.contains(EmergencyState.HIGH_HEAT) &&
                !downEmergencies.contains(EmergencyState.MEDIUM_HEAT) &&
                !downEmergencies.contains(EmergencyState.HIGH_DENSITY) &&
                !downEmergencies.contains(EmergencyState.MEDIUM_DENSITY) &&
                lastAction != VictimAction.GO_UP)
            nextAction = VictimAction.GO_DOWN;
        else if (!leftEmergencies.contains(EmergencyState.HIGH_HEAT) &&
                !leftEmergencies.contains(EmergencyState.HIGH_DENSITY) &&
                lastAction != VictimAction.GO_RIGHT)
            nextAction = VictimAction.GO_LEFT;
        else if (!rightEmergencies.contains(EmergencyState.HIGH_HEAT) &&
                !rightEmergencies.contains(EmergencyState.HIGH_DENSITY) &&
                lastAction != VictimAction.GO_LEFT)
            nextAction = VictimAction.GO_RIGHT;
        else if (!upEmergencies.contains(EmergencyState.HIGH_HEAT) &&
                !upEmergencies.contains(EmergencyState.HIGH_DENSITY) &&
                lastAction != VictimAction.GO_DOWN)
            nextAction = VictimAction.GO_UP;
        else if (!downEmergencies.contains(EmergencyState.HIGH_HEAT) &&
                !downEmergencies.contains(EmergencyState.HIGH_DENSITY) &&
                lastAction != VictimAction.GO_UP)
            nextAction = VictimAction.GO_DOWN;
        else
            nextAction = VictimAction.DO_NOTHING;

    }

    private void revertAction() {
        switch (lastAction) {
            case DO_NOTHING:
                nextAction = VictimAction.DO_NOTHING;
                break;
            case GO_UP:
                nextAction = VictimAction.GO_DOWN;
                break;
            case GO_DOWN:
                nextAction = VictimAction.GO_UP;
                break;
            case GO_LEFT:
                nextAction = VictimAction.GO_RIGHT;
                break;
            case GO_RIGHT:
                nextAction = VictimAction.GO_LEFT;
                break;

        }
    }

}
