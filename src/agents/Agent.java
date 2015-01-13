package agents;

import knowledgeBase.building.model.EmergencyState;

import java.util.ArrayList;

/**
 * Created by Administrator on 04.01.2015.
 */
public interface Agent {

    public Action ask(ArrayList<EmergencyState> states);
}
