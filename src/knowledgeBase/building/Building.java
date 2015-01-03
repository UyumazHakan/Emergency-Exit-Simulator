package knowledgeBase.building;

/**
 * Created by Administrator on 03.01.2015.
 */
public class Building {
    private Floor[] floors;

    public Building(int numberOfFloors, int numberOfRooms) {
        floors = new Floor[numberOfFloors];
        int remainingRooms = numberOfRooms;
        int remainingFloors = numberOfFloors;
        int currentFloor = 1;
        while (remainingFloors > 0) {
            int roomsToUse = decideRoomNumber(remainingRooms, remainingFloors);
            generateFloor(currentFloor, roomsToUse);
            remainingFloors--;
            currentFloor++;
        }

    }

    private int decideRoomNumber(int remainingRooms, int remainingFloors) {
        return (Math.random() > 0.5) ?
                (remainingRooms / remainingFloors) - 1
                : (remainingRooms / remainingFloors) + 1;
    }

    private void generateFloor(int floorNo, int numberOfRooms) {
        Floor generatedFloor = new Floor(floorNo, numberOfRooms);
        floors[floorNo] = generatedFloor;
    }
}
