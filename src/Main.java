import knowledgeBase.building.controller.BuildingController;
import knowledgeBase.building.model.Building;

/**
 * Created by Administrator on 03.01.2015.
 */
public class Main {
    public static void main(String[] args) {
        Building building = new Building(3, 15);
        BuildingController controller = new BuildingController(building);
    }
}
