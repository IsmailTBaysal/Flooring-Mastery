// Author: Ismail Baysal

package org.example.service;

import org.example.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringService {
    List<Order> getAllOrders();
    List<Order> getOrders(LocalDate date);
    void exportAllOrdersToFile();

    Order getOrder(int orderNumber, LocalDate date);

    void removeOrder(int orderNumber, LocalDate date);

    Order calculateOrder(Order order);

    void editOrder(Order order) throws FlooringDataValidationException;

    void createOrder(Order order) throws FlooringDuplicateOrderException, FlooringDataValidationException;

    void validateOrderData(Order order) throws FlooringDataValidationException;

    int generateUniqueOrderNumber(LocalDate date);
}
