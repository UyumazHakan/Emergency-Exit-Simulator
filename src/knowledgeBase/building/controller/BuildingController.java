package knowledgeBase.building.controller;

import agents.emergency.EmergencyBehavior;
import agents.emergency.fire.FireAction;
import agents.emergency.fire.FireBehavior;
import agents.emergency.gas.GasAction;
import agents.emergency.gas.GasBehavior;
import agents.victim.RegularVictim;
import agents.victim.VictimAction;
import knowledgeBase.building.model.Building;
import knowledgeBase.building.model.EmergencyState;
import knowledgeBase.building.model.Floor;
import knowledgeBase.building.model.Room;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 04.01.2015.
 */
public class BuildingController {
    private Building building;
    private Floor[] floors;
    private ArrayList<Room> rooms;
    private int evolveStage;

    private Room currentRoom;


    public BuildingController(Building building) {
        this.building = building;
        this.floors = this.building.getFloors();
        this.rooms = new ArrayList<Room>();
        this.evolveStage = 1;
        for (Floor floor : this.floors) {
            rooms.addAll(Arrays.asList(floor.getRooms()));
        }
    }

    public void evolve() {
        for (Room room : rooms) {
            currentRoom = room;
            evolveRoom();
        }

        printStage();
        evolveStage++;
    }

