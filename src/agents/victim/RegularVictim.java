package agents.victim;


import agents.Agent;
import agents.emergency.EmergencyBehavior;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class RegularVictim implements Agent {
    private VictimAction nextAction;

    private VictimAction getNextAction() {
        VictimAction value = nextAction;
        nextAction = VictimAction.DO_NOTHING;
        return value;
    }

    public VictimAction ask(ArrayList<EmergencyBehavior> states) {
        decide(states);
        return getNextAction();
    }

    private void decide(ArrayList<EmergencyBehavior> states) {

    }
}
