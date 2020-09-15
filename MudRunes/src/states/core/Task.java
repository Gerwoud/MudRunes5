package states.core;

import org.osbot.rs07.Bot;
import org.osbot.rs07.script.MethodProvider;

public abstract class Task extends MethodProvider {
    private String taskName;
    private Bot bot;
    protected boolean hasFinishedSuccessfully;

    public Task(Bot bot, String taskname) {
        exchangeContext(bot);
        this.taskName = taskName;
    }

    public final boolean execute() {
        hasFinishedSuccessfully = false;
        log("Checking requirements for task: " + this.toString());
        if (canExecute()) {
            log("Requirements met... Will execute task");
            log("Executing task: " + this.toString());
            hasFinishedSuccessfully =  onExecute();
            onEndExecute();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return taskName;
    }

    public boolean shouldExecute() {
        return canExecute();
    }

    protected abstract boolean booleancanExecute();

    protected abstract boolean canExecute();

    protected abstract boolean onExecute() throws InterruptedException;

    protected abstract void onEndExecute();
}
