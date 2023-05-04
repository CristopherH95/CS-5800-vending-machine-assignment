package vending.interfaces;

import vending.records.Snack;

public interface Vendor extends VendingState {
    void addSnack(Snack snack);
    void removeSnack(Snack snack);
    void decrementSnackQuantity(String snackName);
    Snack getSelectedSnack();
    void setSelectedSnack(Snack snack);
    VendingState getState();
    void setState(VendingState state);
    Snack getSnack(String name);
    void processDispenseRequest();
}
