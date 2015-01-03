package knowledgeBase.building;

/**
 * Created by Administrator on 03.01.2015.
 */
public class Floor {
    private Room[] rooms;
    private int floorNo;

    public Floor(int floorNo, int numberOfRooms) {
        rooms = new Room[numberOfRooms];
        this.floorNo = floorNo;
        for (int i = 0; i < numberOfRooms; i++)
            generateRoom(i);
    }

    private void generateRoom(int roomNo) {
        if (roomNo == 0)
            generateLeftCornerRoom();
        else if (roomNo == rooms.length)
            generateRightCornerRoom();
        else
            generateMiddleRoom(roomNo);

    }

    private void generateLeftCornerRoom() {
        rooms[0] = new Room(true);
    }

    private void generateRightCornerRoom() {
        int roomNo = rooms.length - 1;
        Room leftNeighbor = rooms[roomNo - 1];
        rooms[roomNo] = new Room(leftNeighbor, false);
    }

    private void generateMiddleRoom(int roomNo) {
        Room leftNeighbor = rooms[roomNo - 1];
        Room rightNeighbor = rooms[roomNo + 1];
        rooms[roomNo] = new Room(leftNeighbor, rightNeighbor);
    }
}
