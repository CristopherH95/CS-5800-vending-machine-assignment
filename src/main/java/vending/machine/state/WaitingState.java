package vending.machine.state;

import vending.exceptions.NotReadyException;
import vending.interfaces.VendingState;
import vending.interfaces.Vendor;
import vending.records.Snack;

public class WaitingState implements VendingState {
    private final Vendor vendor;

    public WaitingState(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public void selectSnack(String snackName) {
        Snack snack = vendor.getSnack(snackName);
        vendor.setSelectedSnack(snack);
    }

    @Override
    public double insertMoney(double money) {
        // If desired item is unavailable, then we can reset the state of the vending machine
        if (isSelectedItemEmpty()) {
            vendor.setState(new IdleState(vendor));
            return money;
        }
        if (canDispenseWithMoney(money)) {
            vendor.setState(new DispensingState(vendor));
            return getChangeForSelected(money);
        }
        return money;
    }

    @Override
    public void dispenseSnack() {
        throw new NotReadyException("Cannot dispense snacks until money has been inserted");
    }

    private boolean isSelectedItemEmpty() {
        Snack selectedSnack = vendor.getSelectedSnack();
        return selectedSnack.quantity() <= 0;
    }

    private boolean canDispenseWithMoney(double money) {
        Snack selectedSnack = vendor.getSelectedSnack();
        return selectedSnack.price() <= money;
    }

    private double getChangeForSelected(double money) {
        Snack selectedSnack = vendor.getSelectedSnack();
        return selectedSnack.quantity() - money;
    }

    @Override
    public String toString() {
        return "Waiting...";
    }
}
