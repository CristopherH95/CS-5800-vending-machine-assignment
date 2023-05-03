package vending.machine.state;

import vending.exceptions.NotReadyException;
import vending.interfaces.VendingState;
import vending.interfaces.Vendor;

public class DispensingState implements VendingState {
    private Vendor vendor;

    public DispensingState(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public void selectSnack(String snackName) {
        throw new NotReadyException("Cannot select snacks while dispensing");
    }

    @Override
    public double insertMoney(double money) {
        return money;
    }

    @Override
    public void dispenseSnack() {
        vendor.processDispenseRequest();
        vendor.setState(new IdleState(vendor));
    }
}
