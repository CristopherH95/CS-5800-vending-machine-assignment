package vending.machine;

import vending.interfaces.VendingDispenseHandler;
import vending.interfaces.Vendor;
import vending.records.Snack;

import java.util.Objects;

public class SnackDispenseHandler implements VendingDispenseHandler {
    private final String snackName;
    private final VendingDispenseHandler nextHandler;

    public SnackDispenseHandler(String snackName) {
        this.snackName = snackName;
        this.nextHandler = null;
    }

    public SnackDispenseHandler(String snackName, VendingDispenseHandler nextHandler) {
        this.snackName = snackName;
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleDispense(Vendor vendor) {
        if (checkCanHandleDispense(vendor)) {
            performDispense(vendor);
            return;
        }
        if (!Objects.isNull(nextHandler)) {
            nextHandler.handleDispense(vendor);
        }
    }

    private boolean checkCanHandleDispense(Vendor vendor) {
        Snack selectedSnack = vendor.getSelectedSnack();
        if (Objects.isNull(selectedSnack)) {
            return false;
        }
        String selectedSnackName = selectedSnack.name();

        return selectedSnackName.equals(snackName);
    }

    private void performDispense(Vendor vendor) {
        Snack selectedSnack = vendor.getSelectedSnack();
        vendor.decrementSnackQuantity(selectedSnack.name());
        vendor.setSelectedSnack(null);
    }
}
