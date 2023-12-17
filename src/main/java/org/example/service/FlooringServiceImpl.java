// Author: Ismail Baysal
package org.example.service;

import org.example.dao.FlooringDao;
import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.State;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class FlooringServiceImpl implements FlooringService{

    FlooringDao dao;

    public FlooringServiceImpl(FlooringDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Order> getAllOrders() { // For exporting all the data
        return dao.getAllOrder();
    }

    @Override
    public List<Order> getOrders(LocalDate date) { // For listing all the orders in a file
        return dao.getOrders(date);
    }

    @Override
    public void exportAllOrdersToFile() {
        /*
        * Implement it later!
        * */
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) {
        List<Order> orderList = dao.getOrders(date);
        for (Order i : orderList){
            if (i.getOrderNumber() == orderNumber){
                return i;
            }
        }
        return null;
    }

    @Override
    public void removeOrder(int orderNumber, LocalDate date) {
        List<Order> orderList = dao.getOrders(date);
        for (Order i : orderList){
            if (i.getOrderNumber() == orderNumber){
                dao.removeOrder(date,orderNumber);
            }
        }
    }

    @Override
    public Order calculateOrder(Order order) {
        if (order == null || order.getProduct() == null || order.getState() == null) {
            throw new IllegalArgumentException("Order, Product, and State must not be null");
        }

        BigDecimal area = order.getArea();
        Product product = order.getProduct();
        State state = order.getState();

        BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();

        BigDecimal materialCost = area.multiply(costPerSquareFoot);
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
        BigDecimal taxRate = state.getTaxRate().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxRate);
        BigDecimal total = materialCost.add(laborCost).add(tax);

        // Setting values in Order
        order.getProduct().setMaterialCost(materialCost);
        order.getProduct().setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);

        return order;
    }

    @Override
    public void editOrder(Order order) throws FlooringDataValidationException {

        // Getting Product and State details from database here:
        String productString = order.getProduct().getProductType().substring(0,1).toUpperCase()
                + order.getProduct().getProductType().substring(1).toLowerCase();

        Product product = dao.getProduct(productString);

        order.setProduct(product);

        String stateString = order.getState().getStateName().substring(0,1).toUpperCase()
                + order.getState().getStateName().substring(1).toLowerCase();
        State state = dao.getState(stateString);

        order.setState(state);

        calculateOrder(order);

        validateOrderData(order);

        dao.editOrder(order.getDate(), order);
    }

    @Override
    public void createOrder(Order order) throws FlooringDuplicateOrderException, FlooringDataValidationException {

        if (dao.getOrders(order.getDate())!= null){
            // Checking if there is duplicate order
            List<Order> orderList = dao.getOrders(order.getDate());
            for (Order i : orderList) {
                if (i.getOrderNumber() == order.getOrderNumber()) {
                    throw new FlooringDuplicateOrderException(
                            "ERROR: Could not create order.  Order number "
                                    + order.getOrderNumber()
                                    + " already exists");
                }

            }

        }

        // Getting Product and State details from database here:
         String productString = order.getProduct().getProductType().substring(0,1).toUpperCase()
                 + order.getProduct().getProductType().substring(1).toLowerCase();

        Product product = dao.getProduct(productString);

        order.setProduct(product);

        String stateString = order.getState().getStateName().substring(0,1).toUpperCase()
                + order.getState().getStateName().substring(1).toLowerCase();
        State state = dao.getState(stateString);

        order.setState(state);

        calculateOrder(order);

        validateOrderData(order);

        dao.addOrder(order);
    }

    @Override
    public void validateOrderData(Order order)  throws FlooringDataValidationException{
        if (order.getDate() == null
                || order.getCustomerName().trim().isEmpty()
                || order.getState() == null
                || order.getProduct().getProductType().trim().isEmpty()
                || order.getArea() == null ){
            throw new FlooringDataValidationException(
                    "ERROR: All fields [Customer Name, State, Product, Area] are required.");
        }
    }

    @Override
    public int generateUniqueOrderNumber(LocalDate date) {
        int lastUsedOrderNumber;
        if (getOrders(date) == null){
            lastUsedOrderNumber = 0;
            return lastUsedOrderNumber;
      }else {
            lastUsedOrderNumber = getOrders(date).size(); // Getting the list of orders in a day to find last order number.
            lastUsedOrderNumber++;
            return lastUsedOrderNumber;
        }
    }
}
