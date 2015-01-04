package agents.emergency.gas;

import agents.emergency.EmergencyBehavior;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class GasBehavior implements EmergencyBehavior {
    private GasAction nextAction;
    private int density;

    private GasAction getNextAction() {
        GasAction value = nextAction;
        nextAction = GasAction.DO_NOTHING;
        return value;
    }

    public GasAction ask(ArrayList<EmergencyBehavior> states) {
        decide(states);
        return getNextAction();
    }

    private void decide(ArrayList<EmergencyBehavior> states) {

    }
}
