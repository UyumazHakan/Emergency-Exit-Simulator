package agents;

import agents.emergency.EmergencyBehavior;

import java.util.ArrayList;

/**
 * Created by Administrator on 04.01.2015.
 */
public interface Agent {

    public Action ask(ArrayList<EmergencyBehavior> states);
}
