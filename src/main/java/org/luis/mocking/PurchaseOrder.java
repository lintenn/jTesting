package org.luis.mocking;

public class PurchaseOrder {

    private final String name;
    private final int amount;

    public PurchaseOrder(String productName, int amountToBuy) {
        name = productName;
        amount = amountToBuy;
    }

    public void purchase(Warehouse warehouse) {

        if (null != warehouse) {
            if (warehouse.thereAreProducts(name,amount)) {
                warehouse.remove(name, amount);
            }
        } else {
            throw new RuntimeException();
        }

    }

}