    private void printStage() {
        try {

            String content = building.toString();

            File file = new File("stages/stage" + evolveStage + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void evolveRoom() {
        for (EmergencyBehavior emergency : currentRoom.getEmergencyBehaviors())
            askEmergency(emergency);
        for (int i = 0; i < currentRoom.getVictims().size(); i++) {
            RegularVictim victim = currentRoom.getVictims().get(i);
            askVictim(victim);
        }

        checkExplosion();
    }

    private void checkExplosion() {
        if (currentRoom.getEmergencyStates().contains(EmergencyState.HIGH_DENSITY) &&
                currentRoom.getEmergencyStates().contains(EmergencyState.HIGH_HEAT))
            currentRoom.addState(EmergencyState.EXPLODED);
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
                Room upNeighbor = currentRoom.getUpNeighborRoom();
                currentRoom.removeVictim(victim);
                upNeighbor.addVictim(victim);
                break;
            case GO_DOWN:
                Room downNeighbor = currentRoom.getDownNeighborRoom();
                currentRoom.removeVictim(victim);
                downNeighbor.addVictim(victim);
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
            case INCREASE_DENSITY:
                increaseDensity();
                break;
            case EXPLODE:
                explode();
                break;
            case SPREAD:
                gasSpread();
                break;
            case DO_NOTHING:
                return;
            default:
                return;
        }
    }

    private void gasSpread() {
        ArrayList<EmergencyState> leftNeighborStates = currentRoom.getLeftNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> rightNeighborStates = currentRoom.getRightNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> upNeighborStates = currentRoom.getUpNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> downNeighborStates = currentRoom.getDownNeighborRoom().getEmergencyStates();
        if (!leftNeighborStates.contains(EmergencyState.HIGH_DENSITY) ||
                !leftNeighborStates.contains(EmergencyState.MEDIUM_DENSITY) ||
                !leftNeighborStates.contains(EmergencyState.LOW_DENSITY))
            currentRoom.getLeftNeighborRoom().addState(EmergencyState.LOW_DENSITY);
        if (!rightNeighborStates.contains(EmergencyState.HIGH_DENSITY) ||
                !rightNeighborStates.contains(EmergencyState.MEDIUM_DENSITY) ||
                !rightNeighborStates.contains(EmergencyState.LOW_DENSITY))
            currentRoom.getRightNeighborRoom().addState(EmergencyState.LOW_DENSITY);
        if (!upNeighborStates.contains(EmergencyState.HIGH_DENSITY) ||
                !upNeighborStates.contains(EmergencyState.MEDIUM_DENSITY) ||
                !upNeighborStates.contains(EmergencyState.LOW_DENSITY))
            currentRoom.getUpNeighborRoom().addState(EmergencyState.LOW_DENSITY);
        if (!downNeighborStates.contains(EmergencyState.HIGH_DENSITY) ||
                !downNeighborStates.contains(EmergencyState.MEDIUM_DENSITY) ||
                !downNeighborStates.contains(EmergencyState.LOW_DENSITY))
            currentRoom.getDownNeighborRoom().addState(EmergencyState.LOW_DENSITY);
    }

    private void increaseDensity() {
        ArrayList<EmergencyState> states = currentRoom.getEmergencyStates();
        if (states.contains(EmergencyState.LOW_DENSITY)) {
            states.remove(EmergencyState.LOW_DENSITY);
            states.add(EmergencyState.MEDIUM_DENSITY);
        }
        if (states.contains(EmergencyState.MEDIUM_DENSITY)) {
            states.remove(EmergencyState.MEDIUM_DENSITY);
            states.add(EmergencyState.HIGH_DENSITY);
        }
    }

    private void explode() {
        currentRoom.getEmergencyStates().add(EmergencyState.EXPLODED);
        ArrayList<RegularVictim> victims = currentRoom.getVictims();
        for (RegularVictim victim : victims)
            victim.die();

    }

    private void askFireEmergency(FireBehavior fireEmergency) {
        FireAction action = fireEmergency.ask(currentRoom.getEmergencyStates());
        switch (action) {
            case INCREASE_HEAT:
                increaseHeat();
                break;
            case EXPLODE:
                explode();
                break;
            case SPREAD:
                fireSpread();
                break;
            case DO_NOTHING:
                return;
            default:
                return;
        }
    }

    private void fireSpread() {
        if (currentRoom.getDownNeighborRoom() == null)
            System.out.println("adasda" + currentRoom.getRoomNo());
        ArrayList<EmergencyState> leftNeighborStates = currentRoom.getLeftNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> rightNeighborStates = currentRoom.getRightNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> upNeighborStates = currentRoom.getUpNeighborRoom().getEmergencyStates();
        ArrayList<EmergencyState> downNeighborStates = currentRoom.getDownNeighborRoom().getEmergencyStates();
        if (!leftNeighborStates.contains(EmergencyState.HIGH_HEAT) ||
                !leftNeighborStates.contains(EmergencyState.MEDIUM_HEAT) ||
                !leftNeighborStates.contains(EmergencyState.LOW_HEAT))
            currentRoom.getLeftNeighborRoom().addState(EmergencyState.LOW_HEAT);
        if (!rightNeighborStates.contains(EmergencyState.HIGH_HEAT) ||
                !rightNeighborStates.contains(EmergencyState.MEDIUM_HEAT) ||
                !rightNeighborStates.contains(EmergencyState.LOW_HEAT))
            currentRoom.getRightNeighborRoom().addState(EmergencyState.LOW_HEAT);
        if (!upNeighborStates.contains(EmergencyState.HIGH_HEAT) ||
                !upNeighborStates.contains(EmergencyState.MEDIUM_HEAT) ||
                !upNeighborStates.contains(EmergencyState.LOW_HEAT))
            currentRoom.getUpNeighborRoom().addState(EmergencyState.LOW_HEAT);
        if (!downNeighborStates.contains(EmergencyState.HIGH_HEAT) ||
                !downNeighborStates.contains(EmergencyState.MEDIUM_HEAT) ||
                !downNeighborStates.contains(EmergencyState.LOW_HEAT))
            currentRoom.getDownNeighborRoom().addState(EmergencyState.LOW_HEAT);
    }

    private void increaseHeat() {
        ArrayList<EmergencyState> states = currentRoom.getEmergencyStates();
        if (states.contains(EmergencyState.LOW_HEAT)) {
            states.remove(EmergencyState.LOW_HEAT);
            states.add(EmergencyState.MEDIUM_HEAT);
        }
        if (states.contains(EmergencyState.MEDIUM_HEAT)) {
            states.remove(EmergencyState.MEDIUM_HEAT);
            states.add(EmergencyState.HIGH_HEAT);
        }
    }

}
