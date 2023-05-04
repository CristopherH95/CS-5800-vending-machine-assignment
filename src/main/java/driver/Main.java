package driver;

import vending.exceptions.NotReadyException;
import vending.interfaces.VendingDispenseHandler;
import vending.interfaces.VendingState;
import vending.interfaces.Vendor;
import vending.machine.SnackDispenseHandler;
import vending.machine.VendingMachine;
import vending.records.Snack;

import java.util.Map;

public class Main {
    private static final String coke = "COKE";
    private static final String pepsi = "PEPSI";
    private static final String cheetos = "CHEETOS";
    private static final String doritos = "DORITOS";
    private static final String kitkat = "KITKAT";
    private static final String snickers = "SNICKERS";
    private static final String[] allSnackNames = new String[]{
        coke,
        pepsi,
        cheetos,
        doritos,
        kitkat,
        snickers
    };
    private static final Map<String, Double> prices = Map.of(
        coke, 1.5,
        pepsi, 1.5,
        cheetos, 1.0,
        doritos, 1.0,
        kitkat, 1.25,
        snickers, 1.25
    );
    private static final int initialQuantity = 3;

    public static void main(String[] args) {
        runSimpleVendingTest();
        runQuantityOutTest();
    }

    private static void runSimpleVendingTest() {
        Vendor vendor = makeVendingMachine();

        testOrderSnack(vendor, coke);
        testOrderSnack(vendor, cheetos);
    }

    private static void runQuantityOutTest() {
        Vendor vendor = makeVendingMachine();

        for (int i = 0; i < initialQuantity + 1; i++) {
            System.out.printf("********** %s order %d **********%n", snickers, i + 1);
            testOrderSnack(vendor, snickers);
        }
    }

    private static Vendor makeVendingMachine() {
        VendingDispenseHandler vendingDispenseHandler = makeDispenser();
        Vendor vendor = new VendingMachine(vendingDispenseHandler);

        for (String snackName : allSnackNames) {
            vendor.addSnack(
                new Snack(
                    snackName,
                    prices.get(snackName),
                    initialQuantity
                )
            );
        }

        return vendor;
    }

    private static VendingDispenseHandler makeDispenser() {
        VendingDispenseHandler snickersHandler = new SnackDispenseHandler(snickers);
        VendingDispenseHandler kitkatHandler = new SnackDispenseHandler(kitkat, snickersHandler);
        VendingDispenseHandler doritosHandler = new SnackDispenseHandler(doritos, kitkatHandler);
        VendingDispenseHandler cheetosHandler = new SnackDispenseHandler(cheetos, doritosHandler);
        VendingDispenseHandler pepsiHandler = new SnackDispenseHandler(pepsi, cheetosHandler);

        return new SnackDispenseHandler(coke, pepsiHandler);
    }

    private static void testOrderSnack(Vendor vendor, String snackName) {
        System.out.printf("**** Running order process for: %s ****%n", snackName);
        try {
            runOrderTestSequence(vendor, snackName);
        } catch (NotReadyException e) {
            System.out.println("Failed to complete order");
            printState(vendor);
        }
        System.out.println("**** Order process completed ****");
    }

    private static void runOrderTestSequence(Vendor vendor, String snackName) {
        printState(vendor);
        System.out.println("Selecting snack");
        vendor.selectSnack(snackName);
        printState(vendor);
        System.out.println("Inserting required money");
        vendor.insertMoney(prices.get(snackName));
        printState(vendor);
        System.out.println("Attempting to dispense snack");
        vendor.dispenseSnack();
        printState(vendor);
    }

    private static void printState(Vendor vendor) {
        VendingState state = vendor.getState();
        System.out.printf("State: %s%n", state);
    }
}