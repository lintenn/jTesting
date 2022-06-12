package org.luis.mocking;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseOrderTest {

    @Test
    public void shouldPurchaseWorkProperlyWhenThereAreEnoughProducts() {
        //Arrange
        PurchaseOrder purchaseOrder = new PurchaseOrder("glass",3);

        //Mocking
        Warehouse warehouse = Mockito.mock(Warehouse.class);

        //Stubs
        Mockito.when(warehouse.thereAreProducts("glass",3)).thenReturn(true);

        //Act
        purchaseOrder.purchase(warehouse);

        //Assert
        Mockito.verify(warehouse).remove("glass",3);
        Mockito.verify(warehouse, Mockito.times(1)).remove("glass",3);
        Mockito.verify(warehouse).thereAreProducts("glass",3);
    }

    @Test
    public void shouldPurchaseWorkProperlyIfThereAreNotEnoughProducts() {
        //Arrange
        PurchaseOrder purchaseOrder = new PurchaseOrder("mouse",2);

        // Mocking
        Warehouse warehouse = Mockito.mock(Warehouse.class);

        //Stubs
        Mockito.when(warehouse.thereAreProducts("mouse",2)).thenReturn(false);

        // Act
        purchaseOrder.purchase(warehouse);

        //Assert
        //Mockito.verify(warehouse).remove("mouse",2);
        Mockito.verify(warehouse, Mockito.never()).remove("mouse",2);
        //Mockito.verify(warehouse).thereAreProducts("mouse",2);
    }

    @Test
    /**
     * There are enough products in a first invocation, and there are not in a second one
     */
    public void shouldPurchaseWorkProperlyCase1() {
        //Arrange
        var purchaseOrder1 = new PurchaseOrder("mouse", 2);
        var purchaseOrder2 = new PurchaseOrder("mouse", 3);

        // Mocking
        Warehouse warehouse = Mockito.mock(Warehouse.class);

        //Stubbing
        Mockito.when(warehouse.thereAreProducts("mouse",2)).thenReturn(true);
        Mockito.when(warehouse.thereAreProducts("mouse",3)).thenReturn(false);

        //Act
        purchaseOrder1.purchase(warehouse);
        purchaseOrder2.purchase(warehouse);

        //Assert
        Mockito.verify(warehouse).remove("mouse",2);
        Mockito.verify(warehouse, Mockito.never()).remove("mouse",3);

        //Mockito.verify(warehouse).thereAreProducts("mouse",2);
        //Mockito.verify(warehouse , Mockito.times(2)).thereAreProducts("mouse", Mockito.anyInt());
    }

}