package states;

import org.osbot.rs07.Bot;
import states.core.Task;

public class WalkToAltar extends Task {


    public WalkToAltar(Bot bot, String taskname) {
        super(bot, "Walk to altar");
    }

    @Override
    protected boolean booleancanExecute() {
        return false;
    }

    @Override
    protected boolean canExecute() {
        return false;
    }

    @Override
    protected boolean onExecute() {
        return false;
    }

    @Override
    protected void onEndExecute() {

    }
}
