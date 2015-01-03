package knowledgeBase.building;

import knowledgeBase.emergencyState.EmergencyState;
import knowledgeBase.victim.RegularVictim;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class Room {
    protected Room leftNeighborRoom;
    protected Room rightNeighborRoom;
    private ArrayList<EmergencyState> emergencyStates;
    private ArrayList<RegularVictim> victims;

    protected Room() {
        emergencyStates = new ArrayList<EmergencyState>();
        victims = new ArrayList<RegularVictim>();
    }

    public Room(boolean isLeftCorner) {
        this(null, isLeftCorner);
    }

    public Room(Room leftNeighborRoom, Room rightNeighborRoom) {
        this();
        this.leftNeighborRoom = leftNeighborRoom;
        this.rightNeighborRoom = rightNeighborRoom;
    }

    public Room(Room neighborRoom, boolean isLeftCorner) {
        this();
        if (isLeftCorner) {
            this.leftNeighborRoom = new Outside(this, true);
            this.rightNeighborRoom = neighborRoom;
        } else {
            this.leftNeighborRoom = neighborRoom;
            this.rightNeighborRoom = new Outside(this, false);
        }
    }

    public void setLeftNeighborRoom(Room leftNeighborRoom) {
        this.leftNeighborRoom = leftNeighborRoom;
    }

    public void setRightNeighborRoom(Room rightNeighborRoom) {
        this.rightNeighborRoom = rightNeighborRoom;
    }
}

class Outside extends Room {
    public Outside(Room neighborRoom, boolean isLeftOutside) {
        super();
        if (isLeftOutside) {
            this.leftNeighborRoom = this;
            this.rightNeighborRoom = neighborRoom;
        } else {
            this.rightNeighborRoom = this;
            this.leftNeighborRoom = neighborRoom;
        }
    }
}
