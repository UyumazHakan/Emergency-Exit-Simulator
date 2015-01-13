package knowledgeBase.building.controller;

import agents.emergency.EmergencyBehavior;
import agents.emergency.fire.FireAction;
import agents.emergency.fire.FireBehavior;
import agents.emergency.gas.GasAction;
import agents.emergency.gas.GasBehavior;
import agents.victim.RegularVictim;
import agents.victim.VictimAction;
import knowledgeBase.building.model.Building;
import knowledgeBase.building.model.Floor;
import knowledgeBase.building.model.Room;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 04.01.2015.
 */
public class BuildingController {
    private Building building;
    private Floor[] floors;
    private ArrayList<Room> rooms;

    private Room currentRoom;


    public BuildingController(Building building) {
        this.building = building;
        this.floors = this.building.getFloors();
        this.rooms = new ArrayList<Room>();
        for (Floor floor : this.floors) {
            rooms.addAll(Arrays.asList(floor.getRooms()));
        }
        System.out.println(building);
        System.out.println(rooms.size());
    }

    public void evolve() {
        for (Room room : rooms) {
            currentRoom = room;
            evolveRoom();
        }
    }

    private void evolveRoom() {
        for (EmergencyBehavior emergency : currentRoom.getEmergencyBehaviors())
            askEmergency(emergency);
        for (RegularVictim victim : currentRoom.getVictims())
            askVictim(victim);
    }

    private void askVictim(RegularVictim victim) {
        VictimAction action = victim.ask(currentRoom.getEmergencyStates());
        switch (action) {
            case GO_RIGHT:
                Room rightNeighbor = currentRoom.getRightNeighborRoom();
                currentRoom.removeVictim(victim);
                rightNeighbor.addVictim(victim);
                break;
            case GO_LEFT:
                Room leftNeighbor = currentRoom.getLeftNeighborRoom();
                currentRoom.removeVictim(victim);
                leftNeighbor.addVictim(victim);
                break;
            case GO_UP:
                break;
            case GO_DOWN:
                break;
            case DO_NOTHING:
                break;
            default:
                break;
        }
    }

    private void askEmergency(EmergencyBehavior emergency) {
        if (emergency instanceof FireBehavior)
            askFireEmergency((FireBehavior) emergency);
        else if (emergency instanceof GasBehavior)
            askGasEmergency((GasBehavior) emergency);
    }

    private void askGasEmergency(GasBehavior gasEmergency) {
        GasAction action = gasEmergency.ask(currentRoom.getEmergencyStates());
        switch (action) {
            case DO_NOTHING:
                return;
            default:
                return;
        }
    }

    private void askFireEmergency(FireBehavior fireEmergency) {
        FireAction action = fireEmergency.ask(currentRoom.getEmergencyStates());
        switch (action) {
            case DO_NOTHING:
                return;
            default:
                return;
        }
    }

}
