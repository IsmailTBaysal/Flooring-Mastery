package org.example.dao;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringDaoImplTest {
    FlooringDao dao = new FlooringDaoImpl();
    LocalDate date = LocalDate.parse("0001-02-03");

    @AfterEach
    public void setUp(){
        List<Order> listOrder = dao.getOrders(date);
        for(Order o: listOrder) {
            dao.removeOrder(date, o.getOrderNumber());
        }
    }

    @Test
    void addOrder() {
        //Creates an Order to add
        Order orderAdded = new Order(1, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.addOrder(orderAdded);
        //Grabs Order. Because it is mapped to an arraylist we need to grab the 0th index
        Order orderCheck = dao.getOrders(date).get(0);

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

        assertEquals(orderAdded.getState().getStateName(),
                orderCheck.getState().getStateName(),
                "Checking Order State Name.");

        assertEquals(orderAdded.getProduct().getProductType(),
                orderCheck.getProduct().getProductType(),
                "Checking Order Product.");

    }

    @Test
    void editOrder() {
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

        assertEquals(orderAddedEdited.getState().getStateName(),
                orderCheck.getState().getStateName(),
                "Checking Order State.");

        assertEquals(orderAddedEdited.getProduct().getProductType(),
                orderCheck.getProduct().getProductType(),
                "Checking Order Product.");
    }

    @Test
    void getOrders() {
        //Same as above and adding created Orders
        Order orderAdded1 = new Order(1, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.addOrder(orderAdded1);
        List<Order> check1 = dao.getOrders(date);
        assertEquals(1, check1.size());
        Order orderAdded2 = new Order(2, "2", "2", new BigDecimal(2), "2",
                new BigDecimal(2), new BigDecimal(2), new BigDecimal(2), new BigDecimal(2),
                new BigDecimal(2), new BigDecimal(2), new BigDecimal(2), date);
        dao.addOrder(orderAdded2);

        //Checks if the list contains two Orders
        List<Order> check2 = dao.getOrders(date);
        assertEquals(2, check2.size());

        Order orderCheck1 = null;
        Order orderCheck2 = null;
        //Checks if the Orders are the list
        for(Order o: check2) {
            if(o.getOrderNumber() == orderAdded1.getOrderNumber()) {
                orderCheck1 = o;
            }
            else {
                orderCheck2 = o;
            }
        }

        assert orderCheck1 != null;
        assertEquals(orderAdded1.getOrderNumber(),
                orderCheck1.getOrderNumber(),
                "Checking Order Number.");

        assertEquals(orderAdded1.getCustomerName(),
                orderCheck1.getCustomerName(),
                "Checking Customer Name.");

        assertEquals(orderAdded1.getArea(),
                orderCheck1.getArea(),
                "Checking Area.");

        assertEquals(orderAdded1.getTax(),
                orderCheck1.getTax(),
                "Checking Tax.");

        assertEquals(orderAdded1.getTotal(),
                orderCheck1.getTotal(),
                "Checking Order Total.");

        assertEquals(orderAdded1.getDate(),
                orderCheck1.getDate(),
                "Checking Order Date.");

        assertEquals(orderAdded1.getState().getStateName(),
                orderCheck1.getState().getStateName(),
                "Checking Order State.");

        assertEquals(orderAdded1.getProduct().getProductType(),
                orderCheck1.getProduct().getProductType(),
                "Checking Order Product.");


        assert orderCheck2 != null;
        assertEquals(orderAdded2.getOrderNumber(),
                orderCheck2.getOrderNumber(),
                "Checking Order Number.");

        assertEquals(orderAdded2.getCustomerName(),
                orderCheck2.getCustomerName(),
                "Checking Customer Name.");

        assertEquals(orderAdded2.getArea(),
                orderCheck2.getArea(),
                "Checking Area.");

        assertEquals(orderAdded2.getTax(),
                orderCheck2.getTax(),
                "Checking Tax.");

        assertEquals(orderAdded2.getTotal(),
                orderCheck2.getTotal(),
                "Checking Order Total.");

        assertEquals(orderAdded2.getDate(),
                orderCheck2.getDate(),
                "Checking Order Date.");

        assertEquals(orderAdded2.getState().getStateName(),
                orderCheck2.getState().getStateName(),
                "Checking Order State.");

        assertEquals(orderAdded2.getProduct().getProductType(),
                orderCheck2.getProduct().getProductType(),
                "Checking Order Product.");

    }

    @Test
    void removeOrder() {
        //Adds Orders to remove them
        Order orderAdded1 = new Order(1, "1", "1", new BigDecimal(1), "1",
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
        dao.addOrder(orderAdded1);

        Order orderAdded2 = new Order(2, "2", "2", new BigDecimal(2), "2",
                new BigDecimal(2), new BigDecimal(2), new BigDecimal(2), new BigDecimal(2),
                new BigDecimal(2), new BigDecimal(2), new BigDecimal(2), date);
        dao.addOrder(orderAdded2);

        //Removes orders and checks the size to see if they were removed
        dao.removeOrder(date, 1);
        List<Order> check1 = dao.getOrders(date);
        assertEquals(1, check1.size());
        //Only one Order in list
        Order orderCheck = check1.get(0);

        //Checks if it removes the correct order
        assertEquals(orderAdded2.getOrderNumber(),
                orderCheck.getOrderNumber(),
                "Checking Order Number.");

        assertEquals(orderAdded2.getCustomerName(),
                orderCheck.getCustomerName(),
                "Checking Customer Name.");

        assertEquals(orderAdded2.getArea(),
                orderCheck.getArea(),
                "Checking Area.");

        assertEquals(orderAdded2.getTax(),
                orderCheck.getTax(),
                "Checking Tax.");

        assertEquals(orderAdded2.getTotal(),
                orderCheck.getTotal(),
                "Checking Order Total.");

        assertEquals(orderAdded2.getDate(),
                orderCheck.getDate(),
                "Checking Order Date.");

        assertEquals(orderAdded2.getState().getStateName(),
                orderCheck.getState().getStateName(),
                "Checking Order State.");

        assertEquals(orderAdded2.getProduct().getProductType(),
                orderCheck.getProduct().getProductType(),
                "Checking Order Product.");

        //Checks if the list is empty since all two object were removed
        dao.removeOrder(date, 2);
        List<Order> check2 = dao.getOrders(date);
        assertEquals(0, check2.size());
    }
    @Test
    void getAllOrder() {
        try {
            //Creates an Order to add
            Order orderAdded1 = new Order(1, "1", "1", new BigDecimal(1), "1",
                    new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                    new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date);
            dao.addOrder(orderAdded1);

            //Creates an Order with a different date
            Order orderAdded2 = new Order(1, "1", "1", new BigDecimal(1), "1",
                    new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1),
                    new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), date.minusDays(1));
            dao.addOrder(orderAdded2);


            //Checks size to make sure all Orders were grabbed
            List<Order> check = dao.getAllOrder();
            assertEquals(2, check.size());

            //Checks if correct order were added
            assertTrue(check.contains(orderAdded1),
                    "The list of Orders should include orderAdded1.");
            assertTrue(check.contains(orderAdded2),
                    "The list of Orders should include orderAdded2.");
        } finally {
            List<Order> listOrder = dao.getOrders(date.minusDays(1));
            for(Order o: listOrder) {
                dao.removeOrder(date.minusDays(1), o.getOrderNumber());
            }
        }
    }
    @Test
    void addState() {
        //Adds States
        State stateAdded = new State("1", new BigDecimal(1));
        dao.addState("1", stateAdded);

        State getState = dao.getState("1");

        //Checks if the added state and the state get are the same
        assertEquals(stateAdded.getStateName(),
                getState.getStateName(),
                "Checking State Name.");

        assertEquals(stateAdded.getTaxRate(),
                getState.getTaxRate(),
                "Checking State Tax Rate.");

    }
    @Test
    void getState() {
        //Adds States
        State stateAdded1 = new State("1", new BigDecimal(1));
        dao.addState("1", stateAdded1);
        State stateAdded2 = new State("2", new BigDecimal(2));
        dao.addState("2", stateAdded2);
        //Checks both states and sees if they can be grabbed correctly

        State getState1 = dao.getState("1");
        assertEquals(stateAdded1.getStateName(),
                getState1.getStateName(),
                "Checking State Name.");

        assertEquals(stateAdded1.getTaxRate(),
                getState1.getTaxRate(),
                "Checking State Tax Rate.");

        State getState2 = dao.getState("2");

        assertEquals(stateAdded2.getStateName(),
                getState2.getStateName(),
                "Checking State Name.");

        assertEquals(stateAdded2.getTaxRate(),
                getState2.getTaxRate(),
                "Checking State Tax Rate.");
    }
    @Test
    void addProduct() {
        //Adds Product
        Product productAdded = new Product("1", new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1));
        dao.addProduct("1", productAdded);

        Product getProduct = dao.getProduct("1");

        //Checks if the added Product and the Product get are the same
        assertEquals(productAdded.getProductType(),
                getProduct.getProductType(),
                "Checking Product Type.");

        assertEquals(productAdded.getCostPerSquareFoot(),
                getProduct.getCostPerSquareFoot(),
                "Checking Cost Per Square Foot.");

        assertEquals(productAdded.getLaborCost(),
                getProduct.getLaborCost(),
                "Checking Labor Cost.");

        assertEquals(productAdded.getMaterialCost(),
                getProduct.getMaterialCost(),
                "Checking Material Cost.");

        assertEquals(productAdded.getLaborCostPerSquareFoot(),
                getProduct.getLaborCostPerSquareFoot(),
                "Checking Labor Cost Per Square Foot.");

    }
    @Test
    void getProduct() {
        //Adds Products
        Product productAdded1 = new Product("1", new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1));
        dao.addProduct("1", productAdded1);

        Product productAdded2 = new Product("2", new BigDecimal(2), new BigDecimal(2), new BigDecimal(2), new BigDecimal(2));
        dao.addProduct("2", productAdded2);

        Product getProduct1 = dao.getProduct("1");

        //Checks if the added Product and the Product get are the same
        assertEquals(productAdded1.getProductType(),
                getProduct1.getProductType(),
                "Checking Product Type.");

        assertEquals(productAdded1.getCostPerSquareFoot(),
                getProduct1.getCostPerSquareFoot(),
                "Checking Cost Per Square Foot.");

        assertEquals(productAdded1.getLaborCost(),
                getProduct1.getLaborCost(),
                "Checking Labor Cost.");

        assertEquals(productAdded1.getMaterialCost(),
                getProduct1.getMaterialCost(),
                "Checking Material Cost.");

        assertEquals(productAdded1.getLaborCostPerSquareFoot(),
                getProduct1.getLaborCostPerSquareFoot(),
                "Checking Labor Cost Per Square Foot.");

        Product getProduct2 = dao.getProduct("2");
        //Checks if the added Product and the Product get are the same
        assertEquals(productAdded2.getProductType(),
                getProduct2.getProductType(),
                "Checking Product Type.");

        assertEquals(productAdded2.getCostPerSquareFoot(),
                getProduct2.getCostPerSquareFoot(),
                "Checking Cost Per Square Foot.");

        assertEquals(productAdded2.getLaborCost(),
                getProduct2.getLaborCost(),
                "Checking Labor Cost.");

        assertEquals(productAdded2.getMaterialCost(),
                getProduct2.getMaterialCost(),
                "Checking Material Cost.");

        assertEquals(productAdded2.getLaborCostPerSquareFoot(),
                getProduct2.getLaborCostPerSquareFoot(),
                "Checking Labor Cost Per Square Foot.");
    }


}