package enums;

import states.core.Task;

public enum States {
    NOT_READY(null),
    WALK_TO_BANK(null),
    ALTAR(null),
    WALK_TO_ALTAR5(null);

    private Task task;

    States(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public void setTast(Task task) {
        this.task = task;
    }
}
