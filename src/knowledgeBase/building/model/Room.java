package knowledgeBase.building.model;

import agents.emergency.EmergencyBehavior;
import agents.emergency.fire.FireBehavior;
import agents.emergency.gas.GasBehavior;
import agents.victim.RegularVictim;

import java.util.ArrayList;

enum Direction {
    NORTH, WEST, SOUTH, EAST,
    NORTHWEST, NORTHEAST,
    SOUTHWEST, SOUTHEAST
}

/**
 * Created by Administrator on 03.01.2015.
 */
public class Room {
    protected Room leftNeighborRoom;
    protected Room rightNeighborRoom;
    protected Room upNeighborRoom;
    protected Room downNeighborRoom;
    private ArrayList<EmergencyBehavior> emergencyStates;
    private ArrayList<RegularVictim> victims;

    protected Room() {
        emergencyStates = new ArrayList<EmergencyBehavior>();
        victims = new ArrayList<RegularVictim>();
        emergencyStates.add(new FireBehavior());
        emergencyStates.add(new GasBehavior());
    }

    public Room(Direction direction) {
        this(null, direction);
    }

    public Room(Room leftNeighborRoom, Room rightNeighborRoom) {
        this();
        this.leftNeighborRoom = leftNeighborRoom;
        this.rightNeighborRoom = rightNeighborRoom;
    }

    public Room(Room neighborRoom, Direction direction) {
        this();
        if (direction == Direction.WEST) {
            this.leftNeighborRoom = new Outside(this, direction);
            this.rightNeighborRoom = neighborRoom;
        } else {
            this.leftNeighborRoom = neighborRoom;
            this.rightNeighborRoom = new Outside(this, direction);
        }
    }

    public void setVerticalNeighbor(Direction direction) {
        if (direction == Direction.NORTH) {
            this.upNeighborRoom = new Outside(this, direction);
        } else {
            this.downNeighborRoom = new Outside(this, direction);
        }
    }

    public void setUpNeighborRoom(Room upNeighborRoom) {
        this.upNeighborRoom = upNeighborRoom;
        this.upNeighborRoom.setDownNeighborRoom(this);
    }

    public void setDownNeighborRoom(Room downNeighborRoom) {
        this.downNeighborRoom = downNeighborRoom;
    }

    public RegularVictim removeVictim(RegularVictim victim) {
        victims.remove(victim);
        return victim;
    }

    public void addVictim(RegularVictim victim) {
        victims.add(victim);
    }

    public ArrayList<EmergencyBehavior> getEmergencyStates() {
        return emergencyStates;
    }

    public Room getRightNeighborRoom() {
        return rightNeighborRoom;
    }

    public void setRightNeighborRoom(Room rightNeighborRoom) {
        this.rightNeighborRoom = rightNeighborRoom;
    }

    public Room getLeftNeighborRoom() {
        return leftNeighborRoom;
    }

    public void setLeftNeighborRoom(Room leftNeighborRoom) {
        this.leftNeighborRoom = leftNeighborRoom;
    }

    public ArrayList<RegularVictim> getVictims() {
        return victims;
    }

    @Override
    public String toString() {
        return "-Room-";
    }
}

class Outside extends Room {
    public Outside(Room neighborRoom, Direction direction) {
        super();
        switch (direction) {
            case NORTH:
                downNeighborRoom = neighborRoom;
                break;
            case WEST:
                rightNeighborRoom = neighborRoom;
                break;
            case SOUTH:
                upNeighborRoom = neighborRoom;
                break;
            case EAST:
                leftNeighborRoom = neighborRoom;
                break;
            default:
                break;
        }
        setOthersToSelf();
    }

    private void setOthersToSelf() {
        if (downNeighborRoom == null) downNeighborRoom = this;
        if (upNeighborRoom == null) upNeighborRoom = this;
        if (rightNeighborRoom == null) rightNeighborRoom = this;
        if (leftNeighborRoom == null) leftNeighborRoom = this;
    }
}
