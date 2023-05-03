package vending.interfaces;

public interface VendingState {
    void selectSnack(String snackName);
    double insertMoney(double money);
    void dispenseSnack();
}
