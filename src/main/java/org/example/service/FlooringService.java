// Author: Ismail Baysal

package org.example.service;

import org.example.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringService {
    List<Order> getAllOrders();
    List<Order> getOrders(LocalDate date);
    boolean exportAllOrdersToFile(List<Order> orders);

    Order getOrder(int orderNumber, LocalDate date);

    void removeOrder(int orderNumber);

    Order calculateOrder(Order order);

    void editOrder(Order order);

    void createOrder(Order order);

    void validateOrderData(Order order);



}
