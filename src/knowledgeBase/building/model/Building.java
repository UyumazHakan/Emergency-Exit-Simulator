package knowledgeBase.building.model;

import java.util.Random;

/**
 * Created by Administrator on 03.01.2015.
 */
public class Building {
    private Floor[] floors;

    public Building(int numberOfFloors, int numberOfRooms) {
        floors = new Floor[numberOfFloors];
        int remainingRooms = numberOfRooms;
        int remainingFloors = numberOfFloors;
        int currentFloor = 0;
        while (remainingFloors > 0) {
            int roomsToUse = decideRoomNumber(remainingRooms, remainingFloors);
            generateFloor(currentFloor, roomsToUse);
            remainingFloors--;
            remainingRooms -= roomsToUse;
            if (remainingFloors == 0) floors[currentFloor].makeBottomRow();
            currentFloor++;
        }
        for (Floor floor : floors)
            floor.setOutsideRooms();

    }

    private int decideRoomNumber(int remainingRooms, int remainingFloors) {
        if (remainingFloors == 1)
            return remainingRooms;
        return (Math.random() > 0.5) ?
                (remainingRooms / remainingFloors) - 1
                : (remainingRooms / remainingFloors) + 1;
    }

    private void generateFloor(int floorNo, int numberOfRooms) {
        Floor generatedFloor;
        if (floorNo != 0) {
            Floor upNeighbor = floors[floorNo - 1];
            generatedFloor = new Floor(floorNo, numberOfRooms, upNeighbor);
        } else
            generatedFloor = new Floor(floorNo, numberOfRooms, null);
        floors[floorNo] = generatedFloor;
    }

    public Floor[] getFloors() {
        return floors;
    }

    @Override
    public String toString() {
        String value = "---- Building ----\n";
        for (Floor floor : floors) {
            value += floor + "\n";
        }
        value += "!--- Building ---!";
        return value;
    }

    public void startFire() {
        floors[new Random().nextInt(floors.length)].startFire();
    }

    public void leakGas() {
        floors[new Random().nextInt(floors.length)].leakGas();
    }

    public void addVictims(int numberOfVictims) {
        for (int i = 0; i < numberOfVictims; i++)
            floors[new Random().nextInt(floors.length)].addVictim();
    }
}
