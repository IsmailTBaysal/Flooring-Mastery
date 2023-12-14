package org.example.dao;

import org.example.dto.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringDaoImplTest {
    FlooringDao dao = new FlooringDaoImpl();

    @Test
    void addOrder() {
        //Creates an Order to add
        Order orderAdded = new Order(1, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), LocalDate.now());
        dao.addOrder(orderAdded);
        //Grabs Order. Because it is mapped to an arraylist we need to grab the 0th index
        Order orderCheck = dao.getOrders(LocalDate.now()).get(0);

        //Checking if everything matches up
        assertEquals(orderAdded.getOrderNumber(),
                orderCheck.getOrderNumber(),
                "Checking Order Number.");

        assertEquals(orderAdded.getCustomerName(),
                orderCheck.getCustomerName(),
                "Checking Customer Name.");

        assertEquals(orderAdded.getArea(),
                orderCheck.getArea(),
                "Checking Area.");

        assertEquals(orderAdded.getTax(),
                orderCheck.getTax(),
                "Checking Tax.");

        assertEquals(orderAdded.getTotal(),
                orderCheck.getTotal(),
                "Checking Order Total.");

        assertEquals(orderAdded.getDate(),
                orderCheck.getDate(),
                "Checking Order Date.");

        assertEquals(orderAdded.getState(),
                orderCheck.getState(),
                "Checking Order State.");

        assertEquals(orderAdded.getProduct(),
                orderCheck.getProduct(),
                "Checking Order Product.");

    }

    @Test
    void editOrder() {
        //Creates an Order to add
        LocalDate date = LocalDate.now();
        Order orderAdded = new Order(1, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.addOrder(orderAdded);

        /*
        Only allowed to change CustomerName, State, ProductType, Area
        if orderNumber is change errors will occur!!! Do not change orderNumber
         */

        Order orderAddedEdited = new Order(1, "2", "2", new BigDecimal(1), "2",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.editOrder(date, orderAddedEdited);

        //Grabs Order. Because it is mapped to an arraylist we need to grab the 0th index
        Order orderCheck = dao.getOrders(date).get(0);

        //Checking if the order is returning the edited order
        assertEquals(orderAddedEdited.getOrderNumber(),
                orderCheck.getOrderNumber(),
                "Checking Order Number.");

        assertEquals(orderAddedEdited.getCustomerName(),
                orderCheck.getCustomerName(),
                "Checking Customer Name.");

        assertEquals(orderAddedEdited.getArea(),
                orderCheck.getArea(),
                "Checking Area.");

        assertEquals(orderAddedEdited.getTax(),
                orderCheck.getTax(),
                "Checking Tax.");

        assertEquals(orderAddedEdited.getTotal(),
                orderCheck.getTotal(),
                "Checking Order Total.");

        assertEquals(orderAddedEdited.getDate(),
                orderCheck.getDate(),
                "Checking Order Date.");

        assertEquals(orderAddedEdited.getState(),
                orderCheck.getState(),
                "Checking Order State.");

        assertEquals(orderAddedEdited.getProduct(),
                orderCheck.getProduct(),
                "Checking Order Product.");
    }

    @Test
    void getOrders() {
        //Same as above and adding created Orders
        LocalDate date = LocalDate.now();
        Order orderAdded1 = new Order(1, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.addOrder(orderAdded1);
        List<Order> check1 = dao.getOrders(date);
        assertEquals(1, check1.size());
        Order orderAdded2 = new Order(2, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.addOrder(orderAdded2);

        //Checks if the list contains two Orders
        List<Order> check2 = dao.getOrders(date);
        assertEquals(2, check1.size());

        //Checks if the Orders are the list
        assertTrue(check2.contains(orderAdded1),
                "The list of Orders should include orderAdded1.");
        assertTrue(check2.contains(orderAdded2),
                "The list of Orders should include orderAdded2.");
    }

    @Test
    void removeOrder() {
        //Adds Orders to remove them
        LocalDate date = LocalDate.now();
        Order orderAdded1 = new Order(1, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.addOrder(orderAdded1);

        Order orderAdded2 = new Order(2, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.addOrder(orderAdded2);

        //Removes orders and checks the size to see if they were removed
        dao.removeOrder(date, 1);
        List<Order> check1 = dao.getOrders(date);
        assertEquals(1, check1.size());

        //Checks if it removes the correct order
        assertTrue(check1.contains(orderAdded2),
                "The list of Orders should include orderAdded2.");

        //Checks if the list is empty since all two object were removed
        dao.removeOrder(date, 2);
        List<Order> check2 = dao.getOrders(date);
        assertEquals(0, check2.size());
    }

}