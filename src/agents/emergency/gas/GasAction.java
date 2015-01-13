package agents.emergency.gas;

import agents.emergency.EmergencyAction;

/**
 * Created by Administrator on 03.01.2015.
 */
public enum GasAction implements EmergencyAction {
    DO_NOTHING, INCREASE_DENSITY, EXPLODE, SPREAD
}
