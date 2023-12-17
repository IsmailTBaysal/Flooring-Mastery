package org.example.service;

import org.example.dao.FlooringDaoImpl;
import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringServiceImplTest {
    FlooringService service = new FlooringServiceImpl(new FlooringDaoStubImpl());
    @Test
    void getAllOrders() {
        //Creating an order to see if it is same as the one in FlooringDaoStubImpl
        Order orderTest = new Order("Ada Lovelace", "Washington", "Wood", new BigDecimal(1), LocalDate.now());
        orderTest.setOrderNumber(1);

        Order shouldBeAda = service.getOrder(1,LocalDate.now());

        //onlyOrder should be the only one in getAllOrder
        assertEquals( 1, service.getAllOrders().size(),
                "Should only have one Order.");
        assertTrue( service.getAllOrders().contains(orderTest),
                "The one Order should be the same as the one in FlooringDaoStubImpl.");
    }

    @Test
    void getOrders() {
        Order orderTest = service.getOrder(1, LocalDate.now());
        //Creating a list to check against

        //Grabbing a date that we know won't be empty
        List<Order> getOnlyOrderList = service.getOrders(LocalDate.now());
        assertFalse(getOnlyOrderList.isEmpty(), "The list should not be empty");

        assertTrue(getOnlyOrderList.contains(orderTest),
                "The arraylist should contain the object");

        //Grabbing a order date that we know doesn't exist
        List<Order> shouldBeEmpty = service.getOrders(LocalDate.now().minusDays(1));
        assertTrue( shouldBeEmpty.isEmpty(), "The list should be empty");
    }
    @Test
    void getOrder() {
        Order orderTest = new Order("1", "Washington", "Wood", new BigDecimal(1), LocalDate.now());
        orderTest.setOrderNumber(1);
        Order getOnlyOrder = service.getOrder(1, LocalDate.now());
        assertNotNull(getOnlyOrder, "Getting 1 and LocalDate.now() should be not null.");
        assertEquals( orderTest, getOnlyOrder,
                "Order stored under 1 and LocalDate.now() should be the same");

        Order shouldBeNull = service.getOrder(2, LocalDate.now());
        assertNull( shouldBeNull, "Getting 2 and LocalDate.now() should be null.");
    }

    @Test
    void removeOrder() {
        Order orderTest = new Order("1", "Washington", "Wood", new BigDecimal(1), LocalDate.now());
        orderTest.setOrderNumber(1);

/*
        Order removeOrder = service.removeOrder(1, LocalDate.now());
        assertNotNull( removeOrder, "Removing 1 and LocalDate() should not be null.");
        assertEquals( orderTest, removeOrder, "Order removed from 1 and LocalDate() should be equal.");

        Order shouldBeNull = service.removeOrder(2, LocalDate.now().minusDays(1));
        assertNull( shouldBeNull, "Removing 2 and LocalDate.now().minusDays(1) should be null.");

 */

    }
    @Test
    void editOrder() throws FlooringDataValidationException {
        Order orderTest = new Order("1", "Florida", "Rock", new BigDecimal(1), LocalDate.now());
        orderTest.setOrderNumber(1);
        service.editOrder(orderTest);

        Order getOnlyOrder = service.getOrder(1, LocalDate.now());
        assertNotNull(getOnlyOrder, "Getting 1 and LocalDate.now() should be not null.");
        assertEquals( orderTest, getOnlyOrder,
                "Order stored under 1 and LocalDate.now() should be the same");

    }

    @Test
    void createOrder() throws FlooringDataValidationException {
        //Create an order
        Order orderTest = new Order("1", "Washington", "Wood", new BigDecimal(1), LocalDate.now());

        try {
            service.createOrder(orderTest);
        } catch (FlooringDuplicateOrderException | FlooringDataValidationException e) {
            // ASSERT
            fail("Order was valid. No exception should have been thrown.");
        }
    }


}