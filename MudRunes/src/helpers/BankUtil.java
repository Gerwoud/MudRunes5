package helpers;

import org.osbot.rs07.Bot;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.MethodProvider;


public class BankUtil extends MethodProvider {
    private static BankUtil instance;

    private Area[] banks = new Area[]{Banks.LUMBRIDGE_UPPER, Banks.VARROCK_WEST, Banks.VARROCK_EAST,
            Banks.FALADOR_WEST, Banks.FALADOR_EAST, Banks.DRAYNOR, Banks.EDGEVILLE, Banks.AL_KHARID};

    private BankUtil() {

    }

    public static BankUtil getInstance() {
        return instance;
    }

    public static void initializeInstance(Bot bot) {
        if (instance == null) {
            instance = new BankUtil();
            instance.exchangeContext(bot);
        }
    }

    public boolean walkToBank() {
        return getWalking().webWalk(banks);
    }

    public boolean bankDepositAllExcept(String... itemNames) {
        return getBank().depositAllExcept(itemNames);
    }

    public boolean openBank() throws InterruptedException {
        return getBank().open();
    }

    public boolean bankIsOpen() {
        return getBank().isOpen();
    }

    public boolean depositAllItemsExcept(String... itemnames) throws InterruptedException {
        if (bankIsOpen() && bankDepositAllExcept(itemnames)) {
            closeBank();
        } else if (!bankIsOpen()) {
            if (openBank()) {
                depositAllItemsExcept(itemnames);
            } else {
                log("Unable to walk to closest bank.");
                getBot().getScriptExecutor().stop(false);
            }
        }
        if (getInventory().isEmptyExcept(itemnames)) {
            log("Deposited items...");
            return true;
        } else {
            return false;
        }
    }

    public boolean closeBank() {
        return getBank().close();
    }

    public boolean retrieveItemsFromBank(String itemName, int amount, boolean closeBank) throws InterruptedException {
        if (getBank().isOpen() && !getInventory().isFull() && getBank().contains(itemName) && getBank().withdraw(itemName, amount)) {
            if (closeBank) getBank().close();
        } else if (!getBank().isOpen()) {
            if (getBank().open()) {
                retrieveItemsFromBank(itemName, amount, closeBank);
            } else {
                if (walkToBank()) {
                    retrieveItemsFromBank(itemName, amount, closeBank);
                } else {
                    log("Unable to walk to closest bank");
                    getBot().getScriptExecutor().stop(false);
                }
            }
        } else if (!getInventory().isFull() && !getBank().contains(itemName)) {
            if (getBank().close()) {
                log("Bank does not contain required materials.");
                getBot().getScriptExecutor().stop(false);
            }
        } else if (getBank().isOpen() && getInventory().isFull()) {
            if (getBank().depositAll()) {
                log("Deposited all items.");
                retrieveItemsFromBank(itemName, amount, closeBank);
            }
        }
        if (getInventory().contains(itemName)) {
            log("Retrieved " + itemName + " from the bank.");
            return true;
        } else {
            return false;
        }
    }

    public boolean retrieveAllItemsFromBank(String itemName, boolean closeBank) throws InterruptedException {
        if (getBank().isOpen() && !getInventory().isFull() && getBank().contains(itemName) && getBank().withdrawAll(itemName)) {
            if (closeBank) getBank().close();
        } else if (!getBank().isOpen()) {
            if (getBank().isOpen()) {
                retrieveAllItemsFromBank(itemName, closeBank);
            } else {
                if (walkToBank()) {
                    retrieveAllItemsFromBank(itemName, closeBank);
                } else {
                    log("Unable to walk to the closest bank.");
                    getBot().getScriptExecutor().stop(false);
                }
            }
        } else if (!getBank().contains(itemName) || !getBank().withdrawAll(itemName)) {
            if (getBank().close()) {
                log("Bank does not contain required items.");
                getBot().getScriptExecutor().stop(false);
            }
        } else if (getBank().isOpen() && getInventory().isFull()) {
            if (getBank().isOpen() && getInventory().isFull()) {
                log("Deposited all items");
                retrieveAllItemsFromBank(itemName, closeBank);
            }
        }
        if (getInventory().contains(itemName)) {
            log("Retrieved " + itemName + " from bank.");
            return true;
        } else {
            return false;
        }
    }

}
