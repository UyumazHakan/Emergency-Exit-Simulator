package agents.victim;


import knowledgeBase.emergencyState.EmergencyState;

import java.util.ArrayList;

/**
 * Created by Administrator on 03.01.2015.
 */
public class RegularVictim {
    private VictimAction nextAction;

    private VictimAction getNextAction() {
        VictimAction value = nextAction;
        nextAction = VictimAction.DO_NOTHING;
        return value;
    }

    public VictimAction ask(ArrayList<EmergencyState> states) {
        decide(states);
        return getNextAction();
    }

    private void decide(ArrayList<EmergencyState> states) {

    }
}
