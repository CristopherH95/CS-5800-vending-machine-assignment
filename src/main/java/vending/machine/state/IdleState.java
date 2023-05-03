package vending.machine.state;

import vending.exceptions.NotReadyException;
import vending.interfaces.VendingState;
import vending.interfaces.Vendor;
import vending.records.Snack;

public class IdleState implements VendingState {
    private final Vendor vendor;

    public IdleState(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public void selectSnack(String snackName) {
        Snack snack = vendor.getSnack(snackName);
        vendor.setSelectedSnack(snack);
        vendor.setState(new WaitingState(vendor));
    }

    @Override
    public double insertMoney(double money) {
        return money;
    }

    @Override
    public void dispenseSnack() {
        throw new NotReadyException("Unable to dispense snack in idle state");
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
