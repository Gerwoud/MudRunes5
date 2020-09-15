package states;

import helpers.BankUtil;
import org.osbot.rs07.Bot;
import states.core.Task;

public class Bank extends Task {
    public Bank(Bot bot, String taskname) {
        super(bot, "Banking Task");
    }

    @Override
    protected boolean booleancanExecute() {
        return false;
    }

    @Override
    protected boolean canExecute() {
        return inventory.contains("Earth rune") && inventory.getAmount("Earth runes") >= 28;
    }

    @Override
    protected boolean onExecute() throws InterruptedException {
        String[] itemsToKeep = new String[]{"Bronze axe"};
        return BankUtil.getInstance().depositAllItemsExcept(itemsToKeep);
    }

    @Override
    protected void onEndExecute() {

    }
}
