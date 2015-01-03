package agents.emergency.gas;

import knowledgeBase.emergencyState.EmergencyState;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class GasBehavior {
    private GasAction nextAction;

    private GasAction getNextAction() {
        GasAction value = nextAction;
        nextAction = GasAction.DO_NOTHING;
        return value;
    }

    public GasAction ask(ArrayList<EmergencyState> states) {
        decide(states);
        return getNextAction();
    }

    private void decide(ArrayList<EmergencyState> states) {

    }
}
