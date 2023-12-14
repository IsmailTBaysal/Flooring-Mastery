// Author: Ismail Baysal
package org.example.service;

import org.example.dao.FlooringDao;
import org.example.dto.Order;

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
    public boolean exportAllOrdersToFile(List<Order> orders) {
        return false;
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
        return null;
    }

    @Override
    public void editOrder(Order order) {
        dao.editOrder(order.getDate(), order);
    }

    @Override
    public void createOrder(Order order) throws FlooringDuplicateOrderException {
        List<Order> orderList = dao.getOrders(order.getDate());
        for (Order i : orderList){
            if (i.getOrderNumber() == order.getOrderNumber()){
                throw new FlooringDuplicateOrderException(
                        "ERROR: Could not create order.  Order number "
                                + order.getOrderNumber()
                                + " already exists");
            }

            validateOrderData(order);
            dao.addOrder(order);
            }


    }

    @Override
    public void validateOrderData(Order order) {

    }
}
