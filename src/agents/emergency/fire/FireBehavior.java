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
    private boolean isSpread;

    public FireBehavior() {
        nextAction = FireAction.DO_NOTHING;
        heat = 0;
        isSpread = false;
    }

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
        if (heat > 0) heat++;
        if (states.contains(EmergencyState.LOW_HEAT) && heat == 0)
            heat = 1;
        else if (!isSpread && heat > 0) {
            isSpread = true;
            nextAction = FireAction.SPREAD;
        } else if (heat == 6 || heat == 9)
            nextAction = FireAction.INCREASE_HEAT;
        else if (heat > 15)
            nextAction = FireAction.EXPLODE;
    }
}
