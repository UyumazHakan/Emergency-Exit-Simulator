package knowledgeBase.building.model;

import java.util.Random;

/**
 * Created by Administrator on 03.01.2015.
 */
public class Floor {
    private Room[] rooms;
    private int floorNo;
    private Floor upNeighbor;
    private Floor downNeighbor;


    public Floor(int floorNo, int numberOfRooms, Floor upNeighbor) {
        this.upNeighbor = upNeighbor;
        if (this.upNeighbor != null) this.upNeighbor.setDownNeighbor(this);
        rooms = new Room[numberOfRooms];
        this.floorNo = floorNo;
        for (int i = 0; i < numberOfRooms; i++)
            generateRoom(i);

        for (int i = 0; i < rooms.length - 1; i++) {
            rooms[i].setRightNeighborRoom(rooms[i + 1]);
        }

    }

    public void setOutsideRooms() {
        for (int i = 0; i < rooms.length; i++) {
            rooms[i].setOutsideRooms();
        }
    }

    public void setDownNeighbor(Floor downNeighbor) {
        this.downNeighbor = downNeighbor;
    }
    private void generateRoom(int roomNo) {
        if (roomNo == 0)
            generateLeftCornerRoom();
        else if (roomNo == rooms.length - 1)
            generateRightCornerRoom();
        else
            generateMiddleRoom(roomNo);
        if (floorNo == 0)
            rooms[roomNo].setVerticalNeighbor(Direction.NORTH);
        else {
            int numUpNeighborRooms = upNeighbor.rooms.length;
            int numRooms = rooms.length;
            int upNeighborNumber = (int) (((double) numUpNeighborRooms * roomNo) / numRooms);
            Room upNeighborRoom = upNeighbor.rooms[upNeighborNumber];
            rooms[roomNo].setUpNeighborRoom(upNeighborRoom);
        }
        rooms[roomNo].setRoomNo(roomNo);

    }

    public void makeBottomRow() {
        for (Room room : rooms)
            room.setVerticalNeighbor(Direction.SOUTH);
    }
    private void generateLeftCornerRoom() {
        rooms[0] = new Room(Direction.WEST);
    }

    private void generateRightCornerRoom() {
        int roomNo = rooms.length - 1;
        Room leftNeighbor = rooms[roomNo - 1];
        rooms[roomNo] = new Room(leftNeighbor, Direction.EAST);
    }

    private void generateMiddleRoom(int roomNo) {
        Room leftNeighbor = rooms[roomNo - 1];
        Room rightNeighbor = rooms[roomNo + 1];
        rooms[roomNo] = new Room(leftNeighbor, rightNeighbor);
    }

    public Room[] getRooms() {
        return rooms;
    }

    @Override
    public String toString() {
        String value = "--- Floor " + floorNo + " ---\n";
        for (Room room : rooms)
            value += room + "\n";
        value += "!-- Floor " + floorNo + " --!";
        return value;
    }

    public void startFire() {
        rooms[new Random().nextInt(rooms.length)].startFire();
    }

    public void leakGas() {
        rooms[new Random().nextInt(rooms.length)].leakGas();
    }

    public void addVictim() {
        rooms[new Random().nextInt(rooms.length)].addVictim();
    }
}
