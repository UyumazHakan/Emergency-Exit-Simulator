package knowledgeBase.building.controller;

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
}
