package agents.emergency.fire;


import agents.emergency.EmergencyBehavior;
import knowledgeBase.building.model.EmergencyState;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class FireBehavior implements EmergencyBehavior {
    private FireAction nextAction;
    private int heat;

    private FireAction getNextAction() {
        FireAction value = nextAction;
        nextAction = FireAction.DO_NOTHING;
        return value;
    }

    public FireAction ask(ArrayList<EmergencyState> states) {
        decide(states);
        return getNextAction();
    }

    private void decide(ArrayList<EmergencyState> states) {

    }
}
