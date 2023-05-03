package vending.machine;

import vending.interfaces.VendingDispenseHandler;
import vending.interfaces.VendingState;
import vending.interfaces.Vendor;
import vending.machine.state.IdleState;
import vending.records.Snack;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine implements Vendor {
    private VendingState state;
    private Snack snackSelected;
    private final Map<String, Snack> snacks;
    private final VendingDispenseHandler dispenseHandler;

    public VendingMachine(VendingDispenseHandler dispenseHandler) {
        this.state = new IdleState(this);
        this.snackSelected = null;
        this.snacks = new HashMap<>();
        this.dispenseHandler = dispenseHandler;
    }

    @Override
    public void addSnack(Snack snack) {
        snacks.put(snack.name(), snack);
    }

    @Override
    public void removeSnack(Snack snack) {
        snacks.remove(snack.name(), snack);
    }

    @Override
    public void removeSnackQuantity(String snackName, int quantity) {
        Snack snack = getSnack(snackName);
        int newQuantity = Math.max(snack.quantity() - quantity, 0);
        Snack updatedSnack = new Snack(snackName, snack.price(), newQuantity);
        removeSnack(snack);
        addSnack(updatedSnack);
    }

    @Override
    public Snack getSnack(String name) {
        return snacks.get(name);
    }

    @Override
    public Snack getSelectedSnack() {
        return snackSelected;
    }

    @Override
    public void setSelectedSnack(Snack snack) {
        snackSelected = snack;
    }

    @Override
    public VendingState getState() {
        return state;
    }

    @Override
    public void setState(VendingState state) {
        this.state = state;
    }

    @Override
    public void processDispenseRequest() {
        dispenseHandler.handleDispense(this);
    }
}
