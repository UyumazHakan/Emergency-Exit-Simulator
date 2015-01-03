package agents.emergency.fire;

import knowledgeBase.emergencyState.EmergencyState;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class FireBehavior {
    private FireAction nextAction;

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
