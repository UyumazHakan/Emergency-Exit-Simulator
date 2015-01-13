package agents.emergency.gas;

import agents.emergency.EmergencyBehavior;
import knowledgeBase.building.model.EmergencyState;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class GasBehavior implements EmergencyBehavior {
    private GasAction nextAction;
    private GasAction lastAction;
    private boolean isSpread;
    private int density;

    public GasBehavior() {
        nextAction = GasAction.DO_NOTHING;
        lastAction = GasAction.DO_NOTHING;
        isSpread = false;
        density = 0;
    }

    private GasAction getNextAction() {
        GasAction value = nextAction;
        lastAction = nextAction;
        nextAction = GasAction.DO_NOTHING;
        return value;
    }

    public GasAction ask(ArrayList<EmergencyState> states) {
        decide(states);
        return getNextAction();
    }

    private void decide(ArrayList<EmergencyState> states) {
        if (density > 0) density++;
        if (states.contains(EmergencyState.LOW_DENSITY) && density == 0)
            density = 1;
        else if (!isSpread && density > 0) {
            isSpread = true;
            nextAction = GasAction.SPREAD;
        } else if (density == 6 || density == 9)
            nextAction = GasAction.INCREASE_DENSITY;
        else if (density > 15)
            nextAction = GasAction.EXPLODE;

    }
}
